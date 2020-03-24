package ru.croc.java.winter.school.zoo.employee;

import ru.croc.java.winter.school.zoo.animal.Animal;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Сотрудник.
 */
public class Employee {
    /** Имя. */
    private String name;
    /** Дата рождения. */
    private LocalDate dateOfBirth;
    /** Подопечные животные. */
    private Set<Animal> animals;

    /**
     * Сотрудник.
     *
     * @param name ФИО
     * @param dateOfBirth дата рождения
     */
    public Employee(String name, LocalDate dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        animals = new HashSet<>();
    }

    /**
     * Поручить сотруднику животное.
     *
     * @param animal животное
     */
    public void add(Animal animal) {
        animals.add(animal);
    }

    /**
     * Снимаем ответственность за животное.
     *
     * @param animal животное
     */
    public void remove(Animal animal) {
        animals.remove(animal);
    }

    /**
     * Находится ли животное на попичении?
     *
     * @param animal животное
     * @return true, если находится
     */
    public boolean isCare(Animal animal) {
        return animals.contains(animal);
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", animals=" + animals +
                '}';
    }
}
