import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class main {

    static int currentWordLength;
    static String currentWord;
    static String candidateWord;
    static String candidateWordBackwards;

    static List<String> originalWordList = new ArrayList<>();
    static List<String> solutionList = new ArrayList<>();
    static List<String> wordList = new ArrayList<>();
//    static List<String> tempWordList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Map<String, String> input = InputHelper.askInput();

        String fileName = input.get("fileName");
        int wordLengthToCheck = Integer.parseInt(input.get("wordLengthToCheck"));

        System.out.println("Solution: ");
        returnSolution(fileName, wordLengthToCheck);
    }

    public static void returnSolution(String fileName, int wordLengthToCheck) throws IOException {
//        //create new list and add words read from .txtfile
        //fill list with words read from input file
        Files.lines(Path.of(fileName))
                .filter(word -> !word.isEmpty())
                .forEach(originalWordList::add);

        //remove duplicates and too long words from list and use this new list
        //save the original list as wordListToCheck (because we use the wordList later and remove words while checking)
        wordList = ListHelper.removeWordsFromTheListLargerThan(originalWordList, wordLengthToCheck);
        wordList = ListHelper.returnTheListWithoutDuplicates(wordList);
        List<String> wordListToCheck = new ArrayList<>(wordList);

        assertThat(wordList).isEqualTo(wordListToCheck);
        assertThat(wordList.size() > 0).isTrue();

        for (int i = 0; i < wordList.size(); i++) {
            currentWord = wordList.get(i);
            currentWordLength = currentWord.length();

            //single combination
            if (wordList.get(i).length() == wordLengthToCheck) {
                if (SolutionHelper.wordIsCorrectAndNotYetInSolutionList(wordListToCheck, currentWord, solutionList)) {
                    solutionList.add(currentWord);
                }
                wordList.remove(currentWord);
            }
        }

        //loop over the list
        for (int i = 0; i < wordList.size(); i++) {
            currentWord = wordList.get(i);
            currentWordLength = currentWord.length();

            //single combination
            if (wordList.get(i).length() == wordLengthToCheck) {
                if (SolutionHelper.wordIsCorrectAndNotYetInSolutionList(wordListToCheck, currentWord, solutionList)) {
                    solutionList.add(currentWord);
                }
                wordList.remove(currentWord);

            } else {
                //we remove this word from the list, so it neglects this same word
                wordList.remove(currentWord);
                //then we look for other words in the list and check if the word size with current word is 6 char
                for (String word : wordList) {
                    candidateWord = currentWord + word;
                    if (candidateWord.length() == wordLengthToCheck) {
                        //check if word is correct and already in list or not, if not: you can add
                        if (SolutionHelper.wordIsCorrectAndNotYetInSolutionList(wordListToCheck, candidateWord, solutionList)) {
                            solutionList.add(candidateWord);
                        }
                        //check all variations (in this double combination case: only 2 possibilities)
                        //in case of multiple words: create new function in WordBuilder class and use this (TODO)
                        candidateWordBackwards = word + currentWord;
                        if (SolutionHelper.wordIsCorrectAndNotYetInSolutionList(wordListToCheck, candidateWordBackwards, solutionList)) {
                            solutionList.add(candidateWordBackwards);
                        }
                    }

                    //TRAIN OF THOUGHTS: multiple words combined
//                     else if (candidateWord.length() < wordLengthToCheck) {
//                        tempWordList = wordList;
//                        tempWordList.remove(word);
//                        //DO THE SAME AS ABOVE -> =recursion?
//                        }
                }
            }
        }

        //visual confirmation
        if (!solutionList.isEmpty()) {
            for (String word : solutionList) {
                System.out.println(word);
            }
        } else {
            System.out.println("no solutions available!");
        }

    }
}
