package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Input the length of the secret code:");
        Scanner scanner = new Scanner(System.in);

        String lengthStr = scanner.nextLine();

        try {
            int length = Integer.parseInt(lengthStr);

            if (length > 0 && length <= 36) {
                System.out.println("Input the number of possible symbols in the code:");

                lengthStr = scanner.nextLine();

                try {
                    int codeBaseLength = Integer.parseInt(lengthStr);
                    if (codeBaseLength <= 36 && codeBaseLength >= length) {

                        String[] possibleCodeDigits = getCodeDigits(codeBaseLength);

                        int i = 1;
                        boolean continuePlay;
                        String code = getCode(length, possibleCodeDigits);

                        System.out.println("Okay, let's start a game!");

                        do {
                            System.out.printf("Turn %d:\n", i);
                            String guess = scanner.next();
                            continuePlay = gameLog(code, guess, length);
                            i++;
                        } while (continuePlay);
                        System.out.println("Congratulations! You guessed the secret code.");
                    } else if (codeBaseLength < length){
                        System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", length, codeBaseLength);
                    } else {
                        System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                    }
                } catch (Exception e) {
                    System.out.printf("Error: \"%s\" isn't a valid number.", lengthStr);
                }
            } else {
                System.out.printf("Error: \"%s\" isn't a valid number.", length);
            }
        } catch (Exception e) {
            System.out.printf("Error: \"%s\" isn't a valid number.", lengthStr);
        }


    }

    public static String[] getCodeDigits(int length) {
        String[] digits = new String[length];

        int j = 0;
        while (j < length) {

            for (char i = '0'; i <= '9'; i++) {
                digits[j] = "" + i;
                j++;
                if (j == length) break;
            }

            if (j == length) break;

            for (char i = 'a'; i <= 'z'; i++) {
                digits[j] = "" + i;
                j++;
                if (j == length) break;
            }

        }

        return digits;
    }

    public static String getCode(int length, String[] digits) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        StringBuilder stars = new StringBuilder();

        int j = 0;

        while (j < length) {

            int randInt = random.nextInt(0, digits.length);

            if (code.isEmpty() && digits[randInt].equals("0")) {
                continue;
            }

            if (code.toString().contains(digits[randInt])) {
                continue;
            }

            code.append(digits[randInt]);
            stars.append("*");
            j++;

        }

        if (digits.length > 10) {
            System.out.printf("The secret is prepared: %s (0-9, a-%s).", stars, digits[digits.length - 1]);
        } else {
            System.out.printf("The secret is prepared: %s (0-%s).", stars, digits[digits.length - 1]);
        }
        System.out.println();

        return code.toString();
    }


    public static boolean gameLog(String code, String guess, int length) {

        int bullCount = 0;
        int cowCount = 0;

        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == code.charAt(i)) {
                bullCount++;
            } else if (code.contains("" + guess.charAt(i))) {
                cowCount++;
            }
        }

        String statement = getStatement(bullCount, cowCount);

        System.out.printf("%s\n", statement);

        return bullCount != length;
    }

    private static String getStatement(int bullCount, int cowCount) {
        String statement = "Grade: ";

        if (bullCount == 0 && cowCount == 0) {
            statement += "None.";
        } else if (bullCount > 0 && cowCount > 0) {
            statement += String.format("%d %s and %d %s",
                    bullCount,
                    bullCount > 1 ? "bulls" : "bull",
                    cowCount,
                    cowCount > 1 ? "cows" : "cow"
            );
        } else if (bullCount > 0) {
            statement += String.format("%d %s", bullCount, bullCount > 1 ? "bulls" : "bull");
        } else if (cowCount > 0) {
            statement += String.format("%d %s", cowCount, cowCount > 1 ? "cows" : "cow");
        }

        return statement;
    }
}