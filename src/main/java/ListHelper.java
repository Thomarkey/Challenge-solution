import java.util.List;
import java.util.stream.Collectors;

public class ListHelper {


    public static List<String> returnTheListWithoutDuplicates(List<String> originalList) {
        return originalList
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public static List<String> removeWordsFromTheListLargerThan(List<String> list, int charSize) {
        return list
                .stream()
                .filter(word -> word.length() <= charSize)
                .collect(Collectors.toList());
    }


}
