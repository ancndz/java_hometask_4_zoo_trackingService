package ru.croc.java.winter.school.zoo.tracking.event;

import java.time.LocalDateTime;

/**
 * Событие отслеживания.
 */
public abstract class TrackingEvent {
    private final LocalDateTime time;

    public TrackingEvent(LocalDateTime time) {
        this.time = time;
    }

    /**
     * Время в которое произошло событие.
     *
     * @return время
     */
    public LocalDateTime getTime() {
        return time;
    }
}
