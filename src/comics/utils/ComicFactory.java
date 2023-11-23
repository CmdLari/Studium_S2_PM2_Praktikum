package comics.utils;

import comics.ScannerFremd.ComicFilmScannerAntonJonas;
import comics.ComicScanner;
import comics.scanner.ComicFilmScanner;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ComicFactory {

    public static ComicScanner makeAScanner(String choose, String uri) throws IOException {
        return switch (choose) {
            case "Eigen"  -> new ComicFilmScanner(uri);
            case "Fremd" -> new ComicFilmScannerAntonJonas(uri);
            default -> throw new IllegalArgumentException();
        };
    }
}
