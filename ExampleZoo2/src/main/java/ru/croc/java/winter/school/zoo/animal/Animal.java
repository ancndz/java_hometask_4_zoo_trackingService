package ru.croc.java.winter.school.zoo.animal;

import ru.croc.java.winter.school.zoo.tracking.location.Location;
import ru.croc.java.winter.school.zoo.tracking.location.Position;
import ru.croc.java.winter.school.zoo.tracking.Tracked;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Животное.
 */
public class Animal implements Tracked {
    private static final String ID_PREFIX = "Animal-";
    private static int count = 0;

    /** Уникальный номер. */
    private final String id;
    /** Название. */
    private final String name;
    /** Дата рождения. */
    private final LocalDate dateBirth;
    /** Журнал болезней. */
    private final List<IllnessRecord> illnessRecords;
    /** Журнал местоположения во времени. */
    private final List<Location> locations;

    /**
     * Животное.
     *
     * @param name название
     * @param dateBirth дата рождения
     */
    public Animal(String name, LocalDate dateBirth) {
        this.id = ID_PREFIX + count++;
        this.name = name;
        this.dateBirth = dateBirth;
        illnessRecords = new ArrayList<>();
        locations = new ArrayList<>();
    }

    /**
     * Добавляем запись в журнал болезней.
     *
     * @param illnessRecord запись о болезни.
     */
    public void add(IllnessRecord illnessRecord) {
        illnessRecords.add(illnessRecord);
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

    public String getName() {
        return name;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public List<IllnessRecord> getIllnessRecords() {
        return illnessRecords;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", dateBirth=" + dateBirth +
                ", illnessRecords=" + illnessRecords +
                '}';
    }
}
