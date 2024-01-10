/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Patient extends Person implements Serializable {

    private String id;
    private String diagnosis;
    private String admissionDate;
    private String dischargeDate;
    
    private ArrayList<Nurse> nurseAssignedList = new ArrayList<>();

    public Patient() {

    }

    public Patient(String id, String diagnosis, String admissionDate, String dischargeDate, String name, int age, String gender, String address, String phone) {
        super(name, age, gender, address, phone);
        this.id = id;
        this.diagnosis = diagnosis;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
        
    }

    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(String dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public ArrayList<Nurse> getNurseAssignedList() {
        return nurseAssignedList;
    }

    public void setNurseAssignedList(ArrayList<Nurse> nurseAssignedList) {
        this.nurseAssignedList = nurseAssignedList;
    }

    /**
     * Assigns a nurse to the patient if there is room for more assigned nurses.
     * The patient will also be added to the nurse's assigned patients list.
     *
     * @param nurse the nurse to assign
     */
    public void assignedNurses(Nurse nurse) {
        if (nurseAssignedList.size() < 2 && !nurseAssignedList.contains(nurse)) {
            nurseAssignedList.add(nurse);
            nurse.assignedPatient(this);
        }
    }

    public void removeAssignedNurse(Nurse nurse) {
        nurseAssignedList.remove(nurse);
        nurse.removePatient(this);
    }

    @Override
    public String toString() {
        return String.format("| %-12s | %-14s | %-30s | %-15s | %-30s |",
               getId(), getAdmissionDate(), getName(), getPhone(), getDiagnosis());
    }
}
