/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import Business.Hospital;
import Business.InputFormatter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The Menu class provides a command-line interface for interacting with the hospital management system.
 */
public class Menu {

    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException, ParseException {
        Hospital h1 = new Hospital();
        int choice;
        boolean quit = false;
        do {
            System.out.println("=======MENU======");
            System.out.println("1 - Add new nurse");
            System.out.println("2 - Find a nurse");
            System.out.println("3 - Update a nurse");
            System.out.println("4 - Delete a nurse");
            System.out.println("5 - Add a patient");
            System.out.println("6 - Display patients");
            System.out.println("7 - Sort patients");
            System.out.println("8 - Save data");
            System.out.println("9 - Load data");
            System.out.println("10 - Quit");
            System.out.println("=================");
            System.out.println("Enter Option: ");

            Scanner sc = new Scanner(System.in);
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid option.");
                sc.nextLine(); // Clear the invalid input from the scanner
                continue;
            }

            switch (choice) {
                case 1:
                    h1.addNurse();
                    break;
                case 2:
                    h1.findNurse();
                    break;
                case 3:
                    h1.updateNurse();
                    break;
                case 4:
                    h1.deleteNurse();
                    break;
                case 5:
                    h1.addPatient();
                    break;
                case 6:
                    h1.displayPatient();
                    break;
                case 7:
                    h1.sortPatients(h1);
                    break;
                case 8:
                    h1.saveFile();
                    break;
                case 9:
                    h1.loadFile();
                    break;
                case 10:
                    quit = true;
                    h1.quit();
                    
                case 11:
                    h1.printNurseWith2Patient();
                    break;
                default:
                    System.out.println("Invalid Option. Please enter a valid option.");
            }
        } while (!quit);

    }
}
