/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Nurse class represents a nurse in the hospital management system. It
 * extends the Person class and implements the Serializable interface for binary
 * file serialization.
 */
public class Nurse extends Person implements Serializable {

    String staffID;
    String department;
    String shift;
    long salary;

    private ArrayList<Patient> assignedPatients = new ArrayList<>();

    public Nurse(String staffID, String department, String shift, long salary, String name, int age, String gender, String address, String phone) {
        super(name, age, gender, address, phone);
        this.staffID = staffID;
        this.department = department;
        this.shift = shift;
        this.salary = salary;
    }

    /**
     * Constructs an empty Nurse object with default values for attributes.
     */
    public Nurse() {
        super();
        staffID = "";
        department = "";
        shift = "";
        salary = 0;
    }

    /**
     * Retrieves the staff ID of the nurse.
     *
     * @return the staff ID
     */
    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public long getSalary() {
        return salary;
    }

    public ArrayList<Patient> getAssignedPatients() {
        return assignedPatients;
    }

    public void setAssignedPatients(ArrayList<Patient> assignedPatients) {
        this.assignedPatients = assignedPatients;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    /**
     * Assigns a patient to the nurse. The patient will be added to the assigned
     * patients list if there is room for more patients. The nurse will also be
     * added as an assigned nurse to the patient.
     *
     * @param patient the patient to assign
     */
    public void assignedPatient(Patient patient) {
        if (assignedPatients.size() <= 2 && !assignedPatients.contains(patient)) {
            assignedPatients.add(patient);
            patient.assignedNurses(this);
        }
    }

    /**
     * Removes a patient from the nurse's assigned patients list. The nurse will
     * also be removed as an assigned nurse from the patient.
     *
     * @param patient the patient to remove
     */
    public void removePatient(Patient patient) {
        assignedPatients.remove(patient);
        patient.removeAssignedNurse(this);
    }

    @Override
   
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("| %-10s | %-15s | %-10s | %-10s |", staffID, department, shift, salary));
    sb.append(String.format(" Nurse Name: %-20s|", getName()));
    if (!getAssignedPatients().isEmpty()) {
        
        for (Patient patient : getAssignedPatients()) {
            sb.append(String.format("%-8s", patient.getId()));
        }
    }
    return sb.toString();
}

}
