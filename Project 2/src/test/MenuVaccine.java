/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import manager.CheckValid;
import manager.InjectionManagement;
import manager.Menu;
import manager.StudentManagement;
import manager.VaccineManagement;

/**
 *
 * @author Nguyễn Thùy Dương
 */
public class MenuVaccine {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String filename = "injection.dat";
        InjectionManagement list = new InjectionManagement();
        StudentManagement obj1 = new StudentManagement();
        VaccineManagement obj2 = new VaccineManagement();
        list.loadInjectionFromFile(filename);
        obj1.loadStudentFromFile();
        obj2.loadVaccineFromFile();
        Menu menu = new Menu();
        menu.add("Show information all students have been injected");
        menu.add("Add student's vaccine injection information");
        menu.add("Updating information of students' vaccine injection");
        menu.add("Delete student vaccine injection information");
        menu.add("Search for injection information by studentID");
        menu.add("Quit");
        int userChoice;
        boolean flag;
        do {
            System.out.println("________________________*******************_________________________");
            System.out.println("Welcome to Program Management - @2021 by SE150727-Nguyen Thuy Duong");
            System.out.println("Select the options below:");
            userChoice = menu.getChoice();
            switch (userChoice) {
                case 1:
                    list.print1();
                    //list.printdate1();
                    //list.printYear();
                    break;
                case 2:
                    do {
                        list.addInjection();
                        System.out.print("Do you want to continue add(Y/y) or return menu(N/n)? ");
                        flag = CheckValid.checkInputYN();
                    } while (flag);
                    break;
                case 3:                    
                    do {
                        list.updateByID1();
                        System.out.print("Do you want to continue update(Y/y) or return menu(N/n)? ");
                        flag = CheckValid.checkInputYN();
                    } while (flag);
                    break;
                case 4:
                    do {
                        list.removeByStudentName();
                        System.out.print("Do you want to continue remove(Y/y) or return menu(N/n)? ");
                        flag = CheckValid.checkInputYN();
                    } while (flag);
                    
                    break;
                case 5:                    
                    do {
                        list.searchByStudentID();
                        System.out.print("Do you want to continue search(Y/y) or return menu(N/n)? ");
                        flag = CheckValid.checkInputYN();
                    } while (flag);
                    break;
                default:
                    System.out.print("Do you want to save(Y/N)?");
                    flag = CheckValid.checkInputYN();
                    if (flag) {
                        list.saveToFile(filename);
                        System.out.println("Save success");
                    }
                    System.out.println("Bye!!!");
            }
        } while (userChoice > 0 && userChoice < 6);
        System.out.println("-----------");
    
    }
    
}
