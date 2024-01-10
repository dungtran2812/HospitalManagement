/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Models.Nurse;
import Models.Patient;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;

/**
 * A class that manages a HashMap of Nurse objects and provides various
 * operations on it. Extends HashMap<String, Nurse>.
 */
public class NurseMapManagement extends HashMap<String, Nurse> {

    /**
     * Creates a new Nurse object with the given staff ID and prompts the user
     * to enter other details.
     *
     * @param staffID The staff ID of the nurse.
     * @return The created Nurse object.
     */
    public Nurse createNurse(String staffID) {
        // Create a new Nurse object
        Nurse n = new Nurse();
        // Set nurse's information
        n.setStaffID(staffID);
        n.setName(InputFormatter.getString("Enter Name: "));
        n.setAge(InputFormatter.getPositiveValue("Enter Age: "));
        n.setGender(InputFormatter.getGender("Enter Gender (Male/Female/Others): "));
        n.setAddress(InputFormatter.getString("Enter Address: "));
        n.setPhone(InputFormatter.getFormattedString("Enter Phone: ", "0\\d{9}"));
        n.setDepartment(InputFormatter.getFormattedString("Enter Department: ", "^.{3,50}$"));
        n.setShift(InputFormatter.getShift("Enter Shift (Day/Night/Both): "));
        n.setSalary(InputFormatter.getPositiveValue("Enter Salary: "));

        return n;
    }

    /**
     * Finds nurses by name in the nurseMap and displays their information.
     *
     * @param name The name to search for.
     * @param nurseMap The HashMap containing nurses.
     */
    public void findNurse(String name, HashMap<String, Nurse> nurseMap) {
    System.out.println("+------+------------+-----------------+------------+------------+---------------------------------+-----------------------------+");
    System.out.println("| No.  | Nurse ID   | Department      | Shift      | Salary     |Name                             | Assigned Patients           |");
    System.out.println("+------+------------+-----------------+------------+------------+---------------------------------+-----------------------------+");

    int num = 0;
    for (Nurse nurse : nurseMap.values()) {
        if (nurse.getName().contains(name)) {
            num++;
            System.out.print("| ");
            System.out.format("%-5d", num);
            System.out.print(nurse.toString());
            System.out.println();
        }
    }
    System.out.println("+------+------------+-----------------+------------+------------+---------------------------------+-----------------------------+");
}
    /**
     * Updates the details of a nurse with the given staff ID.
     *
     * @param staffID The staff ID of the nurse to update.
     * @param nurseMap The HashMap containing nurses.
     */
    public void updateNurse(String staffID, HashMap<String, Nurse> nurseMap) {
        if (nurseMap.containsKey(staffID)) {
            Nurse nurseUpdate = nurseMap.get(staffID);

            nurseUpdate.setName(InputFormatter.getString("Enter Name: "));
            nurseUpdate.setAge(InputFormatter.getPositiveValue("Enter Age: "));
            nurseUpdate.setGender(InputFormatter.getGender("Enter Gender (Male/Female/Others): "));
            nurseUpdate.setAddress(InputFormatter.getString("Enter Address: "));
            nurseUpdate.setPhone(InputFormatter.getFormattedString("Enter Phone: ", "0\\d{9}"));
            nurseUpdate.setDepartment(InputFormatter.getFormattedString("Enter Department: ", "^.{3,50}$"));
            nurseUpdate.setShift(InputFormatter.getShift("Enter Shift (Day/Night/Both): "));
            nurseUpdate.setSalary(InputFormatter.getPositiveValue("Enter Salary: "));
        } else {
            System.out.println("The nurse does not exist");
            System.out.println("Update fail");
        }
    }

    /**
     * Deletes a nurse with the given staff ID from the nurseMap.
     *
     * @param staffID The staff ID of the nurse to delete.
     * @param nurseMap The HashMap containing nurses.
     */
    public void deleteNurse(String staffID, HashMap<String, Nurse> nurseMap) {
        boolean nurseExist = false;
        Nurse nurseDelete = null;

        System.out.println("Nurse info: ");
        for (Nurse n : nurseMap.values()) {

            if (n.getStaffID().equals(staffID)) {
                if (n.getAssignedPatients().isEmpty()) {
                    System.out.println(n.toString());
                    nurseExist = true;
                    nurseDelete = n;
                    break;
                } else {
                    System.out.println("Nurse have a task can not delete");
                }
            }

        }

        if (!nurseExist) {
            System.out.println("The nurse does not exist");
        } else if (nurseDelete.getAssignedPatients().size() > 0) {
            System.out.println("The nurse has a task");
        } else {

            if (InputFormatter.yesNoConfirm("Do you want to delete this nurse")) {
                nurseMap.remove(staffID);
                System.out.println("This nurse has been deleted");
            } else {
                System.out.println("This nurse has not been deleted");
            }

        }

    }

    /**
     * Saves the nurseMap to a file at the specified filePath.
     *
     * @param filePath The path to the file.
     */
    public void saveToFile(String filePath) {
        if (this.isEmpty()) {
            System.out.println("Nurse's list empty!");
            return;
        }
        try {
            FileOutputStream f = new FileOutputStream(filePath);
            ObjectOutputStream fo = new ObjectOutputStream(f);
            Iterator iter = this.keySet().iterator();
            while (iter.hasNext()) {
                Object key = iter.next();
                fo.writeObject(this.get(key));
            }
            f.close();
            fo.close();
            System.out.println("Nurse's list has been saved!");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Loads nurse data from a file at the specified filePath into the nurseMap.
     *
     * @param filePath The path to the file.
     * @throws ClassNotFoundException if the class of a serialized object cannot
     * be found.
     */
    public void loadFromFile(String filePath) throws ClassNotFoundException {
        if (!this.isEmpty()) {
            this.clear();
        }
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Nurse's list is empty!");
                return;
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Nurse nurse;
            boolean quit = false;
            while (!quit) {
                try {
                    nurse = (Nurse) objectInputStream.readObject();
                    this.put(nurse.getStaffID(), nurse);
                } catch (EOFException e) {
                    quit = true;
                    break; // Reached the end of the file, break out of the loop(End of File exception)
                }
            }
            fileInputStream.close();
            objectInputStream.close();
            System.out.println("Nurse's list has been loaded!");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

}
