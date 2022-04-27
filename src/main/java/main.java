import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


public class main {


//    static final int wordLengthToCheck = 6;
//    static final String fileName = "input.txt";

    static int currentWordLength;
    static String currentWord;
    static String candidateWord;
    static String candidateWordBackwards;

    static List<String> solutionList = new ArrayList<>();
    static List<String> wordList = new ArrayList<>();
//    static List<String> tempWordList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Scanner myObj = new Scanner(System.in);

        //mimic file upload and TODO: add validation (.txt file for example)
        System.out.println("Enter attached filename");
        String fileName = myObj.nextLine();

        //TODO: make sure input is a number and > 0
        System.out.println("Enter numeric wordlength to check");
        int wordLengthToCheck = Integer.parseInt(myObj.nextLine());

        System.out.println("Solution: ");
        returnSolution(fileName, wordLengthToCheck);
    }

    public static void returnSolution(String fileName, int wordLengthToCheck) throws IOException {
        //create new list and add words read from .txtfile
        List<String> originalWordList = new ArrayList<>();

        //assert that file is not empty
        assertThat(Path.of(fileName)).isNotEmptyFile();
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
        for (String word : solutionList) {
            System.out.println(word);
        }

    }


}
