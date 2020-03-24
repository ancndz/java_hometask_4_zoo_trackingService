package ru.croc.java.winter.school.zoo.animal;

import java.time.LocalDateTime;

/**
 * Запись о болезни.
 */
public class IllnessRecord {
    /** Болезнь. */
    private final String name;
    /** Когда заболел. */
    private final LocalDateTime time;
    /** Дополнения. */
    private final String comment;

    /**
     * Запись о болезни.
     *
     * @param name болезнь
     * @param time когда заболел
     * @param comment дополнения
     */
    public IllnessRecord(String name, LocalDateTime time, String comment) {
        this.name = name;
        this.time = time;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "IllnessRecord{" +
                "name='" + name + '\'' +
                ", time=" + time +
                ", comment='" + comment + '\'' +
                '}';
    }
}
