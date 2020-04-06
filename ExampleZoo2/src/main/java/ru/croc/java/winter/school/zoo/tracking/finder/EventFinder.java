package ru.croc.java.winter.school.zoo.tracking.finder;

import ru.croc.java.winter.school.zoo.tracking.Tracked;
import ru.croc.java.winter.school.zoo.tracking.actions.Interaction;
import ru.croc.java.winter.school.zoo.tracking.actions.Work;
import ru.croc.java.winter.school.zoo.tracking.event.TrackingEvent;

import java.util.List;
import java.util.Map;

/**
 * Поиск событий определенного типа.
 */
public interface EventFinder {

    /**
     * Ищет события для отслеживаемого объекта, у которого изменились координаты.
     * @param updatedTracked - выбранный объект
     * @param trackable - все отслеживаемые объекты
     * @return - список событий
     */
    List<? extends TrackingEvent> findEvents(final Tracked updatedTracked, final Map<String, Tracked> trackable);
}
