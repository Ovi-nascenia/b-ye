package com.nascenia.biyeta.activity;

/**
 * Created by saiful on 3/19/17.
 */

public class DemoClass {
    public static void main(String[] args) throws java.lang.Exception {
        // your code goes here


        checkNullField(null);


    }

    private static String checkNullField(String value) {

        if (value == null || value.isEmpty()) {
            System.out.println("null Found");
            return "";
        } else {
            System.out.println("null Found");
            return value;
        }

    }
}