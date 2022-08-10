/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Nguyễn Thùy Dương
 */
public class CheckValid {
    
    public static String checkdate() {
        String str;
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        boolean cont = false;
        do {
            df.setLenient(false);
            try {
                Scanner sc = new Scanner(System.in);
                str = sc.nextLine(); 
                Date d = df.parse(str);
                String s = df.format(d);
                cont = false;
                return s;
            } catch (ParseException e) {
                System.out.println("Invalid date");
                System.out.println("Enter again: ");
                cont = true;
            }
        } while (cont);
        return null;
    }
    
    public static int inputInt(int min, int max) {
        Scanner sc = new Scanner(System.in);
        boolean cont;
        int number = 0;
        do {
            cont = true;
            try {
                number = Integer.parseInt(sc.nextLine());
                if (number < min) {
                    System.out.println("**your number must be more than " + min + "**");
                    System.out.println("Enter again:");
                    cont = false;
                } else if (number > max) {
                    System.out.println("**your number must be less than " + max + "**");
                    System.out.println("Enter again:");
                    cont = false;
                }
            } catch (Exception e) {
                System.out.println("***Only accept integer***");
                System.out.println("Enter again:");
                cont = false;
            }
        } while (cont == false);
        return number;

    }
    
    public static double inputNumber() {
        Scanner sc = new Scanner(System.in);
        boolean cont;
        double number = 0;
        do {
            cont = true;
            try {
                number = Double.parseDouble(sc.nextLine());
                if (number < 0) {
                    System.out.println("**Input number > 0**");
                    System.out.println("Enter again:");
                    cont = false;
                }
            } catch (Exception e) {
                System.out.println("***Only accept integer***");
                System.out.println("Enter again:");
                cont = false;
            }
        } while (cont == false);
        return number;
    }
    
    public static String checkInputString() {
        String s;
        boolean cont = false;
        do {
            try {
                String pattern = "^[A-Za-z\\s]{1,}";
                Scanner sc = new Scanner(System.in);
                s = sc.nextLine().trim();;
                if (!s.matches(pattern)) {
                    throw new Exception();
                }
                cont = false;
                return s;
            } catch (Exception e) {
                System.out.println("The string is invalid.");
                System.out.print("Enter again: ");
                cont = true;
            }
        } while (cont);
        return null;
    }
    
    public static boolean checkInputYN() {      
        while (true) {
            Scanner sc = new Scanner(System.in);
            String result = sc.nextLine();           
            if (result.equalsIgnoreCase("Y")) {
                return true;
            }            
            if (result.equalsIgnoreCase("N")) {
                return false;
            }
            System.out.println("Please input y/Y or n/N.");
            System.out.print("Enter again: ");
        }
    }

}
