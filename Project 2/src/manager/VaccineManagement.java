/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import data.Vaccine;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author Nguyễn Thùy Dương
 */
public class VaccineManagement extends ArrayList<Vaccine> {

    public VaccineManagement() {
        super();
    }

    public void loadVaccineFromFile() {
        String fName = "vaccine.dat";
        try {
            File f = new File(fName);
            if (!f.exists()) {
                return;
            }
            FileInputStream fi = new FileInputStream(f);
            ObjectInputStream fo = new ObjectInputStream(fi);
            while (fi.available() > 0) {
                this.add((Vaccine) (fo.readObject()));
            }
            fo.close();
            fi.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void WriteVacine() {
        String fName = "vaccine.dat";
        try {
            FileOutputStream f = new FileOutputStream(fName);
            ObjectOutputStream fo = new ObjectOutputStream(f);
            this.add(new Vaccine("Covid-V001", "AstraZeneca"));
            this.add(new Vaccine("Covid-V002", "SPUTNIK V"));
            this.add(new Vaccine("Covid-V003", "Vero Cell"));
            this.add(new Vaccine("Covid-V004", "Pfizer"));
            this.add(new Vaccine("Covid-V005", "Moderna"));
            for (Vaccine vacxin : this) {
                fo.writeObject(vacxin);
            }
            fo.close();
            f.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean addAuthor(Vaccine x) {
        return this.add(x);
    }

    public void displayAllauthor() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
        } else {
            for (Vaccine v : this) {
                v.output();
            }
        }
    }

    public boolean findVaccine(String vaccineID, String name) {
        for (Vaccine vacxin : this) {
            if (vacxin.getVaccineID().equalsIgnoreCase(vaccineID) && vacxin.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean findVaccineID(String vaccineID) {
        for (Vaccine vacxin : this) {
            if (vacxin.getVaccineID().equalsIgnoreCase(vaccineID)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean findVaccineName(String name) {
        for (Vaccine vacxin : this) {
            if (vacxin.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

}
