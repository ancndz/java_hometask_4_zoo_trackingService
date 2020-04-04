package ru.croc.java.winter.school.zoo.tracking.interaction;

import ru.croc.java.winter.school.zoo.tracking.Tracked;

import java.time.LocalDateTime;

/**
 * Взаимодействие между двумя отслеживаемыми объектами.
 */
public class Interaction {
    private final Tracked a;
    private final Tracked b;
    private final LocalDateTime startTime;
    private LocalDateTime finishTime;

    /**
     * Взаимодействие между двумя отслеживаемыми объектами.
     *
     * @param a первый объект
     * @param b второй объект
     * @param startTime время начала взаимодействия
     */
    public Interaction(Tracked a, Tracked b, LocalDateTime startTime) {
        this.a = a;
        this.b = b;
        this.startTime = startTime;
        finishTime = null;
    }

    /**
     * Время окончания взаимодействия.
     *
     * @return время, null если объекты еще взаимодействуют
     */
    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    /**
     * Устанавливаем время окончания взаимодействия.
     *
     * @param finishTime время окончания
     */
    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public Tracked getA() {
        return a;
    }

    public Tracked getB() {
        return b;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}
