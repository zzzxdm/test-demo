package com.zzz.test.daily_questions;

public class AnimalFarm {

    public static void main(String[] args) {
        final String dog = "length: 10";
        final String pig = "length: " + dog.length();
        System.out.println("Animals are equals :" + dog == pig);
        System.out.println("Animals are equals :" + dog.equals(pig));
        System.out.println("Animals are equals :" + (dog == pig));
    }
}
