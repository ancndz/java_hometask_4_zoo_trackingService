package ru.croc.java.school.animal;

public class Animal {
    protected String name;

    public Animal(String name) {
        this.name = name;
        System.out.println("Animal 1");
    }

    public String getName() {
        return name;
    }
}
