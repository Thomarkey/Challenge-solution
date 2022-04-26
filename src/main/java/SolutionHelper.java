import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionHelper {


    public static boolean wordIsCorrectAndNotYetInSolutionList(List<String> wordListToCheck, String wordToCheck, List<String> solutionList) {
        assertThat(wordListToCheck).isNotNull();
        assertThat(wordToCheck).isNotNull();

        return wordListToCheck.contains(wordToCheck) && !solutionList.contains(wordToCheck);
    }

}
