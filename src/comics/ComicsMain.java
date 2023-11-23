package comics;

import comics.utils.ComicFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ComicsMain {
    private static final String DIR_PREFIX = "";

    public static void main(String[] args) throws MalformedURLException, IOException {

        System.out.print("\n//////////////////////////////////////////////////////////////////////////////////");
        Path path = Paths.get("Praktikum2/ress/list.html");

        ComicScanner eigenerScanner = ComicFactory.makeAScanner("Eigen","file:///"+path.toAbsolutePath());
        ComicScanner fremderScanner = ComicFactory.makeAScanner("Fremd","file:///"+path.toAbsolutePath());

        long starttimeAry = System.nanoTime();
        eigenerScanner.contentToComicMap();
        long endtimeAry = System.nanoTime();
        System.out.printf("\nTime to fill comic map (own): \n%sns", (endtimeAry - starttimeAry));

        System.out.printf("\n//////////////////////////////////////////////////////////////////////////////////");

        long starttimeLinky = System.nanoTime();
        fremderScanner.contentToComicMap();
        long endtimeLinky = System.nanoTime();
        System.out.printf("\nTime to fill comic map (stolen): \n%sns", (endtimeLinky - starttimeLinky));
    }
}

        /////1OLD CODE/////

        /*
         * Wir arbeiten mit einer lokalen Datei / Kopie einer Wikipediaseite.
         * Referenz auf die Datei wird als URI String übergeben: file:///<absoluter Pfad zur Datei>
         * Path wiki3DFilmLocal = Paths.get("Listevon3D-Filmen–Wikipedia.html") erzeugt einen relativen
         * Pfad zur der Datei (Bezug das aktuelle Projekt)
         * wiki3DFilmLocal.toAbsolutePath() erzeugt den absoluten Pfad, der als
         * Argument übergeben wird.
         */
//        Path wikiComicLocal = Paths.get("Liste von Comicverfilmungen.html");
//        ComicFilmScanner wp1 = new ComicFilmScanner("file:///" + wikiComicLocal.toAbsolutePath());
        //wp1.echoPage();

        /*
         * Parsen der Seite und Einsammeln der Liste von 3D-Filmen eines Jahres / eines Zeitraums
         * in einem Verzeichnis (einer Java-Map).
         */
//        long start = System.currentTimeMillis();
//        Map<String, Map<YearInterval,List<String>>> comicFilmMap = wp1.contentToComicMap();
//        System.out.println("Duration: " + (System.currentTimeMillis() - start) + "ms");
//        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
//        ppMap(comicFilmMap);
//    }

//    private static <K extends Comparable<? super K>> void ppMap(Map<K, Map<YearInterval,List<String>>> aMap) {
//        List<K> al = new ArrayList<K>(aMap.keySet());
//        Collections.sort(al);
//        for (K key : al) {
//            System.out.printf("%s->", key);
//            for (Map.Entry<YearInterval,List<String>> entry : aMap.get(key).entrySet()) {
//                System.out.printf("%s %s ", entry.getKey(),entry.getValue().toString());
//            }
//            System.out.println();
//        }
//    }
//}
