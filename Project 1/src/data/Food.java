/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;

/**
 *
 * @author Nguyễn Thùy Dương
 */
public class Food implements Comparable, Serializable  {
    String ID;
    String name;
    double weight;
    String type;
    String place;
    String expiredDate;

    public Food() {
    }

    public Food(String ID, String name, double weight, String type, String place, String expiredDate) {
        this.ID = ID;
        this.name = name;
        this.weight = weight;
        this.type = type;
        this.place = place;
        this.expiredDate = expiredDate;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public void output(){
       System.out.println("ID: "+ID);
       System.out.println("Name: "+ name);
       System.out.println("Weight: "+weight);
       System.out.println("Type: "+type);
       System.out.println("Place: "+place);
       System.out.println("Expired Date: "+expiredDate);
       System.out.println("------------------------");
    }

    @Override
    public int compareTo(Object o) {
        return this.getID().compareTo(((Food) o).getID());
    }
    
    
}
