/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.util.ArrayList;

/**
 *
 * @author Nguyễn Thùy Dương
 */
public class Menu extends ArrayList<String> {
    public Menu() {
        super();
    }
    
    void addMenuFood(String s){
        this.add(s);
    }
    
    public int getChoice(){
        int choice;
        for(int i=0; i<this.size();i++){
            System.out.println((i+1)+". "+this.get(i));
        }
        System.out.println("_____________________________________________________________________");
        System.out.print("Input choice 1 to 6: ");
        choice =CheckValid.inputInt(1, 6);
        return choice;
    }
}
