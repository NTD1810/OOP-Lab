/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import manager.CheckValid;

/**
 *
 * @author Nguyễn Thùy Dương
 */
public class Vaccine implements Comparable,Serializable{

    String vaccineID;
    String name;

    public Vaccine() {
    }

    public Vaccine(String vaccineID, String name) {
        this.vaccineID = vaccineID;
        this.name = name;
    }

    public String getVaccineID() {
        return vaccineID;
    }

    public void setVaccineID(String vaccineID) {
        this.vaccineID = vaccineID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void input() {
        System.out.println("   vaccineID: ");
        vaccineID = CheckValid.checkInputString();
        System.out.println("   name:  ");
        name = CheckValid.checkInputString();
    }
    
    public void output(){
       System.out.println("vaccineID: "+vaccineID);
       System.out.println("vaccineName: "+ name);
    }

    @Override
    public int compareTo(Object o) {
        return this.getVaccineID().compareTo(((Vaccine) o).getVaccineID());
    }
}
