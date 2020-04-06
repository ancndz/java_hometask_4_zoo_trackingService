package ru.croc.java.winter.school.zoo.tracking.event;

import ru.croc.java.winter.school.zoo.tracking.actions.Work;

/**
 * Событие заступления на смену
 */
public class EmployeeOnWorkdayEvent extends TrackingEvent{
    private final Work work;

    public EmployeeOnWorkdayEvent(Work work) {
        super(work.getStartTime());
        this.work = work;
    }

    public Work getWork() {
        return work;
    }
}
