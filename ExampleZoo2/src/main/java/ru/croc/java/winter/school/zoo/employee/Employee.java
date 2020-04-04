package ru.croc.java.winter.school.zoo.employee;

import ru.croc.java.winter.school.zoo.animal.Animal;
import ru.croc.java.winter.school.zoo.tracking.location.Location;
import ru.croc.java.winter.school.zoo.tracking.location.Position;
import ru.croc.java.winter.school.zoo.tracking.Tracked;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Сотрудник.
 */
public class Employee implements Tracked {
    private static final String ID_PREFIX = "Employee-";
    private static int count = 0;

    /** Уникальный номер. */
    private final String id;
    /** Имя. */
    private String name;
    /** Дата рождения. */
    private LocalDate dateOfBirth;
    /** Подопечные животные. */
    private Set<Animal> animals;
    /** Журнал местоположения во времени. */
    private final List<Location> locations;

    /**
     * Сотрудник.
     *
     * @param name ФИО
     * @param dateOfBirth дата рождения
     */
    public Employee(String name, LocalDate dateOfBirth) {
        this.id = ID_PREFIX + count++;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        animals = new HashSet<>();
        locations = new ArrayList<>();
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

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void updatePosition(double x, double y) {
        locations.add(new Location(Position.of(x ,y), LocalDateTime.now()));
    }

    @Override
    public List<Location> getLocations() {
        return locations;
    }

    @Override
    public Location getCurrentLocation() {
        return locations.isEmpty() ? null : locations.get(locations.size() - 1);
    }
}
