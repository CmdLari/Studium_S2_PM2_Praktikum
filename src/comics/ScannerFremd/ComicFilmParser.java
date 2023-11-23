package comics.ScannerFremd;

import utils.YearInterval;

import java.time.Year;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComicFilmParser {

    //Identifiers for Groups inside the Patterns
    private static final String COUNT_COMIC_ITEMS_IN_SECTION = "rowspan";
    private static final String COMIC_TITLE_SECTION = "leftS";
    private static final String COMIC_ITEM_FROM_SECTION = "rightS";
    private static final String COMIC_ITEM = "single";
    private static final String COMIC_TITLE_WITHOUT_SECTION = "left";
    private static final String COMIC_ITEM_WITHOUT_SECTION = "right";

    private static final String COMIC_ITEM_DATE = "date";
    private static final String COMIC_ITEM_DATE_RANGE = "dateRange";
    private static final String COMIC_ITEM_NAME = "comicName";
    private static final String COMIC_ITEM_META = "meta";

    // Patterns used
    private static final Pattern PER_TR_PATTERN = Pattern.compile("<tr>(.*?)</tr>", Pattern.MULTILINE | Pattern.DOTALL);
    private static final String SANITIZE_PATTERN = "<a.*?>|</a>|<i>|</i>|\\r?\\n|\\r/g";
    private static final Pattern CELL_DATA_PATTERN = Pattern.compile("^<td rowspan=\"(?<rowspan>\\d+)\">(?<leftS>.+?)</td><td>(?<rightS>.+?)</td>$|^<td>(?<left>.+?)</td><td>(?<right>.+?)</td>$|^<td>(?<single>.*?)</td>$");
    private static final Pattern COMIC_ITEM_DATA_PATTERN = Pattern.compile("(?<comicName>.*)\\s\\((?:(?<meta>.*),)?.*?(?:(?<dateRange>\\d*)–)?(?<date>\\d+).*\\)");

    private final Map<String, Map<YearInterval, List<String>>> result = new HashMap<>();
    private Matcher perComicCellMatcher;
    private Matcher comicData;

    public void parse(MatchResult tbodyMatchResult) {
        parseSingleCells(tbodyMatchResult.group(1));
    }

    public Map<String, Map<YearInterval, List<String>>> result() {
        return result;
    }

    private void parseSingleCells(String tbody) {
        perComicCellMatcher = PER_TR_PATTERN.matcher(tbody);
        while (perComicCellMatcher.find()) {
            String cellData = sanitize(perComicCellMatcher.group(1));
//            if (isIndex(cellData)) continue;
            comicData = CELL_DATA_PATTERN.matcher(cellData);
            if (comicData.find()) {
                if (isNewComicSection()) {
                    startComicSection();
                }
                else if (isStandaloneComicSection()) {
                    startStandaloneComicSection();
                } else {
//                    System.err.printf("Error parsing Cell, no section nor standalone comic section found '%s'%n", cellData);
                }
            } else {
//                System.err.printf("Error parsing cellData '%s'%n", cellData);
            }
        }
    }

    private boolean isNewComicSection() {
        // mandatory fields for section
        final String rowsNum = comicData.group(COUNT_COMIC_ITEMS_IN_SECTION);
        final String comicTitleFromSection = comicData.group(COMIC_TITLE_SECTION);
        final String comicItemFromSection = comicData.group(COMIC_ITEM_FROM_SECTION);

        return nonNull(rowsNum, comicTitleFromSection, comicItemFromSection);
    }

    private boolean isStandaloneComicSection() {
        final String standaloneComicTitle = comicData.group(COMIC_TITLE_WITHOUT_SECTION);
        final String standaloneComicItem = comicData.group(COMIC_ITEM_WITHOUT_SECTION);

        return nonNull(standaloneComicTitle, standaloneComicItem);
    }

    private void startStandaloneComicSection() {
        final String standaloneComicTitle = comicData.group(COMIC_TITLE_WITHOUT_SECTION);
        final String standaloneComicItem = comicData.group(COMIC_ITEM_WITHOUT_SECTION);

        createComicEntry(standaloneComicTitle, standaloneComicItem);
    }

    private void startComicSection() {
        // mandatory fields for section
        final String rowsNum = comicData.group(COUNT_COMIC_ITEMS_IN_SECTION);
        final String comicTitleFromSection = comicData.group(COMIC_TITLE_SECTION);
        final String comicItemFromSection = comicData.group(COMIC_ITEM_FROM_SECTION);

        createComicEntry(comicTitleFromSection, comicItemFromSection);
        for (int i = 1; i < Integer.parseInt(rowsNum); i++) {
            if (perComicCellMatcher.find()) {
                final Matcher singleDataMatcher = CELL_DATA_PATTERN.matcher(sanitize(perComicCellMatcher.group(1)));
                if (singleDataMatcher.find()) {
                    createComicEntry(comicTitleFromSection, singleDataMatcher.group(COMIC_ITEM));
                } else {
//                    System.err.printf("Could not parse comic section data '%s'%n", singleDataMatcher.group());
                }
            } else {
//                System.err.printf("Could not parse comic section '%s'%n", perComicCellMatcher.group());
            }
        }
    }

    public void createComicEntry(String comicTitle, String comicItem) {
        final Matcher matcher = COMIC_ITEM_DATA_PATTERN.matcher(comicItem);
        if (matcher.find()) {
            final YearInterval yearInterval = getYearIntervalFrom(matcher);
            putComicData(comicTitle, yearInterval, getComicItemName(matcher));
        } else {
//            System.err.printf("Error parsing Comic Item Data '%s'%n", comicItem);
        }
    }

    private void putComicData(String comicTitle, YearInterval yearInterval, String... comicItems) {
        if (nonNull(comicTitle, yearInterval, comicItems)) {
            result.putIfAbsent(comicTitle, new HashMap<>());
            result.get(comicTitle).putIfAbsent(yearInterval, new ArrayList<>());
            result.get(comicTitle).get(yearInterval).addAll(List.of(comicItems));
        } else {
//            System.err.printf("Error putting comic data for title: '%s', yearInterval: '%s', comicItems: '%s'%n", comicTitle, yearInterval, Arrays.toString(comicItems));
        }
    }

    private YearInterval getYearIntervalFrom(Matcher comicItemDataMatcher) {
        final String date = comicItemDataMatcher.group(COMIC_ITEM_DATE);
        final String dateStart = comicItemDataMatcher.group(COMIC_ITEM_DATE_RANGE);

        if (Objects.nonNull(date)) {
            if (Objects.nonNull(dateStart)) return new YearInterval(Year.parse(dateStart), Year.parse(date));

            return new YearInterval(Year.parse(date));
        } else {
            System.err.printf("Error parsing '%s' group in comic item '%s'%n", COMIC_ITEM_DATE, comicItemDataMatcher.group());
        }
        return new YearInterval(Year.of(Year.MIN_VALUE));
    }

    private String getComicItemName(Matcher comicItemDataMatcher) {
        final String comicName = comicItemDataMatcher.group(COMIC_ITEM_NAME);
        final String comicMeta = comicItemDataMatcher.group(COMIC_ITEM_META);

        if (Objects.nonNull(comicName)) {
            if (Objects.nonNull(comicMeta)) {
                return String.format("%s (%s)", comicName, comicMeta);
            }
            return comicName;
        } else {
//            System.err.printf("Error parsing '%s' group in comic item '%s'%n", COMIC_ITEM_NAME, comicItemDataMatcher.group());
        }
        return "invalid name";
    }

    private boolean isIndex(String str) {
        return str.matches(".*class=.*|.*width.*");
    }

    private String sanitize(String str) {
        return str.replaceAll(SANITIZE_PATTERN, "");
    }

    private boolean nonNull(Object... objects) {
        for (Object obj : objects) {
            if (Objects.isNull(obj)) return false;
        }
        return true;
    }
}
