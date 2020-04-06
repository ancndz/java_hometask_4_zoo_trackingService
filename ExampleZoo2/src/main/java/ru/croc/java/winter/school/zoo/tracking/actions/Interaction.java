package ru.croc.java.winter.school.zoo.tracking.actions;

import ru.croc.java.winter.school.zoo.tracking.Tracked;

import java.time.LocalDateTime;

/**
 * Взаимодействие между двумя отслеживаемыми объектами.
 */
public class Interaction extends Work{
    private final Tracked with;

    /**
     * Взаимодействие между двумя отслеживаемыми объектами.
     *
     * @param a первый объект
     * @param b второй объект
     * @param startTime время начала взаимодействия
     */
    public Interaction(Tracked a, Tracked b, LocalDateTime startTime) {
        super(a, startTime);
        this.with = b;
    }

    public Tracked getWith() {
        return this.with;
    }

}
