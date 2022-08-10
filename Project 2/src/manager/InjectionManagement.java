/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import data.Injection;
import data.Student;
import data.Vaccine;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Nguyễn Thùy Dương
 */
public class InjectionManagement extends ArrayList<Injection> {

    Scanner sc = new Scanner(System.in);
    Student obj = new Student();
    Vaccine obj1 = new Vaccine();
    Injection obj2 = new Injection();
    StudentManagement o1 = new StudentManagement();
    VaccineManagement o2 = new VaccineManagement();

    public InjectionManagement() {
        super();
    }

    public void loadInjectionFromFile(String fName) {
        try {
            File f = new File(fName);
            if (!f.exists()) {
                return;
            }
            FileInputStream fi = new FileInputStream(f);
            ObjectInputStream fo = new ObjectInputStream(fi);
            while (fi.available() > 0) {
                this.add((Injection) (fo.readObject()));
            }
            fo.close();
            fi.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void saveToFile(String fName) {
        if (this.isEmpty()) {
            System.out.println("Empty list.");
            return;
        }
        try {
            FileOutputStream f = new FileOutputStream(fName);
            ObjectOutputStream fo = new ObjectOutputStream(f);
            for (Injection inject : this) {
                fo.writeObject(inject);
            }
            fo.close();
            f.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean find(String code) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getInjectionID().equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;

    }

    public void addInjection() {
        o1.loadStudentFromFile();
        o2.loadVaccineFromFile();
        String ID, firstInjectionPlace, studentID, name1, vaccineID, name2;
        Date firstInjectionDate;
        Injection inject1;
        boolean valid = true;
        do {
            System.out.println("  code Ixxx:");
            ID = sc.nextLine();
            inject1 = this.findByID(ID);
            valid = ID.matches("^I\\d{3}$");
            if (inject1 != null) {
                System.out.println("  The code is duplicated.");
            }
            if (!valid) {
                System.out.println("  The code: I and 3 digits.");
            }
        } while ((inject1 != null) || (!valid));
        System.out.println("   1st Place: ");
        firstInjectionPlace = CheckValid.checkInputString();
        System.out.println("   1st Date:  ");
        firstInjectionDate = CheckValid.inputDate();
        Injection inject2;
        do {
            do {
                System.out.println("   studentID: ");
                studentID = CheckValid.checkString();
                inject2 = this.findByStudentID(studentID);
                if (inject2 != null) {
                    System.out.println("  The code is duplicated.");
                }
            } while (inject2 != null);
            System.out.println("   studentName:  ");
            name1 = CheckValid.checkInputString();
            if (o1.findSudent(studentID, name1) == false) {
                System.out.println("Can not see any student in the file");
            }
        } while (o1.findSudent(studentID, name1) == false);
        do {
            System.out.println("   vaccineID: ");
            vaccineID = CheckValid.checkString();
            System.out.println("   vaccineName:  ");
            name2 = CheckValid.checkInputString();
            if (o2.findVaccine(vaccineID, name2) == false) {
                System.out.println("Can not see any vaccine in the file");
            }
        } while (o2.findVaccine(vaccineID, name2) == false);
        this.add(new Injection(ID, firstInjectionPlace, firstInjectionDate, new Student(studentID, name1), new Vaccine(vaccineID, name2)));
        System.out.println("-----> New injection has been added.");
        System.out.println("------------------------");
    }

    public Injection findByID(String ID) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getInjectionID().contains(ID)) {
                return this.get(i);

            }
        }
        return null;

    }

    public void updateByID() {
        o1.loadStudentFromFile();
        o2.loadVaccineFromFile();
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        }
        String code;
        System.out.println("Enter the ID  ");
        code = CheckValid.checkString();
        Injection inject = this.findByID(code);
        if (inject == null) {
            System.out.println("This ID does not exist.");
            System.out.println("------------------------");
        } else {
            boolean flag = false;
            Scanner sc = new Scanner(System.in);
            int choice;
            do {
                System.out.println("Which do information you want to update?");
                System.out.println("----------------------------------------");
                System.out.println("1. 1st Place");
                System.out.println("2. 1st Date");
                System.out.println("3. 2nd Place");
                System.out.println("4. 2nd Date");
                System.out.println("5. Quit");
                System.out.print("Enter your choice: ");
                choice = CheckValid.inputInt(1, 5);
                switch (choice) {
                    case 1:
                        System.out.println("   1st Place ");
                        inject.setFirstInjectionPlace(CheckValid.checkInputString());
                        System.out.println();
                        flag = true;
                        break;
                    case 2:
                        System.out.println("   1st Date ");
                        if (inject.getSecondInjectionDate() == null) {
                            inject.setFirstInjectionDate(CheckValid.inputDate());
                        } else {
                            inject.setFirstInjectionDate(CheckValid.getDifferenceDays(inject.getSecondInjectionDate()));
                        }
                        System.out.println();
                        flag = true;
                        break;
                    case 3:
                        System.out.println("   2nd Place ");
                        inject.setSecondInjectionPlace(CheckValid.checkInputString());
                        System.out.println();
                        flag = true;
                        break;
                    case 4:
                        System.out.println("   2nd Date ");
                        inject.setSecondInjectionDate(CheckValid.getDifferenceDays(inject.getFirstInjectionDate()));
                        System.out.println();
                        flag = true;
                        break;
                    default:
                        if (!flag) {
                            System.out.println("Information don't have change");
                        }
                }
            } while (choice > 0 && choice < 5);
            System.out.println("-----> Update successful!");
            if (inject.getSecondInjectionPlace() != null && inject.getSecondInjectionDate() != null) {
                System.out.println("Student has completed 2 injections!");
            }
            System.out.println("------------------------");
        }

    }

    public void updateByID1() {
        o1.loadStudentFromFile();
        o2.loadVaccineFromFile();
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        }
        String code;
        System.out.println("Enter the ID  ");
        code = CheckValid.checkString();
        Injection inject = this.findByID(code);
        if (inject == null) {
            System.out.println("This ID does not exist.");
            System.out.println("------------------------");
        } else {
            boolean flag;
            System.out.println("Do you want to update(Y/N)? ");
            System.out.print("   1st Place ");
//            inject.setFirstInjectionPlace(CheckValid.checkInput());
            //System.out.print("Do you want to update(Y/N)? ");
            flag = CheckValid.checkInputYN();
            if (flag) {
                System.out.print("---->Information update: ");
                inject.setFirstInjectionPlace(CheckValid.checkInputString());
            }
            System.out.print("   1st Date ");
            //System.out.print("Do you want to update(Y/N)? ");
            flag = CheckValid.checkInputYN();
            if (flag) {
                System.out.print("---->Information update: ");
                inject.setFirstInjectionDate(CheckValid.getDifferenceDays(inject.getSecondInjectionDate()));
            }
            System.out.print("   2nd Place ");
            //System.out.print("Do you want to update(Y/N)? ");
            flag = CheckValid.checkInputYN();
            if (flag) {
                System.out.print("---->Information update: ");
                inject.setSecondInjectionPlace(CheckValid.checkInputString());
            }
            System.out.print("   2nd Date ");
            //System.out.print("Do you want to update(Y/N)? ");
            flag = CheckValid.checkInputYN();
            if (flag) {
                System.out.print("---->Information update: ");
                inject.setSecondInjectionDate(CheckValid.getDifferenceDays(inject.getFirstInjectionDate()));
            }
            System.out.println("-----> Update successful!");
            if (inject.getSecondInjectionPlace() != null && inject.getSecondInjectionDate() != null) {
                System.out.println("Student has completed 2 injections!");
            }
            System.out.println("------------------------");
        }

    }

    public void removeByID() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        }
        String ID;
        System.out.print("Enter the InjectionID: ");
        ID = CheckValid.checkString();
        Injection inject = this.findByID(ID);
        if (inject == null) {
            System.out.println("This InjectionID does not exist.");
            System.out.println("------------------------");
        } else {
            System.out.print("Do you want to remove(Y/N)? ");
            boolean flag = CheckValid.checkInputYN();
            if (flag) {
                this.remove(inject);
                System.out.println("-----> The injection " + ID + " has been removed.");
                System.out.println("------------------------");
            }
        }

    }

    public Injection findByStudentID(String ID) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getStudentID().getStudentID().contains(ID)) {
                return this.get(i);
            }
        }
        return null;

    }

    public void searchByStudentID() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            String ID;
            System.out.print("Enter the StudentID: ");
            ID = CheckValid.checkString();
            Injection inject = this.findByStudentID(ID);
            if (inject == null) {
                System.out.println("This StudentID does not exist.");
                System.out.println("------------------------");
            } else {
                System.out.println("----->The information found: ");
                System.out.println("------------------------");
                inject.output();
            }
        }
    }

    public void print() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            return;
        }
        System.out.println("\n LIST");
        System.out.println("-------------------------------");
        for (Injection x : this) {
            x.output();
        }

    }

    /**
     * **********************************************************************************************************************************************
     */
    //tìm khoảng cách 2 ngày
    public boolean findDate(Date d1, Date d2) {
        System.out.print("Enter distance: ");
        int n = CheckValid.inputInt();
        return CheckValid.getDifferenceDays(d1, d2) >= n;
    }

    //in d1,d2 cách ? ngày
    public void printDate() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {            
            ArrayList<Injection> list = new ArrayList<>();
            for (int i = 0; i < this.size(); i++) {
                Date d = this.get(i).getSecondInjectionDate();
                if (d != null) {
                    if (findDate(this.get(i).getFirstInjectionDate(), d) == true) {
                        list.add(this.get(i));
                    }

                }
            }
            if (list.isEmpty()) {
                System.out.println("Empty List.");
                System.out.println("------------------------");
                return;
            } else {
                System.out.println("\n LIST");
                System.out.println("-------------------------------");
                for (Injection x : list) {
                    x.output();
                }
            }
        }
    }

    //in d1 cách d hiện tại
    public void printdate1() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            ArrayList<Injection> list = new ArrayList<>();
            Date d = new Date();
            System.out.println(d);
            for (int i = 0; i < this.size(); i++) {
                if (this.get(i).getFirstInjectionDate()!= null) {
                    if (findDate(this.get(i).getFirstInjectionDate(), d) == true) {
                        list.add(this.get(i));
                    }
                }

            }
            if (list.isEmpty()) {
                System.out.println("Empty List.");
                System.out.println("------------------------");
                return;
            } else {
                System.out.println("\n LIST");
                System.out.println("-------------------------------");
                for (Injection x : list) {
                    x.output();
                }
            }
        }
    }

    //in d2 cách d hiện tại
    public void printdate2() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            ArrayList<Injection> list = new ArrayList<>();
            Date d = new Date();
            System.out.println(d);
            for (int i = 0; i < this.size(); i++) {
                if (this.get(i).getSecondInjectionDate() != null) {
                    if (findDate(this.get(i).getSecondInjectionDate(), d) == true) {
                        list.add(this.get(i));
                    }
                }

            }
            if (list.isEmpty()) {
                System.out.println("Empty List.");
                System.out.println("------------------------");
                return;
            } else {
                System.out.println("\n LIST");
                System.out.println("-------------------------------");
                for (Injection x : list) {
                    x.output();
                }
            }
        }
    }

    //xóa d1 cách d hiện tại
    public void removedate1() {

        ArrayList<Injection> list = new ArrayList<>();
        Date d = new Date();
        for (int i = 0; i < this.size(); i++) {
            if (findDate(this.get(i).getFirstInjectionDate(), d) == true) {
                list.add(this.get(i));
            }
        }
        if (list.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            System.out.print("Do you want to remove(Y/N)? ");
            boolean flag = CheckValid.checkInputYN();
            if (flag) {
                this.removeAll(list);
                System.out.println("-----> Delete successfull!!");
                System.out.println("------------------------");
            }

        }

    }

    //xóa d2 cách d hiện tại
    public void removedate2() {
        ArrayList<Injection> list = new ArrayList<>();
        Date d = new Date();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getSecondInjectionDate() != null) {
                if (findDate(this.get(i).getSecondInjectionDate(), d) == true) {
                    list.add(this.get(i));
                }
            }
        }
        if (list.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            System.out.print("Do you want to remove(Y/N)? ");
            boolean flag = CheckValid.checkInputYN();
            if (flag) {
                this.removeAll(list);
                System.out.println("-----> Delete successfull!!");
                System.out.println("------------------------");
            }

        }

    }

    // xóa d1 cách d2 ? ngày
    public void removeDate() {
        ArrayList<Injection> list = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            Date d = this.get(i).getSecondInjectionDate();
            if (d != null) {
                if (findDate(this.get(i).getFirstInjectionDate(), d) == true) {
                    list.add(this.get(i));
                }
            }
        }
        if (list.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            System.out.print("Do you want to remove(Y/N)? ");
            boolean flag = CheckValid.checkInputYN();
            if (flag) {
                this.removeAll(list);
                System.out.println("-----> Delete successfull!!");
                System.out.println("------------------------");
            }

        }

    }

    //update theo StudentID
    public void updateByStudentID() {
        o1.loadStudentFromFile();
        o2.loadVaccineFromFile();
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        }
        String code;
        System.out.println("Enter the ID  ");
        code = CheckValid.checkString();
        Injection inject = this.findByStudentID(code);
        if (inject == null) {
            System.out.println("This ID does not exist.");
            System.out.println("------------------------");
        } else {
            boolean flag = false;
            Scanner sc = new Scanner(System.in);
            int choice;
            do {
                System.out.println("Which do information you want to update?");
                System.out.println("----------------------------------------");
                System.out.println("1. 1st Place");
                System.out.println("2. 1st Date");
                System.out.println("3. 2nd Place");
                System.out.println("4. 2nd Date");
                System.out.println("5. Quit");
                System.out.print("Enter your choice: ");
                choice = CheckValid.inputInt(1, 5);
                switch (choice) {
                    case 1:
                        System.out.println("   1st Place ");
                        inject.setFirstInjectionPlace(CheckValid.checkInputString());
                        System.out.println();
                        flag = true;
                        break;
                    case 2:
                        System.out.println("   1st Date ");
                        if (inject.getSecondInjectionDate() == null) {
                            inject.setFirstInjectionDate(CheckValid.inputDate());
                        } else {
                            inject.setFirstInjectionDate(CheckValid.getDifferenceDays(inject.getSecondInjectionDate()));
                        }
                        System.out.println();
                        flag = true;
                        break;
                    case 3:
                        System.out.println("   2nd Place ");
                        inject.setSecondInjectionPlace(CheckValid.checkInputString());
                        System.out.println();
                        flag = true;
                        break;
                    case 4:
                        System.out.println("   2nd Date ");
                        inject.setSecondInjectionDate(CheckValid.getDifferenceDays(inject.getFirstInjectionDate()));
                        System.out.println();
                        flag = true;
                        break;
                    default:
                        if (!flag) {
                            System.out.println("Information don't have change");
                        }
                }
            } while (choice > 0 && choice < 5);
            System.out.println("-----> Update successful!");
            if (inject.getSecondInjectionPlace() != null && inject.getSecondInjectionDate() != null) {
                System.out.println("Student has completed 2 injections!");
            }
            System.out.println("------------------------");
        }

    }

    public void updateByStudentID1() {
        o1.loadStudentFromFile();
        o2.loadVaccineFromFile();
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        }
        String code;
        System.out.println("Enter the ID  ");
        code = CheckValid.checkString();
        Injection inject = this.findByStudentID(code);
        if (inject == null) {
            System.out.println("This ID does not exist.");
            System.out.println("------------------------");
        } else {
            boolean flag;
            System.out.println("Do you want to update(Y/N)? ");
            System.out.print("   1st Place ");
//            inject.setFirstInjectionPlace(CheckValid.checkInput());
            //System.out.print("Do you want to update(Y/N)? ");
            flag = CheckValid.checkInputYN();
            if (flag) {
                System.out.print("---->Information update: ");
                inject.setFirstInjectionPlace(CheckValid.checkInputString());
            }
            System.out.print("   1st Date ");
            //System.out.print("Do you want to update(Y/N)? ");
            flag = CheckValid.checkInputYN();
            if (flag) {
                System.out.print("---->Information update: ");
                inject.setFirstInjectionDate(CheckValid.getDifferenceDays(inject.getSecondInjectionDate()));
            }
            System.out.print("   2nd Place ");
            //System.out.print("Do you want to update(Y/N)? ");
            flag = CheckValid.checkInputYN();
            if (flag) {
                System.out.print("---->Information update: ");
                inject.setSecondInjectionPlace(CheckValid.checkInputString());
            }
            System.out.print("   2nd Date ");
            //System.out.print("Do you want to update(Y/N)? ");
            flag = CheckValid.checkInputYN();
            if (flag) {
                System.out.print("---->Information update: ");
                inject.setSecondInjectionDate(CheckValid.getDifferenceDays(inject.getFirstInjectionDate()));
            }
            System.out.println("-----> Update successful!");
            if (inject.getSecondInjectionPlace() != null && inject.getSecondInjectionDate() != null) {
                System.out.println("Student has completed 2 injections!");
            }
            System.out.println("------------------------");
        }

    }

    ArrayList<Injection> findByPlace1(String place) {
        ArrayList<Injection> list = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getFirstInjectionPlace().contains(place)) {
                list.add(this.get(i));

            }
        }
        return list;
    }

    //xóa theo place1
    public void removeByPlace1() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        }
        String place;
        System.out.print("Enter the 1st Place: ");
        place = CheckValid.checkString();
        ArrayList<Injection> list = this.findByPlace1(place);
        if (list.isEmpty()) {
            System.out.println("This 1st Place does not exist.");
            System.out.println("------------------------");
        } else {
            System.out.print("Do you want to remove(Y/N)? ");
            boolean flag = CheckValid.checkInputYN();
            if (flag) {
                this.removeAll(list);
                System.out.println("-----> Delete successful!!");
                System.out.println("------------------------");
            }
        }
    }

    //in theo place1
    public void searchByPlace1() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            String place;
            System.out.print("Enter the 1st Place: ");
            place = CheckValid.checkString();
            ArrayList<Injection> list = this.findByPlace1(place);
            if (list.isEmpty()) {
                System.out.println("This 1st Place does not exist.");
                System.out.println("------------------------");
            } else {
                System.out.println("----->The information found: ");
                System.out.println("------------------------");
                for (Injection x : list) {
                    x.output();
                }
            }
        }
    }

    ArrayList<Injection> findByPlace2(String place) {
        ArrayList<Injection> list = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getSecondInjectionPlace() != null) {
                if (this.get(i).getSecondInjectionPlace().contains(place)) {
                    list.add(this.get(i));
                }
            }
        }
        return list;
    }

    //xóa theo place2
    public void removeByPlace2() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        }
        String place;
        System.out.print("Enter the 2nd Place: ");
        place = CheckValid.checkString();
        ArrayList<Injection> list = this.findByPlace2(place);
        if (list.isEmpty()) {
            System.out.println("This 2nd Place does not exist.");
            System.out.println("------------------------");
        } else {
            System.out.print("Do you want to remove(Y/N)? ");
            boolean flag = CheckValid.checkInputYN();
            if (flag) {
                this.removeAll(list);
                System.out.println("-----> Delete successful!!");
                System.out.println("------------------------");
            }
        }
    }

    //in theo place2
    public void searchByPlace2() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            String place;
            System.out.print("Enter the 2nd Place: ");
            place = CheckValid.checkString();
            ArrayList<Injection> list = this.findByPlace2(place);
            if (list.isEmpty()) {
                System.out.println("This 2nd Place does not exist.");
                System.out.println("------------------------");
            } else {
                System.out.println("----->The information found: ");
                System.out.println("------------------------");
                for (Injection x : list) {
                    x.output();
                }
            }
        }
    }

    ArrayList<Injection> findByDate1(Date date) {
        ArrayList<Injection> list = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getFirstInjectionDate().equals(date)) {
                list.add(this.get(i));

            }
        }
        return list;
    }

    //xóa theo d1
    public void removeByDate1() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        }
        Date date;
        System.out.print("Enter the 1st Date: ");
        date = CheckValid.inputDate();
        ArrayList<Injection> list = this.findByDate1(date);
        if (list.isEmpty()) {
            System.out.println("This 1st Date does not exist.");
            System.out.println("------------------------");
        } else {
            System.out.print("Do you want to remove(Y/N)? ");
            boolean flag = CheckValid.checkInputYN();
            if (flag) {
                this.removeAll(list);
                System.out.println("-----> Delete successful!!");
                System.out.println("------------------------");
            }
        }
    }

    //in theo d1
    public void searchByDate1() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            Date date;
            System.out.print("Enter the 1st Date: ");
            date = CheckValid.inputDate();
            ArrayList<Injection> list = this.findByDate1(date);
            if (list.isEmpty()) {
                System.out.println("This 1st Date does not exist.");
                System.out.println("------------------------");
            } else {
                System.out.println("----->The information found: ");
                System.out.println("------------------------");
                for (Injection x : list) {
                    x.output();
                }
            }
        }
    }

    ArrayList<Injection> findByDate2(Date date) {
        ArrayList<Injection> list = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getSecondInjectionDate() != null) {
                if (this.get(i).getSecondInjectionDate().equals(date)) {
                    list.add(this.get(i));
                }
            }
        }
        return list;
    }

    //xóa theo d2
    public void removeByDate2() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        }
        Date date;
        System.out.print("Enter the 2nd Date: ");
        date = CheckValid.inputDate();
        ArrayList<Injection> list = this.findByDate2(date);
        if (list.isEmpty()) {
            System.out.println("This 2nd Date does not exist.");
            System.out.println("------------------------");
        } else {
            System.out.print("Do you want to remove(Y/N)? ");
            boolean flag = CheckValid.checkInputYN();
            if (flag) {
                this.removeAll(list);
                System.out.println("-----> Delete successful!!");
                System.out.println("------------------------");
            }
        }
    }

    //in theo d2
    public void searchByDate2() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            Date date;
            System.out.print("Enter the 2nd Date: ");
            date = CheckValid.inputDate();
            ArrayList<Injection> list = this.findByDate2(date);
            if (list.isEmpty()) {
                System.out.println("This 2nd Date does not exist.");
                System.out.println("------------------------");
            } else {
                System.out.println("----->The information found: ");
                System.out.println("------------------------");
                for (Injection x : list) {
                    x.output();
                }
            }
        }
    }

    public Injection findByStudentName(String StudentName) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getStudentID().getName().contains(StudentName)) {
                return this.get(i);
            }
        }
        return null;
    }

    //xóa theo StudentName
    public void removeByStudentName() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        }
        String name;
        System.out.print("Enter the name: ");
        name = CheckValid.checkString();
        Injection inject = this.findByStudentName(name);
        if (inject == null) {
            System.out.println("This name does not exist.");
            System.out.println("------------------------");
        } else {
            System.out.print("Do you want to remove(Y/N)? ");
            boolean flag = CheckValid.checkInputYN();
            if (flag) {
                this.remove(inject);
                System.out.println("-----> Delete successful!!");
                System.out.println("------------------------");
            }
        }

    }

    //in theo StudentName
    public void searchByStudentName() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            String name;
            System.out.print("Enter the name: ");
            name = CheckValid.checkString();
            Injection inject = this.findByStudentName(name);
            if (inject == null) {
                System.out.println("This name does not exist.");
                System.out.println("------------------------");
            } else {
                System.out.println("----->The information found: ");
                System.out.println("------------------------");
                inject.output();
            }
        }
    }

    ArrayList<Injection> findByVaccineID(String VaccineID) {
        ArrayList<Injection> list = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getVaccineID().getVaccineID().contains(VaccineID)) {
                list.add(this.get(i));

            }
        }
        return list;
    }

    //xóa theo VaccineID
    public void removeByVaccineID() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        }
        String vaccineID;
        System.out.print("Enter the VaccineID: ");
        vaccineID = CheckValid.checkString();
        ArrayList<Injection> list = this.findByVaccineID(vaccineID);
        if (list.isEmpty()) {
            System.out.println("This VaccineID does not exist.");
            System.out.println("------------------------");
        } else {
            System.out.print("Do you want to remove(Y/N)? ");
            boolean flag = CheckValid.checkInputYN();
            if (flag) {
                this.removeAll(list);
                System.out.println("-----> Delete successful!!");
                System.out.println("------------------------");
            }
        }
    }

    //in theo VaccineID
    public void searchByVaccineID() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            String vaccineID;
            System.out.print("Enter the VaccineID: ");
            vaccineID = CheckValid.checkString();
            ArrayList<Injection> list = this.findByVaccineID(vaccineID);
            if (list.isEmpty()) {
                System.out.println("This VaccineID does not exist.");
                System.out.println("------------------------");
            } else {
                System.out.println("----->The information found: ");
                System.out.println("------------------------");
                for (Injection x : list) {
                    x.output();
                }
            }
        }
    }

    ArrayList<Injection> findByVaccineName(String VaccineName) {
        ArrayList<Injection> list = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getVaccineID().getName().contains(VaccineName)) {
                list.add(this.get(i));

            }
        }
        return list;
    }

    //xóa theo VaccineName
    public void removeByVaccineName() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        }
        String name;
        System.out.print("Enter the name: ");
        name = CheckValid.checkString();
        ArrayList<Injection> list = this.findByVaccineName(name);
        if (list.isEmpty()) {
            System.out.println("This name does not exist.");
            System.out.println("------------------------");
        } else {
            System.out.print("Do you want to remove(Y/N)? ");
            boolean flag = CheckValid.checkInputYN();
            if (flag) {
                this.removeAll(list);
                System.out.println("-----> Delete successful!!");
                System.out.println("------------------------");
            }
        }
    }

    //in theo VaccineName
    public void searchByVaccineName() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            String name;
            System.out.print("Enter the 2nd Place: ");
            name = CheckValid.checkString();
            ArrayList<Injection> list = this.findByVaccineName(name);
            if (list.isEmpty()) {
                System.out.println("This 2nd Place does not exist.");
                System.out.println("------------------------");
            } else {
                System.out.println("----->The information found: ");
                System.out.println("------------------------");
                for (Injection x : list) {
                    x.output();
                }
            }
        }
    }

    //in theo ID
    public void searchByID() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            String ID;
            System.out.print("Enter the ID: ");
            ID = CheckValid.checkString();
            Injection inject = this.findByID(ID);
            if (inject == null) {
                System.out.println("This ID does not exist.");
                System.out.println("------------------------");
            } else {
                System.out.println("----->The information found: ");
                System.out.println("------------------------");
                inject.output();
            }
        }
    }

    //xóa theo StudentID
    public void removeByStudentID() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        }
        String ID;
        System.out.print("Enter the StudentID: ");
        ID = CheckValid.checkString();
        Injection inject = this.findByStudentID(ID);
        if (inject == null) {
            System.out.println("This StudentID does not exist.");
            System.out.println("------------------------");
        } else {
            System.out.print("Do you want to remove(Y/N)? ");
            boolean flag = CheckValid.checkInputYN();
            if (flag) {
                this.remove(inject);
                System.out.println("-----> Delete successful!!");
                System.out.println("------------------------");
            }
        }

    }

    public void sort() {
        Collections.sort(this, new Comparator<Injection>() {
            @Override
            public int compare(Injection o1, Injection o2) {
                return (o1.getInjectionID().compareTo(o1.getInjectionID()));
            }
        });
        this.print();
    }

    //in d2=null
    public void findByDate2() {
        ArrayList<Injection> list = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getSecondInjectionDate() == null) {
                list.add(this.get(i));
            }
        }
        if (list.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            System.out.println("\n LIST");
            System.out.println("-------------------------------");
            for (Injection x : list) {
                x.output();
            }
        }
    }

    //in place2=null
    public void findByPlace2() {
        ArrayList<Injection> list = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getSecondInjectionPlace() == null) {
                list.add(this.get(i));
            }
        }
        if (list.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            System.out.println("\n LIST");
            System.out.println("-------------------------------");
            for (Injection x : list) {
                x.output();
            }
        }
    }

    //xóa d2=null
    public void deleByDate2() {
        ArrayList<Injection> list = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getSecondInjectionDate() == null) {
                list.add(this.get(i));
            }
        }
        if (list.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            System.out.print("Do you want to remove(Y/N)? ");
            boolean flag = CheckValid.checkInputYN();
            if (flag) {
                this.removeAll(list);
                System.out.println("-----> Delete successfull!!");
                System.out.println("------------------------");
            }

        }
    }

    //xóa place2=null
    public void deleByPlace2() {
        ArrayList<Injection> list = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getSecondInjectionPlace() == null) {
                list.add(this.get(i));
            }
        }
        if (list.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            System.out.print("Do you want to remove(Y/N)? ");
            boolean flag = CheckValid.checkInputYN();
            if (flag) {
                this.removeAll(list);
                System.out.println("-----> Delete successfull!!");
                System.out.println("------------------------");
            }

        }
    }

    ArrayList<Injection> findDate2(Date date) {
        ArrayList<Injection> list = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getFirstInjectionDate().equals(date)) {
                list.add(this.get(i));
            }
            if (this.get(i).getSecondInjectionDate() != null) {
                if (this.get(i).getSecondInjectionDate().equals(date)) {
                    list.add(this.get(i));
                }
            }
        }
        return list;
    }

    //xóa d1 hoặc d2 theo yc
    public void removeDate2() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        }
        Date date;
        System.out.print("Enter the Date: ");
        date = CheckValid.inputDate();
        ArrayList<Injection> list = this.findDate2(date);
        if (list.isEmpty()) {
            System.out.println("This Date does not exist.");
            System.out.println("------------------------");
        } else {
            System.out.print("Do you want to remove(Y/N)? ");
            boolean flag = CheckValid.checkInputYN();
            if (flag) {
                this.removeAll(list);
                System.out.println("-----> Delete successful!!");
                System.out.println("------------------------");
            }
        }
    }

    public int findDay(Date d) {
        int day;
        Calendar date = Calendar.getInstance();
        date.setTime(d);
        day = date.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    //in ngày theo yc
    public void printDay() {
        ArrayList<Injection> list = new ArrayList();
        System.out.print("Enter day: ");
        int n = CheckValid.inputInt();
        for (int i = 0; i < this.size(); i++) {
            if (findDay(this.get(i).getFirstInjectionDate()) == n) {
                list.add(this.get(i));
            } else if (this.get(i).getSecondInjectionDate() != null) {
                if (findDay(this.get(i).getSecondInjectionDate()) == n) {
                    list.add(this.get(i));
                }
            }
        }
        if (list.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            System.out.println("\n LIST");
            System.out.println("-------------------------------");
            for (Injection x : list) {
                x.output();
            }
        }

    }

    //xóa ngày theo yc
    public void removeDay() {
        ArrayList<Injection> list = new ArrayList();
        System.out.print("Enter day: ");
        int n = CheckValid.inputInt();
        for (int i = 0; i < this.size(); i++) {
            if (findDay(this.get(i).getFirstInjectionDate()) == 6) {
                list.add(this.get(i));
            } else if (this.get(i).getSecondInjectionDate() != null) {
                if (findDay(this.get(i).getSecondInjectionDate()) == 6) {
                    list.add(this.get(i));
                }
            }
        }
        if (list.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            System.out.print("Do you want to remove(Y/N)? ");
            boolean flag = CheckValid.checkInputYN();
            if (flag) {
                this.removeAll(list);
                System.out.println("-----> Delete successfull!!");
                System.out.println("------------------------");
            }

        }

    }

    public int findMonth(Date d) {
        int month;
        Calendar date = Calendar.getInstance();
        date.setTime(d);
        month = date.get(Calendar.MONTH);
        return month + 1;
    }

    //in tháng theo yc
    public void printMonth() {
        ArrayList<Injection> list = new ArrayList();
        System.out.print("Enter month: ");
        int n = CheckValid.inputInt();
        for (int i = 0; i < this.size(); i++) {
            if (findMonth(this.get(i).getFirstInjectionDate()) == n) {
                list.add(this.get(i));
            } else if (this.get(i).getSecondInjectionDate() != null) {
                if (findMonth(this.get(i).getSecondInjectionDate()) == n) {
                    list.add(this.get(i));
                }
            }
        }
        if (list.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            System.out.println("\n LIST");
            System.out.println("-------------------------------");
            for (Injection x : list) {
                x.output();
            }
        }

    }

    //xóa tháng theo yc
    public void removeMonth() {
        ArrayList<Injection> list = new ArrayList();
        System.out.print("Enter month: ");
        int n = CheckValid.inputInt();
        for (int i = 0; i < this.size(); i++) {
            if (findMonth(this.get(i).getFirstInjectionDate()) == n) {
                list.add(this.get(i));
            } else if (this.get(i).getSecondInjectionDate() != null) {
                if (findMonth(this.get(i).getSecondInjectionDate()) == n) {
                    list.add(this.get(i));
                }
            }
        }
        if (list.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            System.out.print("Do you want to remove(Y/N)? ");
            boolean flag = CheckValid.checkInputYN();
            if (flag) {
                this.removeAll(list);
                System.out.println("-----> Delete successfull!!");
                System.out.println("------------------------");
            }

        }

    }

    public int findYear(Date d) {
        int year;
        Calendar date = Calendar.getInstance();
        date.setTime(d);
        year = date.get(Calendar.YEAR);
        return year;
    }

    //in năm theo yc
    public void printYear() {
        ArrayList<Injection> list = new ArrayList();
        System.out.print("Enter year: ");
        int n = CheckValid.inputInt();
        for (int i = 0; i < this.size(); i++) {
            if (findYear(this.get(i).getFirstInjectionDate()) == n) {
                list.add(this.get(i));
            } else if (this.get(i).getSecondInjectionDate() != null) {
                if (findYear(this.get(i).getSecondInjectionDate()) == n) {
                    list.add(this.get(i));
                }
            }
        }
        if (list.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            System.out.println("\n LIST");
            System.out.println("-------------------------------");
            for (Injection x : list) {
                x.output();
            }
        }

    }

    //xóa năm theo yc
    public void removeYear() {
        ArrayList<Injection> list = new ArrayList();
        System.out.print("Enter year: ");
        int n = CheckValid.inputInt();
        for (int i = 0; i < this.size(); i++) {
            if (findYear(this.get(i).getFirstInjectionDate()) == n) {
                list.add(this.get(i));
            } else if (this.get(i).getSecondInjectionDate() != null) {
                if (findYear(this.get(i).getSecondInjectionDate()) == n) {
                    list.add(this.get(i));
                }
            }
        }
        if (list.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            System.out.print("Do you want to remove(Y/N)? ");
            boolean flag = CheckValid.checkInputYN();
            if (flag) {
                this.removeAll(list);
                System.out.println("-----> Delete successfull!!");
                System.out.println("------------------------");
            }

        }

    }
    
    public void print1() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            return;
        }
        System.out.println("\n LIST");
        System.out.println("-------------------------------");
        for(int i=0;i<this.size();i++){
            if(this.get(i).getSecondInjectionDate()!= null&&this.get(i).getSecondInjectionPlace()!=null)
            {
                System.out.println("Status: Complete");
                this.get(i).output();
                
            }
            else{
                System.out.println("Status: Not complete");
                this.get(i).output();
                
            }
        }
                
        

    }
}
