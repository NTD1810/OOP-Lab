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
public class Student implements Comparable, Serializable {

    String studentID;
    String name;

    public Student() {
    }

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void input() {
        System.out.println("   studentID: ");
        studentID = CheckValid.checkInputString();
        System.out.println("   name:  ");
        name = CheckValid.checkInputString();
    }

    public void output() {
        System.out.println("studentID: " + studentID);
        System.out.println("studentName: " + name);
    }

    @Override
    public int compareTo(Object o) {
        return this.getStudentID().compareTo(((Student) o).getStudentID());
    }
}
