/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringweb.controller;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 *
 * @author apprentice
 */
public class ConsoleIO {

    int intValue;
    Double doubleValue;

    public String padRight(int width, String string) {

        String spaces = "";

        int padding = width - string.length();

        for (int i = 0; i < padding; i++) {
            spaces += " ";
        }
        return (string + spaces);

    }

    public String padRight(int width, int number) {

        String spaces = "";

        String stringNumber = Integer.toString(number);
        
        int padding = width - stringNumber.length();

        for (int i = 0; i < padding; i++) {
            spaces += " ";
        }
        return (stringNumber + spaces);

    }

    public String padRight(int width, Double number) {

        String spaces = "";
        
        String stringNumber = Double.toString(number);

        int padding = width - stringNumber.length();

        for (int i = 0; i < padding; i++) {
            spaces += " ";
        }
        return (stringNumber + spaces);

    }

    public String padLeft(int width, String string) {

        String spaces = "";

        int padding = width - string.length();

        for (int i = 0; i < padding; i++) {
            spaces += " ";
        }
        return (spaces + string);

    }

    public int PromptGetInt(String prompt) {
        System.out.println(prompt);
        Scanner sc = new Scanner(System.in);
        boolean isValid = false;

        while (!isValid) {
            try {
                intValue = Integer.parseInt(sc.nextLine());
                isValid = true;
            } catch (Exception ex) {
                System.out.println("Invalid Entry, please enter an integer");
            }
        }
        return intValue;
    }

    public int VerifyValueInRange(String prompt, String errorPrompt, int min, int max) {
        System.out.println(prompt);
        Scanner sc = new Scanner(System.in);
        int value = Integer.parseInt(sc.nextLine());

        while (value < min || value > max) {

            System.out.println(errorPrompt);
            value = Integer.parseInt(sc.nextLine());
        }
        return value;
    }

    public String PromptGetString(String prompt) {
        System.out.println(prompt);
        Scanner sc = new Scanner(System.in);
        String stringValue = sc.nextLine();
        return stringValue;
    }

    public float PromptGetFloat(String prompt) {
        System.out.println(prompt);
        Scanner sc = new Scanner(System.in);
        float floatValue = Float.parseFloat(sc.nextLine());
        return floatValue;
    }

    public float VerifyValueInRange(String prompt, float min, float max) {
        Scanner sc = new Scanner(System.in);
        float value = 0;
        do {
            System.out.println(prompt);
            value = Float.parseFloat(sc.nextLine());
        } while (value > min && value < max);
        return value;
    }

    public Double PromptGetDouble(String prompt) {
        System.out.println(prompt);
        Scanner sc = new Scanner(System.in);

        boolean isValid = false;

        while (!isValid) {
            try {
                doubleValue = Double.parseDouble(sc.nextLine());
                isValid = true;
            } catch (Exception ex) {
                System.out.println("Invalid Entry, please enter a positive number");
            }
        }
        DecimalFormat df = new DecimalFormat("#.00");
        String twoDec = df.format(doubleValue);
        doubleValue = Double.parseDouble(twoDec);

        return doubleValue;
    }

    public double VerifyValueInRange(String prompt, double min, double max) {
        Scanner sc = new Scanner(System.in);
        double value = 0;
        do {
            System.out.println(prompt);
            value = Double.parseDouble(sc.nextLine());
        } while (value > min && value < max);
        return value;
    }

    public void PrintString(String prompt) {
        System.out.println(prompt);
    }

    public String encodeComma(int number) {
        String stringNum = Integer.toString(number);
        String encode = stringNum.replaceAll(",", "\\\\,");
        return encode;
    }

    public String encodeComma(Double number) {
        String stringD = Double.toString(number);
        String encode = stringD.replaceAll(",", "\\\\,");
        return encode;
    }

    public String encodeComma(String string) {
        String encode = string.replaceAll(",", "\\\\,");
        return encode;
    }

}
