package ru.croc.java.school.animal.ext;

import ru.croc.java.school.animal.Animal;

public class Panda extends Animal {

    public Panda(String name) {
        super(name);
    }

    @Override
    public String getName() {
        return name + "!!!";
    }

    public static void main(String[] args) {
        Panda panda = new Panda("Panda 1");
        System.out.println(panda.getName());
    }
}
