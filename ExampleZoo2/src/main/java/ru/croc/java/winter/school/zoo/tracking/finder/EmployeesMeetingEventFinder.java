package ru.croc.java.winter.school.zoo.tracking.finder;

import ru.croc.java.winter.school.zoo.employee.Employee;
import ru.croc.java.winter.school.zoo.tracking.Tracked;
import ru.croc.java.winter.school.zoo.tracking.actions.Interaction;
import ru.croc.java.winter.school.zoo.tracking.event.EmployeesMeetingEvent;
import ru.croc.java.winter.school.zoo.tracking.event.InteractionEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeesMeetingEventFinder extends InteractionEventFinder {
    /**
     * Анализатор события {@link EmployeesMeetingEvent}.
     *
     * @param interactionDistance максимальное растояние, которое считается взаимодействием
     */
    public EmployeesMeetingEventFinder(double interactionDistance) {
        super(interactionDistance);
    }

    @Override
    public List<EmployeesMeetingEvent> findEvents(Tracked updatedTracked, Map<String, Tracked> trackable) {
        final List<EmployeesMeetingEvent> newEvents = new ArrayList<>();
        for (Tracked trackedA : trackable.values()) {
            if (trackedA == updatedTracked || trackedA.getCurrentLocation() == null) {
                continue;
            }

            final EmployeesMeetingEvent event = findInteractionEvent(updatedTracked, trackedA);
            if (event != null) {
                newEvents.add(event);
            }
        }
        return newEvents;
    }

    /**
     * Произошло ли событие встречи двух сотрудников?
     * @param trackedA первый объект
     * @param trackedB второй объект
     * @return если да - событие, null в обратном случае.
     */
    public EmployeesMeetingEvent findInteractionEvent(Tracked trackedA, Tracked trackedB) {
        final boolean EmpAndEmp = trackedA instanceof Employee && trackedB instanceof Employee;
        if (!EmpAndEmp) {
            return null;
        }
        //
        final boolean currentInteraction = super.isInteraction(
                trackedA.getCurrentLocation().position,
                trackedB.getCurrentLocation().position
        );

        if (currentInteraction) {
            if (super.isExistsNotCompletedInteractionBetween(trackedA, trackedB)) {
                return null; // взаимодействие уже было зафиксировано и не прекратилось
            }

            // Взаимодействие новое, генерируем событие и добавляем отслеживание завершения
            final Interaction interaction = new Interaction(trackedA, trackedB, LocalDateTime.now());
            super.addInteraction(interaction);
            return new EmployeesMeetingEvent(interaction);
        } else {
            if (!super.isExistsNotCompletedInteractionBetween(trackedA, trackedB)) {
                return null; // взаимодействия не было и нет
            }

            // взаимодействие прекратилось, событие уже было, но проставим дату завершения
            // и удалим с отслеживания
            final Interaction interaction = getNotCompletedInteractionBetween(trackedA, trackedB);
            interaction.setFinishTime(LocalDateTime.now());
            super.removeInteraction(interaction);
            return null;
        }
    }

}
