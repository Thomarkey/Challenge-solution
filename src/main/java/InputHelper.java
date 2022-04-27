import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


public class InputHelper {

    public static Map<String, String> askInput() {
        Map<String, String> input = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        //mimic file upload and TODO: add validation (.txt file for example)
        System.out.println("Enter attached filename");
        String fileName = scanner.nextLine();

        assertThat(Path.of(fileName)).isNotEmptyFile();


        int wordLengthToCheck;
        do {
            System.out.println("Enter a positive numeric wordlength to check");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number!");
                System.out.println("Enter a positive numeric wordlength to check");
                scanner.next();
            }
            wordLengthToCheck = scanner.nextInt();
        } while (wordLengthToCheck < 0);

        input.put("fileName", fileName);
        input.put("wordLengthToCheck", String.valueOf(wordLengthToCheck));

        return input;
    }
}
