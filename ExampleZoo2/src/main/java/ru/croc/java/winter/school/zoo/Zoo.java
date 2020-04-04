package ru.croc.java.winter.school.zoo;

import ru.croc.java.winter.school.zoo.animal.Animal;
import ru.croc.java.winter.school.zoo.employee.Employee;
import ru.croc.java.winter.school.zoo.tracking.TrackingService;

import java.util.ArrayList;
import java.util.List;

/**
 * Зоопарк.
 */
public class Zoo {
    /** Название. */
    private String title;
    /** Животные. */
    private List<Animal> animals;
    /** Сотрудники. */
    private List<Employee> employees;
    /** Сервис отслеживания. */
    private TrackingService trackingService;

    /**
     * Зоопарк.
     *
     * @param title название
     */
    public Zoo(String title) {
        this.title = title;
        animals = new ArrayList<>();
        employees = new ArrayList<>();
        trackingService = new TrackingService();
    }

    /**
     * Добавляем животное.
     *
     * @param animal животное
     * @param employee опекун
     */
    public void add(Animal animal, Employee employee) {
        if (!animals.contains(animal) && employees.contains(employee)) {
            animals.add(animal);
            employee.add(animal);
            trackingService.add(animal);
        } else {
            // TODO что делать
        }
    }

    /**
     * Добавляем сотрудника.
     *
     * @param employee сотрудник
     */
    public void add(Employee employee) {
        employees.add(employee);
        trackingService.add(employee);
    }

    /**
     * Добавляем сотрудника.
     *
     * @param employees сотрудники
     */
    public void add(Employee... employees) {
        for (Employee employee : employees) {
            add(employee);
        }
    }

    /**
     * Удаляем животное.
     *
     * @param animal животное
     */
    public void remove(Animal animal) {
        for (Employee employee : employees) {
            if (employee.isCare(animal)) {
                employee.remove(animal);
            }
        }
        trackingService.remove(animal);
        animals.remove(animal);
    }

    /**
     * Удаление животного.
     *
     * @param employee сотрудник
     */
    public void remove(Employee employee) {
        if (employees.contains(employee) && employee.getAnimals().isEmpty()) {
            employees.remove(employee);
            trackingService.remove(employee);
        } else {
            // TODO учим исключения
        }
    }


    public String getTitle() {
        return title;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public TrackingService getTrackingService() {
        return trackingService;
    }

    @Override
    public String toString() {
        return "Zoo{" +
                "title='" + title + '\'' +
                ", animals=" + animals +
                ", employees=" + employees +
                '}';
    }
}
