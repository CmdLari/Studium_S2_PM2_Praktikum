package comics.ScannerFremd;

import comics.ComicScanner;
import utils.YearInterval;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;

public class ComicFilmScannerAntonJonas implements ComicScanner {

    private final Scanner scanner;

    private final Pattern TBODY_PATTERN = Pattern.compile("<tbody>(?<tbody>.*?)</tbody>", Pattern.MULTILINE | Pattern.DOTALL);

    public ComicFilmScannerAntonJonas(String uri) throws MalformedURLException, IOException {
        scanner = new Scanner(new URL(uri).openStream(), StandardCharsets.UTF_8);
    }

    public ComicFilmScannerAntonJonas(Path path) throws MalformedURLException, IOException {
        this(path.toAbsolutePath().toString());
    }

    public Map<String, Map<YearInterval, List<String>>> contentToComicMap() {
        final ComicFilmParser parser = new ComicFilmParser();
        scanner.findAll(TBODY_PATTERN).forEach(parser::parse);
        scanner.close();
        return parser.result();
    }


}
