package ru.croc.java.school;

import java.util.Arrays;

public class Zoo {
    private String title;
    private Animal[] animals;
    private Employee[] employees;

    public Zoo() {
        this("Zoo6");
    }

    public Zoo(String title) {
        this(title, new Animal[0]);
    }

    public Zoo(String title, Animal[] animals) {
        this.title = title + "!";
        this.animals = animals;
    }

    public String getTitle() {
        return title;
    }

    public Animal[] getAnimals() {
        return animals;
    }

    public void add(Animal animal) {
        animals = Arrays.copyOf(animals, animals.length + 1);
        animals[animals.length - 1] = animal;
    }

    public void add(Employee employee) {
        employees = Arrays.copyOf(employees, employees.length + 1);
        employees[employees.length - 1] = employee;
    }

    public void delete(Animal animal) {
        Animal[] newAnimals = new Animal[animals.length-1];
        for (int i = 0, j = 0; i < animals.length; i++) {
            if (animals[i] != animal) {
                newAnimals[j++] = animals[i];
            }
        }
        animals = newAnimals;
    }
}

class MainZoo {
    public static void main(String[] args) {
        Zoo zoo = new Zoo("Zoo4", new Animal[]{new Animal(), new Animal()});
        Zoo zoo2 = new Zoo("Zoo5");
        System.out.println(zoo.getTitle());
        System.out.println(zoo.getAnimals().length);
        System.out.println(zoo2.getTitle());
        System.out.println(zoo2.getAnimals().length);

        zoo.add(new Animal());
        zoo.add(new Employee());
    }
}
