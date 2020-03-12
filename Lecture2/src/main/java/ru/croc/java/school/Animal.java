package ru.croc.java.school;

import java.lang.*;
import java.time.LocalDate;

import static java.time.LocalDate.*;


public class Animal {
    LocalDate date = now();
    String name = "Cat";

    public String getName() {
        return name;
    }

}

class Main {
    public static void main(String[] args) {
        Animal[] animals = new Animal[2];
        animals[0] = new Animal();
        animals[1] = new Animal();

        for (int i = 0; i < animals.length; i++) {
            System.out.println(animals[i].getName());
        }
    }
}
