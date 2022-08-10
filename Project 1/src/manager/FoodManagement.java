/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import data.Food;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 *
 * @author Nguyễn Thùy Dương
 */
public class FoodManagement extends ArrayList<Food> {

    Scanner sc = new Scanner(System.in);

    public FoodManagement() {
        super();
    }

    public void loadFoodFromFile(String fName) {
        try {
            File f = new File(fName);
            if (!f.exists()) {
                return;
            }
            FileInputStream fi = new FileInputStream(f);
            ObjectInputStream fo = new ObjectInputStream(fi);
            while (fi.available() > 0) {
                this.add((Food) (fo.readObject()));
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
            for (Food food : this) {
                fo.writeObject(food);
            }
            fo.close();
            f.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private int find(String code) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getID().equals(code)) {
                return i;
            }
        }
        return -1;

    }

    ArrayList<Food> getFoodByName(String newName) {
        ArrayList<Food> listFood = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getName().contains(newName)) {
                listFood.add(this.get(i));

            }
        }
        return listFood;
    }

    public void addFood() {
        String ID, name, type, place, expiredDate;
        double weight;
        int pos;
        boolean valid = true;
        System.out.println("Enter ");
        do {
            System.out.println("  code Fxxx:");
            ID = sc.nextLine();
            pos = find(ID);
            valid = ID.matches("^F\\d{3}$");
            if (pos >= 0) {
                System.out.println("  The code is duplicated.");
            }
            if (!valid) {
                System.out.println("  The code: F and 3 digits.");
            }
        } while (pos >= 0 || (!valid));
        System.out.println("   name: ");
        name = CheckValid.checkInputString();
        System.out.println("   weight: ");
        weight = CheckValid.inputNumber();
        System.out.println("   type:  ");
        type = CheckValid.checkInputString();
        System.out.println("   place:  ");
        place = CheckValid.checkInputString();
        System.out.println("   expiredDate(dd/mm/yyyy):  ");
        expiredDate = CheckValid.checkdate();
        this.add(new Food(ID, name, weight, type, place, expiredDate));
        System.out.println("New food has been added.");
        System.out.println("------------------------");
    }

    public void searchFood() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            String name;
            System.out.print("Enter the name: ");
            name = CheckValid.checkInputString();
            ArrayList<Food> food = this.getFoodByName(name);
            if (food.isEmpty()) {
                System.out.println("This food does not exist.");
                System.out.println("------------------------");
            } else {
                System.out.println("The food found: ");
                System.out.println("------------------------");
                for (Food x : food) {
                    x.output();
                }
            }
        }
    }

    public void removeFood() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        }
        String ID;
        System.out.print("Enter the ID: ");
        ID = sc.nextLine();
        int pos = find(ID);
        if (pos < 0) {
            System.out.println("This ID does not exist.");
            System.out.println("------------------------");
        } else {
            System.out.print("Do you want to remove(Y/N)? ");
            boolean flag = CheckValid.checkInputYN();
            if (flag) {
                this.remove(pos);
                System.out.println("The food " + ID + " has been removed.");
                System.out.println("------------------------");
            }
        }

    }

    public long Compare(String s1, String s2) {
        long Days;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate d1 = LocalDate.parse(s1, formatter);
        LocalDate d2 = LocalDate.parse(s2, formatter);
        Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
        Days = diff.toDays();
        return Days;
    }

    public void printSort() {
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            return;
        }
        for (int i = 0; i < this.size() - 1; i++) {
            for (int j = i + 1; j < this.size(); j++) {
                if (Compare(this.get(i).getExpiredDate(), this.get(j).getExpiredDate()) > 0) {
                    Collections.swap(this, i, j);
                }
            }
        }
        this.print();

    }

    public void print() {
        System.out.println("\n LIST");
        System.out.println("-------------------------------");
        for (Food x : this) {
            x.output();
        }
    }    
    
    public void sort(){
        Collections.sort(this, new Comparator<Food>(){
            @Override
            public int compare(Food o1, Food o2) {
                return (o1.getName().compareTo(o1.getName()));
            }
        });
        this.print();
    }
    
    public void sortWeight(){
        Collections.sort(this, new Comparator<Food>(){
            @Override
            public int compare(Food o1, Food o2) {
//                if(o1.getWeight()>o2.getWeight()){
//                    return 1;
//                }
//                return -1;
                return (int) (o1.getWeight()-o2.getWeight());
            }
        });
//        Collections.reverse(this);
        this.print();
    }
    
    public void searchByID(){
        
    }
    
    public void searchByTypevsPlace(){
        
    }
    public void searchByDate(){
        
    }
    
    
    
    public void searchByWeight(){
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        } else {
            Double weight;
            System.out.print("Enter the name: ");
            weight = sc.nextDouble();
            ArrayList<Food> food = this.getFoodByweight(weight);
            if (food.isEmpty()) {
                System.out.println("This food does not exist.");
                System.out.println("------------------------");
            } else {
                System.out.println("The food found: ");
                System.out.println("------------------------");
                for (Food x : food) {
                    x.output();
                }
            }
        }
        
    }
    
    public void removeByNamevsTypevsPlacevsDate(){
        
    }
    
    ArrayList<Food> getFoodByweight(Double weight) {
        ArrayList<Food> listFood = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getWeight() == weight) {
                listFood.add(this.get(i));

            }
        }
        return listFood;
    }
    
    public void removeByWeight(){
        if (this.isEmpty()) {
            System.out.println("Empty List.");
            System.out.println("------------------------");
            return;
        }
        Double weight;
        System.out.print("Enter the ID: ");
        weight = sc.nextDouble();
        ArrayList<Food> food = this.getFoodByweight(weight);
        if (food.isEmpty()) {
            System.out.println("This ID does not exist.");
            System.out.println("------------------------");
        } else {
            System.out.print("Do you want to remove(Y/N)? ");
            boolean flag = CheckValid.checkInputYN();
            if (flag) {
                this.removeAll(food);
                System.out.println("The food " + weight + " has been removed.");
                System.out.println("------------------------");
            }
        }
        
    }    
    public void sortByIDvsNamevsTypevsPlace(){
        
    }
    
    public void sortByWeight(){
        
    }
    
    public double sum(){
        return 0;
    }
}
