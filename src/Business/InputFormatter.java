/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * A utility class for formatting user input.
 */
public class InputFormatter {

    ArrayList<String> staffIDList = new ArrayList<>();

    static Scanner sc = new Scanner(System.in);

    /**
     * Reads a string input from the user.
     *
     * @param message The message to display to the user.
     * @return The string input from the user.
     */
    public static String getString(String message) {
        String input;
        boolean isValidString;
        do {
            isValidString = true;
            System.out.println(message);
            input = sc.nextLine();
            if (input.isEmpty()) {
                isValidString = false;
                System.out.println("Input can not be null");
            }
        } while (!isValidString);
        return input;
    }

    /**
     * Reads a string input from the user and validates it against a specified
     * pattern.
     *
     * @param message The message to display to the user.
     * @param pattern The regular expression pattern to validate the input
     * against.
     * @return The valid string input from the user.
     */
    public static String getFormattedString(String message, String pattern) {
        String input;

        do {
            System.out.println(message);
            input = sc.nextLine();
            if (!removeWhitespace(input).matches(pattern)) {
                System.out.println("Input Invalid");
            }

        } while (!removeWhitespace(input).matches(pattern) || input.isEmpty());
        return input;
    }

    /**
     * Reads a positive integer input from the user.
     *
     * @param message The message to display to the user.
     * @return The positive integer input from the user.
     */
    public static int getPositiveValue(String message) {
        int input = 0;

        boolean isValidInput;
        do {
            System.out.println(message);
            String userInput = sc.nextLine();

            try {
                input = Integer.parseInt(userInput);
                if (input > 0) {
                    isValidInput = true;
                } else {
                    System.out.println("Value must be a positive number");
                    isValidInput = false;
                }
            } catch (Exception e) {
                System.out.println("Invalid value");
                isValidInput = false;

            }
        } while (!isValidInput);

        return input;
    }

    /**
     * Removes whitespace characters from a given string.
     *
     * @param input The string to remove whitespace from.
     * @return The string without whitespace.
     */
    public static String removeWhitespace(String input) {

        String trimmed = input.trim();
        String noWhitespace = trimmed.replaceAll("\\s+", "");

        return noWhitespace;
    }

    /**
     * Asks the user for a yes/no confirmation.
     *
     * @param message The message to display to the user.
     * @return true if the user confirms with 'y', false if the user confirms
     * with 'n'.
     */
    public static boolean yesNoConfirm(String message) {
        String choice;
        boolean quit = false;
        System.out.println(message);
        do {
            choice = sc.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                quit = true;
                return true;
            } else if (choice.equalsIgnoreCase("n")) {
                quit = true;
                return false;
            } else {
                System.out.println("Invalid Choice");
            }
        } while (!quit);
        return false;
    }
    private static final String dateFormat = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)[0-9]{2,2}$";

    /**
     * Reads a date input from the user and validates its format.
     *
     * @param msg The message to display to the user.
     * @return The valid date input from the user.
     */
    public static String getDate(String msg) {
        boolean quit = false;
        String date = null;
        while (!quit) {
            try {
                System.out.print(msg);
                date = sc.nextLine();
                Pattern pt = Pattern.compile(dateFormat);
                if (pt.matcher(date).find() && isValidDate(date)) {
                    quit = true;
                    return date;
                }
                throw new Exception();
            } catch (Exception ex) {
                System.out.println("Wrong date format!");
            }
        }
        return date;
    }

    /**
     * Checks if a given date is valid.
     *
     * @param date The date to check in the format "dd-mm-yyyy".
     * @return true if the date is valid, false otherwise.
     */
    public static boolean isValidDate(String date) {
        String[] split = date.split("[-/. ]");
        int day = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        int year = Integer.parseInt(split[2]);
        int maxDay = 30;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            maxDay = 31;
        }
        if (month == 2) {
            if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
                maxDay = 29;
            } else {
                maxDay = 28;
            }
        }
        return day <= maxDay;
    }
    public static String getGender(String message) {
        String input;
        do {
            System.out.print(message);
            input = sc.nextLine().trim().toLowerCase();
        } while (!input.equals("male") && !input.equals("female") && !input.equals("others"));
        return input;
    }

    public static String getShift(String message) {
        String input;
        do {
            System.out.print(message);
            input = sc.nextLine().trim().toLowerCase();
        } while (!input.equals("day") && !input.equals("night") && !input.equals("both"));
        return input;
    }
}
