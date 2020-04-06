package ru.croc.java.winter.school.zoo.tracking.event;

import ru.croc.java.winter.school.zoo.tracking.actions.Interaction;

/**
 * Событие встречи сотрудников
 */
public class EmployeesMeetingEvent extends InteractionEvent {

    public EmployeesMeetingEvent(Interaction interaction) {
        super(interaction);
    }
}
