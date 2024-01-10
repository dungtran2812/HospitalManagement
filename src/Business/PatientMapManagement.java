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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author asus
 */
public class PatientMapManagement extends HashMap<String, Patient> {

    transient Scanner sc = new Scanner(System.in);

   
    /**
     * Sorts the patients based on the selected sort field and order.
     *
     * @return A list of sorted patients.
     */
    public List<Patient> sortPatients() {
        List<Patient> sortedPatients = new ArrayList<>(this.values());
        boolean quit = false;
        String sortField = null;
        // Sort options
        while (!quit) {
            System.out.println("Sort Patients");
            System.out.println("1. Sort by ID");
            System.out.println("2. Sort by Name");

            sortField = InputFormatter.getString("Enter Option");
            if (sortField.equals("1")) {
                sortedPatients.sort(Comparator.comparing(Patient::getId));
                quit = true;
                break;
            } else if (sortField.equals("2")) {
                sortedPatients.sort(Comparator.comparing(Patient::getName));
                quit = true;
                break;
            } else {
                System.out.println("Invalid Option");
            }
        }
        quit = false;
        while (!quit) {
            System.out.println("1. Ascending");
            System.out.println("2. Descending");
            String order = InputFormatter.getString("Enter Option");
            System.out.println("Sorted Patients:");
            if (order.equals("1")) {
                quit = true;
                return sortedPatients;
            } else if (order.equals("2")) {
                quit = true;
                Collections.reverse(sortedPatients);
                return sortedPatients;
            } else {
                System.out.println("Invalid Option");
            }
        }
        return sortedPatients;
        
    }

    /**
     * Displays the patients within the specified date range.
     *
     * @param startDate The start date of the range.
     * @param endDate The end date of the range.
     * @param patientMap The patient map.
     */
    public void displayPatients(String startDate, String endDate, HashMap<String, Patient> patientMap) {
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    try {
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.format("|%-5s|%-12s|%-14s|%-30s|%-15s|%-30s|\n", "No.", "Patient Id", "Admission Date", "Full Name", "Phone", "Diagnosis");
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        int num = 0; // Initialize num to 0
        for (Patient patient : patientMap.values()) {
            Date admissionDate = dateFormat.parse(patient.getAdmissionDate());
            if (admissionDate.after(dateFormat.parse(startDate)) && admissionDate.before(dateFormat.parse(endDate))) {
                num++; // Increment num for each valid patient
                
                System.out.format("|%-5d|%-12s|%-14s|%-30s|%-15s|%-30s|\n",
                        num, patient.getId(), patient.getAdmissionDate(), patient.getName(), patient.getPhone(), patient.getDiagnosis());
            }
        }
    } catch (ParseException ex) {
        System.out.println("Invalid date format");
    }
}

    /**
     * Saves the patient's list to a file.
     *
     * @param filePath The path of the file to save.
     */
    public void saveToFile(String filePath) {
        if (this.isEmpty()) {
            System.out.println("Patient's list empty!");
            return;
        }
        try {
            FileOutputStream f = new FileOutputStream(filePath);
            ObjectOutputStream fo = new ObjectOutputStream(f);
            // Save each patient object to the file
            Iterator iter = this.keySet().iterator();
            while (iter.hasNext()) {
                Object key = iter.next();
                fo.writeObject(this.get(key));
            }
            f.close();
            fo.close();
            System.out.println("Patient's list has been saved!");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Loads the patient's list from a file.
     *
     * @param filePath The path of the file to load.
     * @throws ClassNotFoundException If the class of a serialized object cannot
     * be found.
     */
    public void loadFromFile(String filePath) throws ClassNotFoundException {
        //check whether the collection (or any data structure) is empty or not
        if (!this.isEmpty()) {
            clear();
        }
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Patient's list is empty!");
                return;
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Patient patient;
            // Read patient objects from the file and add them to the patient map
            boolean quit = false;
            while (!quit) {
                try {
                    patient = (Patient) objectInputStream.readObject();
                    this.put(patient.getId(), patient);
                } catch (EOFException e) {
                    quit = true;
                    break; // Reached the end of the file, break out of the loop(End of File exception)
                }
            }
            fileInputStream.close();
            objectInputStream.close();
            System.out.println("Patient's list has been loaded!");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
