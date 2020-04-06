package ru.croc.java.winter.school.zoo.tracking.actions;

import ru.croc.java.winter.school.zoo.tracking.Tracked;

import java.time.LocalDateTime;

/**
 * Смена в зоопарке
 */
public class Work {
    private final Tracked who;
    private final LocalDateTime startTime;
    private LocalDateTime finishTime;

    /**
     * Заступление на смену
     * @param who - сотрудник
     * @param startTime - когда начал
     */
    public Work(Tracked who, LocalDateTime startTime) {
        this.who = who;
        this.startTime = startTime;
    }

    /**
     * Дата окончания смены (когда вышел из зоопаркка)
     * @return дата
     */
    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    /**
     * Устанавливаем дату завершения смены
     * @param finishTime - дата завершения смены
     */
    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public Tracked getWho() {
        return who;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}
