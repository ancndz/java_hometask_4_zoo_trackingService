package ru.croc.java.winter.school.zoo.tracking.finder;

import ru.croc.java.winter.school.zoo.tracking.Tracked;
import ru.croc.java.winter.school.zoo.tracking.actions.Interaction;
import ru.croc.java.winter.school.zoo.tracking.event.InteractionEvent;
import ru.croc.java.winter.school.zoo.tracking.location.Position;

import java.util.*;

/**
 * Поиск событий типа "встреча" (взаимодействие)
 * расширяет обычный поиск событий, добавляется метод для поиска события встречи
 */
public abstract class InteractionEventFinder implements EventFinder {
    /** Список незавершенных взаимодействий для каждого объекта. */
    private final Map<String, List<Interaction>> interactions = new HashMap<>();
    private final double interactionDistance;

    protected InteractionEventFinder(double interactionDistance) {
        this.interactionDistance = interactionDistance;
    }

    /**
     * Проверяет находятся ли два обекта в зоне взаимодействия.
     *
     * @param a а
     * @param b b
     * @return true, если взаимодействие есть в текущий момент
     */
    boolean isInteraction(Position a, Position b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2)) <= interactionDistance;
    }

    /**
     * Проверяет существует ли незаконченное взаимодействие между двуми объектами.
     *
     * @param a объект A
     * @param b объекта B
     * @return true, если да
     */
    boolean isExistsNotCompletedInteractionBetween(Tracked a, Tracked b) {
        return getNotCompletedInteractionBetween(a, b) != null;
    }

    /**
     * Находит незаконченное взаимодействие между двумя объектами.
     *
     * @param a объект A
     * @param b объекта B
     * @return взаимодействие, null иначе
     */
    Interaction getNotCompletedInteractionBetween(Tracked a, Tracked b) {
        final List<Interaction> interactionsForA = this.interactions.getOrDefault(a.getId(), Collections.emptyList());
        for (Interaction interaction : interactionsForA) {
            if (interaction.getWho() == b || interaction.getWith() == b) {
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
    void addInteraction(Interaction interaction) {
        if (!this.interactions.containsKey(interaction.getWho().getId())) {
            this.interactions.put(interaction.getWho().getId(), new ArrayList<>());
        }
        if (!this.interactions.containsKey(interaction.getWith().getId())) {
            this.interactions.put(interaction.getWith().getId(), new ArrayList<>());
        }
        this.interactions.get(interaction.getWho().getId()).add(interaction);
        this.interactions.get(interaction.getWith().getId()).add(interaction);
    }

    /**
     * Удаляет взаимодействие для прекращения отслеживнаия завершения.
     *
     * @param interaction взаимодействие
     */
    void removeInteraction(Interaction interaction) {
        this.interactions.get(interaction.getWho().getId()).remove(interaction);
        this.interactions.get(interaction.getWith().getId()).remove(interaction);
    }


}
