import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class ListHelper {


    public static List<String> returnTheListWithoutDuplicates(List<String> originalList) {
        assertThat(originalList).isNotNull();
        return originalList
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public static List<String> removeWordsFromTheListLargerThan(List<String> list, int charSize) {
        assertThat(list).isNotNull();
        return list
                .stream()
                .filter(word -> word.length() <= charSize)
                .collect(Collectors.toList());
    }

}
