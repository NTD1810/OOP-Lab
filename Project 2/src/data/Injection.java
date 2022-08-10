/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import manager.CheckValid;

/**
 *
 * @author Nguyễn Thùy Dương
 */
public class Injection implements Comparable, Serializable {

    String injectionID;
    String firstInjectionPlace;
    String secondInjectionPlace;
    Date firstInjectionDate;
    Date secondInjectionDate;
    Student studentID;
    Vaccine vaccineID;

    public Injection() {
        studentID = new Student();
        vaccineID = new Vaccine();
    }

    public Injection(String injectionID, String firstInjectionPlace, String secondInjectionPlace, Date firstInjectionDate, Date secondInjectionDate, Student studentID, Vaccine vaccineID) {
        this.injectionID = injectionID;
        this.firstInjectionPlace = firstInjectionPlace;
        this.secondInjectionPlace = secondInjectionPlace;
        this.firstInjectionDate = firstInjectionDate;
        this.secondInjectionDate = secondInjectionDate;
        this.studentID = studentID;
        this.vaccineID = vaccineID;
    }


    public Injection(String injectionID) {
        this.injectionID = injectionID;
    }

    public Injection(String injectionID, String firstInjectionPlace, Date firstInjectionDate, Student studentID, Vaccine vaccineID) {
        this.injectionID = injectionID;
        this.firstInjectionPlace = firstInjectionPlace;
        this.firstInjectionDate = firstInjectionDate;
        this.studentID = studentID;
        this.vaccineID = vaccineID;
    }

    public String getInjectionID() {
        return injectionID;
    }

    public void setInjectionID(String injectionID) {
        this.injectionID = injectionID;
    }

    public String getFirstInjectionPlace() {
        return firstInjectionPlace;
    }

    public void setFirstInjectionPlace(String firstInjectionPlace) {
        this.firstInjectionPlace = firstInjectionPlace;
    }

    public String getSecondInjectionPlace() {
        return secondInjectionPlace;
    }

    public void setSecondInjectionPlace(String secondInjectionPlace) {
        this.secondInjectionPlace = secondInjectionPlace;
    }

    public Date getFirstInjectionDate() {
        return firstInjectionDate;
    }

    public void setFirstInjectionDate(Date firstInjectionDate) {
        this.firstInjectionDate = firstInjectionDate;
    }

    public Date getSecondInjectionDate() {
        return secondInjectionDate;
    }

    public void setSecondInjectionDate(Date secondInjectionDate) {
        this.secondInjectionDate = secondInjectionDate;
    }

    public Student getStudentID() {
        return studentID;
    }

    public void setStudentID(Student studentID) {
        this.studentID = studentID;
    }

    public Vaccine getVaccineID() {
        return vaccineID;
    }

    public void setVaccineID(Vaccine vaccineID) {
        this.vaccineID = vaccineID;
    }

    public void input() {
        boolean valid = true;
        do {
            System.out.println("    code Ixxx:");
            injectionID = CheckValid.checkInputString();
            valid = injectionID.matches("^I\\d{3}$");
            if (!valid) {
                System.out.println("  The code: I and 3 digits.");
            }
        } while (!valid);
        System.out.println("   1st Place: ");
        firstInjectionPlace = CheckValid.checkInputString();
        System.out.println("   1st Date:  ");
        firstInjectionDate = CheckValid.inputDate();        
        studentID.input();
        vaccineID.input();
    }

    public void output() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("injectionID: " + injectionID);
        System.out.println("1st Place: " + firstInjectionPlace);
        System.out.println("1st Date: " + df.format(firstInjectionDate));
        System.out.println("2nd Place: " + secondInjectionPlace);        
        if(secondInjectionDate==null){
            System.out.println("2nd Date: " + secondInjectionDate);
        } else System.out.println("2nd Date: " + df.format(this.getSecondInjectionDate()));
        
        System.out.println("studentID: " + studentID.getStudentID());
        System.out.println("studentName: " + studentID.getName());
        System.out.println("vaccineID: " + vaccineID.getVaccineID());
        System.out.println("vaccineName: " + vaccineID.getName());
        System.out.println("------------------------");
    }


    @Override
    public int compareTo(Object o) {
        return this.getInjectionID().compareTo(((Injection) o).getInjectionID());
    }

}
