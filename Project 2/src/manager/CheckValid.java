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
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Nguyễn Thùy Dương
 */
public class CheckValid {

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
                    cont = false;
                } else if (number > max) {
                    System.out.println("**your number must be less than " + max + "**");
                    cont = false;
                }
            } catch (Exception e) {
                System.out.println("***Only accept integer***");
                cont = false;
            }
        } while (cont == false);
        return number;

    }

    public static int inputInt() {
        Scanner sc = new Scanner(System.in);
        boolean cont;
        int number = 0;
        do {
            cont = true;
            try {
                number = Integer.parseInt(sc.nextLine());
                if (number < 0) {
                    System.out.println("**Input number > 0**");
                    System.out.println("Input again:");
                    cont = false;
                }
            } catch (Exception e) {
                System.out.println("***Only accept integer***");
                System.out.println("Input again:");
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

    public static String checkString() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String s = sc.nextLine().trim();
            if (s.isEmpty()) {
                System.out.println("Not empty");
                System.out.print("Enter again: ");
            } else {
                return s;
            }
        }
    }

    public static boolean checkInputYN() {
        while (true) {
            String result = checkInputString();

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

    public static Date inputDate() {
        String dateFormat = "\\d{1,2}/\\d{1,2}/\\d{4}";
        Scanner sc = new Scanner(System.in);
        Date date = null;
        String data;
        boolean flag = true;
        do {
            data = sc.nextLine();
            if (data.matches(dateFormat)) {
                try {                   
                    date = getDate(data);
                    flag = true;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    flag = false;
                }
            } else {
                System.out.println("DateFormat invalid.");
                flag = false;
            }

        } while (flag == false);

        return date;
    }

    public static Date getDate(String s) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = formatter.parse(s);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
        return date;
    }

    public static long getDifferenceDays(Date d1, Date d2) {
        long diffInMillies = Math.abs(d1.getTime() - d2.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diff;
    }

    public static Date getDifferenceDays(Date d1) {
        Date d2;
        boolean flag = true;
        do {
            d2 = inputDate();
            long diffInMillies = Math.abs(d1.getTime() - d2.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if ((diff >= 28 && diff <= 84) || (-28 >= diff && diff >= -84)) {
                flag = true;
            } else {
                System.out.println("The second dose of vaccine must be given 4 to 12 weeks after the first injection");
                System.out.println("Enter again: ");
                flag = false;
            }
        } while (flag == false);
        return d2;
    }

}
