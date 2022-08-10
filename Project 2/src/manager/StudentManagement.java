/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import data.Student;
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
public class StudentManagement extends ArrayList<Student> {

    public StudentManagement() {
        super();
    }

    public void loadStudentFromFile() {
        String fName = "student.dat";
        try {
            File f = new File(fName);
            if (!f.exists()) {
                return;
            }
            FileInputStream fi = new FileInputStream(f);
            ObjectInputStream fo = new ObjectInputStream(fi);
            while (fi.available() > 0) {
                this.add((Student) (fo.readObject()));
            }
            fo.close();
            fi.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void WriteStudent() {
        String fName = "student.dat";
        try {
            FileOutputStream f = new FileOutputStream(fName);
            ObjectOutputStream fo = new ObjectOutputStream(f);
            this.add(new Student("SE1501", "Nguyen Van A"));
            this.add(new Student("SE1502", "Le Thi C"));
            this.add(new Student("SE1503", "Tran Van C"));
            this.add(new Student("SE1504", "Nguyen Thi D"));
            this.add(new Student("SE1505", "Le Van E"));
            for (Student st : this) {
                fo.writeObject(st);
            }
            fo.close();
            f.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean addAuthor(Student x) {
        return this.add(x);
    }

    public void displayAllauthor() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            return;
        }
        for (Student st : this) {
            st.output();
        }

    }

    public boolean findSudent(String studentID, String name) {
        for (Student st : this) {
            if (st.getStudentID().equalsIgnoreCase(studentID) && st.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean findSudentID(String studentID) {
        for (Student st : this) {
            if (st.getStudentID().equalsIgnoreCase(studentID)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean findSudentName(String name) {
        for (Student st : this) {
            if (st.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
