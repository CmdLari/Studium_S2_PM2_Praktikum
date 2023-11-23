package comics.scanner;

import comics.ComicScanner;
import utils.YearInterval;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Year;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.*;

public class ComicFilmScanner implements ComicScanner {

    private final static Pattern COMIC_UND_FILM = Pattern.compile("(?:<td(?: rowspan=\"\\d+\")?>(?<comic>.*?)</td>)?<td>(?<film>.*?)</td>");
    private final static Pattern FILM_UND_JAHR = Pattern.compile("(?<film>.*?) \\((?:.*?,.*?)?(?<date>\\d+)(?:–(?<dateEnd>\\d+))?");

    private final Scanner scanner;
    private final Map<String, Map<YearInterval, List<String>>> comicMap = new HashMap<>();


    public ComicFilmScanner(String uri) throws MalformedURLException, IOException {
        scanner = new Scanner(new URL(uri).openStream(), StandardCharsets.UTF_8);
    }

    public ComicFilmScanner(Path path) throws MalformedURLException, IOException {
        this(path.toAbsolutePath().toString());
    }

    public Map<String, Map<YearInterval, List<String>>> contentToComicMap() {
        Pattern BEFORE_TABLE = Pattern.compile("<tbody>", Pattern.MULTILINE | Pattern.DOTALL);
        scanner.useDelimiter(BEFORE_TABLE);
        scanner.next();
        scanner.useDelimiter(Pattern.compile("</tr>.*?<tr>", Pattern.MULTILINE | Pattern.DOTALL));

        String comic = null;
        while (scanner.hasNext()) {
            String s = scanner.next().replaceAll("<a.*?>|</a>|<i>|</i>|\\r?\\n|\\r/g", "");
            Matcher comicUndFilm = COMIC_UND_FILM.matcher(s);
            if (comicUndFilm.find()) {
                String film = comicUndFilm.group("film");
                Matcher filmUndJahr = FILM_UND_JAHR.matcher(film);
                if (filmUndJahr.find()) {
                    String filmtitel = filmUndJahr.group("film");
                    String jahr = filmUndJahr.group("date");
                    String endJahr = filmUndJahr.group("dateEnd");
                    YearInterval filmjahr = filmjahrrechner(jahr, endJahr);
                    if (comicUndFilm.group("comic") != null) {
                        comic = comicUndFilm.group("comic");
                    }
                    mapFiller(comic, filmjahr, filmtitel);
                } else {
//                    System.err.printf("Could not parse data %s%n", film);
                }
            }
        }
        scanner.close();
        return comicMap;
}

    private YearInterval filmjahrrechner (String jahr, String endJahr){
        if (isNull(endJahr)){
            return new YearInterval(Year.parse(jahr));
        }
        else {
            return new YearInterval(Year.parse(jahr), Year.parse(endJahr));
        }
    }

    private void mapFiller(String comic, YearInterval jahr, String film){
        if(!comicMap.containsKey(comic)){
            comicMap.put(comic, new HashMap<>());
        }
        if(!comicMap.get(comic).containsKey(jahr)) {
            comicMap.get(comic).put(jahr, new ArrayList<>(List.of(film)));
        }
        else {
            comicMap.get(comic).get(jahr).add(film);
        }
    }
}
