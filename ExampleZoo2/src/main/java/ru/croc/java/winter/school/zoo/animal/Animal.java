package ru.croc.java.winter.school.zoo.animal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Животное.
 */
public class Animal {
    /** Название. */
    private String name;
    /** Дата рождения. */
    private LocalDate dateBirth;
    /** Журнал болезней. */
    private List<IllnessRecord> illnessRecords;

    /**
     * Животное.
     *
     * @param name название
     * @param dateBirth дата рождения
     */
    public Animal(String name, LocalDate dateBirth) {
        this.name = name;
        this.dateBirth = dateBirth;
        illnessRecords = new ArrayList<>();
    }

    /**
     * Добавляем запись в журнал болезней.
     *
     * @param illnessRecord запись о болезни.
     */
    public void add(IllnessRecord illnessRecord) {
        illnessRecords.add(illnessRecord);
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
