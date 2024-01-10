/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Models.Nurse;
import Models.Patient;
import java.awt.Choice;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * The Hospital class represents a hospital management system.
 */
public class Hospital {

    Boolean hasChange = false;
    Scanner sc = new Scanner(System.in);
    NurseMapManagement nurseMap;
    PatientMapManagement patientMap;

    public Hospital() {
        nurseMap = new NurseMapManagement();
        patientMap = new PatientMapManagement();
    }

    /**
     * Adds a nurse to the hashmap.
     */
    public void addNurse() {
        String staffID;
        boolean quit = false;
        do {
            do {

                staffID = InputFormatter.getFormattedString("Enter Staff ID (Nxxxx): ", "(?i)^N\\d{4}$");
                if (!nurseMap.containsKey(staffID) && !staffID.isEmpty()) {
                    quit = true;
                    break;
                } else {
                    System.out.println("Staff ID already exist");
                }
            } while (!quit);
            nurseMap.put(staffID, nurseMap.createNurse(staffID));
            System.out.println("Nurse added successfully");
        } while (InputFormatter.yesNoConfirm("Do you want to add more nurse(Y/N)"));
        hasChange = true;
    }

    /**
     * Finds nurses based on name or part of the name.
     */
    public void findNurse() {
        String name;
        System.out.println("Find nurse by enter the name or part of the name: ");
        name = sc.nextLine();
        nurseMap.findNurse(name, nurseMap);
    }

    /**
     * Updates a nurse based on staff ID.
     */
    public void updateNurse() {
        System.out.println("Enter staffID to update: ");
        String staffID = sc.nextLine();
        nurseMap.updateNurse(staffID, nurseMap);
        hasChange = true;
    }

    /**
     * Deletes a nurse based on staff ID.
     */
    public void deleteNurse() {
        String staffID;
        System.out.println("Delete nurse by enter StaffID: ");
        staffID = sc.nextLine();
        nurseMap.deleteNurse(staffID, nurseMap);
        hasChange = true;
    }

    /**
     * Adds a patient to the patient map.
     *
     * @throws ParseException if there is an error parsing dates.
     */
    public void addPatient() throws ParseException {
        String id;

        do {
            do {
                id = InputFormatter.getFormattedString("Enter ID (Pxxxx): ", "(?i)^P\\d{4}$");
                if (!patientMap.containsKey(id) && !id.isEmpty()) {
                    break;
                } else {
                    System.out.println("Patient ID already exists");
                }
            } while (true);

            int assignedNursesCount = 0; // Count the number of assigned nurses

            // Check if there are at least 2 nurses available with less than 2 assigned patients
            boolean enoughNursesAvailable = false;
            for (Nurse nurse : nurseMap.values()) {
                if (nurse.getAssignedPatients().size() < 2) {
                    assignedNursesCount++;
                    if (assignedNursesCount >= 2) {
                        enoughNursesAvailable = true;
                        break;
                    }
                }
            }

            if (!enoughNursesAvailable) {
                System.out.println("Not enough nurses available to assign to the patient. Please add more nurses first.");
                break;
            }

            String name = InputFormatter.getString("Enter Name: ");
            int age = InputFormatter.getPositiveValue("Enter Age: ");
            String gender = InputFormatter.getGender("Enter Gender (Male/Female/Others): ");
            String address = InputFormatter.getString("Enter Address: ");
            String phone = InputFormatter.getFormattedString("Enter Phone: ", "0\\d{9}");
            String diagnosis = InputFormatter.getString("Enter Diagnosis: ");
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String admissionDate;
            String dischargeDate;

            // Loop until Admission date before or equal Discharge date
            while (true) {
                admissionDate = InputFormatter.getDate("Enter Admission Date (dd/MM/yyyy): ");
                dischargeDate = InputFormatter.getDate("Enter Discharge Date (dd/MM/yyyy): ");
                if (dateFormat.parse(admissionDate).before(dateFormat.parse(dischargeDate)) || admissionDate.equals(dischargeDate)) {
                    break;
                } else {
                    System.out.println("Admission date must be before Discharge date");
                }
            }

            Patient info = new Patient(id, diagnosis, admissionDate, dischargeDate, name, age, gender, address, phone);

            // Assign up to 2 nurses to the patient
            assignedNursesCount = 0;
            while (assignedNursesCount < 2) {
                String staffID = InputFormatter.getFormattedString("Enter staffID to assign (2 nurses each patient): ", "(?i)^N\\d{4}$");

                // Check if the nurse is available and not already assigned to the maximum number of patients
                if (nurseMap.containsKey(staffID) && nurseMap.get(staffID).getAssignedPatients().size() < 2) {
                    info.assignedNurses(nurseMap.get(staffID));
                    assignedNursesCount++;
                } else {
                    System.out.println("Invalid staffID or nurse already assigned maximum patients");
                }
            }

            Patient patient = new Patient(info.getId(), info.getDiagnosis(), info.getAdmissionDate(), info.getDischargeDate(), info.getName(), info.getAge(), info.getGender(), info.getAddress(), info.getPhone());

            patientMap.put(patient.getId(), patient);

        } while (InputFormatter.yesNoConfirm("Do you want to add more patients (Y/N)"));

        hasChange = true;
    }

    /**
     * Displays patients within a date range.
     *
     * @throws ParseException if there is an error parsing dates.
     */
    public void displayPatient() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String startDate = InputFormatter.getDate("Enter start date(dd/MM/yyyy): ");
        String endDate;
        while (true) {
            endDate = InputFormatter.getDate("Enter end date(dd/MM/yyyy): ");
            if (dateFormat.parse(endDate).after(dateFormat.parse(startDate))) {
                patientMap.displayPatients(startDate, endDate, patientMap);
                break;
            } else {
                System.out.println("End date must be before start date!");
            }
        }

    }

    /**
     * Sorts and displays patients.
     */
    public void sortPatients(Hospital hospital) {

        for (Patient patient : patientMap.sortPatients()) {
            System.out.println(patient);
        }
    }

    /**
     * Saves data to a file.
     */
    public void saveFile() {
        nurseMap.saveToFile("src\\info\\nurse.dat");
        patientMap.saveToFile("src\\info\\patient.dat");
    }

    /**
     * Loads data from a file.
     *
     * @throws ClassNotFoundException if the class is not found during deserialization.
     */
    public void loadFile() throws ClassNotFoundException {
        nurseMap.loadFromFile("src\\info\\nurse.dat");
        patientMap.loadFromFile("src\\info\\patient.dat");

    }
    
    /**
     * Quits the program.
     */
    public void quit() {
        if (InputFormatter.yesNoConfirm("Do you want to exit program ? (Y/N)")) {
            if (hasChange) {
                if (InputFormatter.yesNoConfirm("Data has changed, Do you want to save data? (Y/N)")) {
                    this.saveFile();
                }

            }

            System.exit(0);
        }
    }
    public void printNurseWith2Patient() {
        for (Nurse n : nurseMap.values()) {
            if (n.getAssignedPatients().size() == 2) {
                System.out.println(n);
            }
        }
    }
}
