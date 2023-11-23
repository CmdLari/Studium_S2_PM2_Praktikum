package comics;

import utils.YearInterval;

import java.util.List;
import java.util.Map;

public interface ComicScanner {

    Map<String, Map<YearInterval, List<String>>> contentToComicMap();

}
