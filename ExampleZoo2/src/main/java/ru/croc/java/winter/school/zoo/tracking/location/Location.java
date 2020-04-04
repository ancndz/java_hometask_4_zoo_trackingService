package ru.croc.java.winter.school.zoo.tracking.location;

import java.time.LocalDateTime;

/**
 * Местоположение в момент времени.
 */
public class Location {
    /** Местоположение. */
    public final Position position;
    /** Время. */
    public LocalDateTime time;

    public Location(Position position, LocalDateTime time) {
        this.position = position;
        this.time = time;
    }
}
