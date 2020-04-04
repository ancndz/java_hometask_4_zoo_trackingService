package ru.croc.java.winter.school.zoo.tracking.finder;

import ru.croc.java.winter.school.zoo.tracking.Tracked;
import ru.croc.java.winter.school.zoo.tracking.event.TrackingEvent;

import java.util.List;
import java.util.Map;

/**
 * Поиск событий определенного типа.
 */
public interface EventFinder {
    /**
     * Ищет событие для отслеживаемого объекта, у которого изменились координаты.
     *
     * @param updatedTracked обновленый отслеживаемый объект
     * @param trackable все отслеживаемые объекты
     * @return событие, если обнаружено.
     */
    List<? extends TrackingEvent> findNext(final Tracked updatedTracked, final Map<String, Tracked> trackable);
}
