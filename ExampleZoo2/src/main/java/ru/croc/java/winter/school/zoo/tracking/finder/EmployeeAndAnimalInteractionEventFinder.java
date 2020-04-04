package ru.croc.java.winter.school.zoo.tracking.finder;

import ru.croc.java.winter.school.zoo.animal.Animal;
import ru.croc.java.winter.school.zoo.employee.Employee;
import ru.croc.java.winter.school.zoo.tracking.Tracked;
import ru.croc.java.winter.school.zoo.tracking.event.EmployeeAndAnimalInteractionEvent;
import ru.croc.java.winter.school.zoo.tracking.interaction.Interaction;
import ru.croc.java.winter.school.zoo.tracking.location.Position;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Анализатор события {@link EmployeeAndAnimalInteractionEvent}.
 */
public class EmployeeAndAnimalInteractionEventFinder implements EventFinder {
    /** Список незавершенных взаимодействий для каждого объекта. */
    private final Map<String, List<Interaction>> interactions = new HashMap<>();
    private final double interactionDistance;

    /**
     * Анализатор события {@link EmployeeAndAnimalInteractionEvent}.
     *
     * @param interactionDistance максимальное растояние, которое считается взаимодействием
     */
    public EmployeeAndAnimalInteractionEventFinder(double interactionDistance) {
        this.interactionDistance = interactionDistance;
    }

    @Override
    public List<EmployeeAndAnimalInteractionEvent> findNext(Tracked updatedTracked, Map<String, Tracked> trackable) {
        final List<EmployeeAndAnimalInteractionEvent> newEvents = new ArrayList<>();
        for (Tracked trackedA : trackable.values()) {
            if (trackedA == updatedTracked || trackedA.getCurrentLocation() == null) {
                continue;
            }

            final EmployeeAndAnimalInteractionEvent event = findInteractionEvent(updatedTracked, trackedA);
            if (event != null) {
                newEvents.add(event);
            }
        }
        return newEvents;
    }

    /**
     * Поиск события взаимодействия сотрудника и животного.
     *
     * @param trackedA первый объект
     * @param trackedB второй объект
     */
    private EmployeeAndAnimalInteractionEvent findInteractionEvent(Tracked trackedA, Tracked trackedB) {
        final boolean animalAndEmployee = trackedA instanceof Animal && trackedB instanceof Employee
                || trackedA instanceof Employee && trackedB instanceof Animal;
        if (!animalAndEmployee) {
            return null;
        }
        //
        final boolean currentInteraction = isInteraction(
                trackedA.getCurrentLocation().position,
                trackedB.getCurrentLocation().position
        );

        if (currentInteraction) {
            if (isExistsNotCompletedInteractionBetween(trackedA, trackedB)) {
                return null; // взаимодействие уже было зафиксировано и не прекратилось
            }

            // Взаимодействие новое, генерируем событие и добавляем отслеживание завершения
            final Interaction interaction = new Interaction(trackedA, trackedB, LocalDateTime.now());
            addInteraction(interaction);
            return new EmployeeAndAnimalInteractionEvent(interaction);
        } else {
            if (!isExistsNotCompletedInteractionBetween(trackedA, trackedB)) {
                return null; // взаимодействия не было и нет
            }

            // взаимодействие прекратилось, событие уже было, но проставим дату завершения
            // и удалим с отслеживания
            final Interaction interaction = getNotCompletedInteractionBetween(trackedA, trackedB);
            interaction.setFinishTime(LocalDateTime.now());
            removeInteraction(interaction);
            return null;
        }
    }

    /**
     * Проверяет находятся ли два обекта в зоне взаимодействия.
     *
     * @param a а
     * @param b b
     * @return true, если взаимодействие есть в текущий момент
     */
    private boolean isInteraction(Position a, Position b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2)) <= interactionDistance;
    }

    /**
     * Проверяет существует ли незаконченное взаимодействие между двуми объектами.
     *
     * @param a объект A
     * @param b объекта B
     * @return true, если да
     */
    private boolean isExistsNotCompletedInteractionBetween(Tracked a, Tracked b) {
        return getNotCompletedInteractionBetween(a, b) != null;
    }

    /**
     * Находит незаконченное взаимодействие между двуми объектами.
     *
     * @param a объект A
     * @param b объекта B
     * @return взаимодействие, null иначе
     */
    private Interaction getNotCompletedInteractionBetween(Tracked a, Tracked b) {
        final List<Interaction> interactionsForA = interactions.getOrDefault(a.getId(), Collections.emptyList());
        for (Interaction interaction : interactionsForA) {
            if (interaction.getA() == b || interaction.getB() == b) {
                return interaction;
            }
        }
        return null;
    }

    /**
     * Добавляет взаимодействие для отслеживнаия завершения.
     *
     * @param interaction взаимодействие
     */
    private void addInteraction(Interaction interaction) {
        if (!interactions.containsKey(interaction.getA().getId())) {
            interactions.put(interaction.getA().getId(), new ArrayList<>());
        }
        if (!interactions.containsKey(interaction.getB().getId())) {
            interactions.put(interaction.getB().getId(), new ArrayList<>());
        }
        interactions.get(interaction.getA().getId()).add(interaction);
        interactions.get(interaction.getB().getId()).add(interaction);
    }

    /**
     * Удаляет взаимодействие для прекращения отслеживнаия завершения.
     *
     * @param interaction взаимодействие
     */
    private void removeInteraction(Interaction interaction) {
        interactions.get(interaction.getA().getId()).remove(interaction);
        interactions.get(interaction.getB().getId()).remove(interaction);
    }

}
