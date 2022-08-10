/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import manager.Menu;
import manager.FoodManagement;
import manager.CheckValid;

/**
 *
 * @author Nguyễn Thùy Dương
 */
public class MenuFood {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String filename = "food.dat";
        FoodManagement list = new FoodManagement();
        list.loadFoodFromFile(filename);
        Menu menu = new Menu();
        menu.add("Add a new");
        menu.add("Search a food by name");
        menu.add("Remove the food by ID");
        menu.add("Print the food list in the ascending order of name");
        menu.add("Save file");
        menu.add("Quit");
        int userChoice;
        boolean flag;
        do {
            System.out.println("____________________*******************_________________________");
            System.out.println("Welcome to Food Management - @2021 by SE150727-Nguyen Thuy Duong");
            System.out.println("Select the options below:");
            userChoice = menu.getChoice();
            switch (userChoice) {
                case 1:
                    do {
                        list.addFood();
                        System.out.print("Do you want to continue add(Y/y) or return menu(N/n)? ");
                        flag = CheckValid.checkInputYN();
                    } while (flag);
                    break;
                case 2:
                    do {
                        //list.searchFood();
                        list.searchByWeight();
                        System.out.print("Do you want to continue search(Y/y) or return menu(N/n)? ");
                        flag = CheckValid.checkInputYN();
                    } while (flag);
                    break;
                case 3:
                    do {
                        //list.removeFood();
                        list.removeByWeight();
                        System.out.print("Do you want to continue remove(Y/y) or return menu(N/n)? ");
                        flag = CheckValid.checkInputYN();
                    } while (flag);
                    break;
                case 4:
                    list.sort();
                    list.sortWeight();
                    break;
                case 5:
                    System.out.print("Do you want to save(Y/N)? ");
                    flag = CheckValid.checkInputYN();
                    if (flag) {
                        list.saveToFile(filename);
                        System.out.println("Save success");
                    }
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
