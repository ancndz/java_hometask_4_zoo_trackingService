package ru.croc.java.winter.school.zoo.tracking.event;

import ru.croc.java.winter.school.zoo.tracking.actions.Interaction;

/**
 * Событие взаимодействия сотрудника и животного.
 */
public class EmployeeAndAnimalInteractionEvent extends InteractionEvent {

    public EmployeeAndAnimalInteractionEvent(Interaction interaction) {
        super(interaction);
    }
}
