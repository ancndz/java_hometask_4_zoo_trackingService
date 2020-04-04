package ru.croc.java.winter.school.zoo.tracking;

import ru.croc.java.winter.school.zoo.animal.Animal;
import ru.croc.java.winter.school.zoo.employee.Employee;
import ru.croc.java.winter.school.zoo.tracking.event.EmployeeAndAnimalInteractionEvent;
import ru.croc.java.winter.school.zoo.tracking.event.TrackingEvent;
import ru.croc.java.winter.school.zoo.tracking.finder.EmployeeAndAnimalInteractionEventFinder;
import ru.croc.java.winter.school.zoo.tracking.finder.EventFinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Сервис отслеживания {@link Tracked}.
 */
public class TrackingService {
    /** Отслеживаемые объекты. ид -> объект. */
    private final Map<String, Tracked> trackable;
    /** Журнал событий. */
    private final List<TrackingEvent> events;
    /** Анализаторы событий. */
    private final List<EventFinder> eventFinders;

    public TrackingService() {
        trackable = new HashMap<>();
        events = new ArrayList<>();
        eventFinders = new ArrayList<>();
        eventFinders.add(new EmployeeAndAnimalInteractionEventFinder(1));
    }

    /**
     * Добавляем новый объект для отслеживания.
     *
     * @param tracked новый объект
     */
    public void add(Tracked tracked) {
        trackable.put(tracked.getId(), tracked);
    }

    /**
     * Пришли данные с GPS-датчика(обработанные).
     *
     * @param id ид отсл. объекта
     * @param x Х
     * @param y Y
     */
    public void update(String id, double x, double y) {
        if (!trackable.containsKey(id)) {
            return;
        }

        trackable.get(id).updatePosition(x, y);
        for (EventFinder eventFinder : eventFinders) {
            events.addAll(eventFinder.findNext(trackable.get(id), trackable));
        }
    }


    /**
     * Снимаем слежение с объекта.
     *
     * @param tracked объект
     */
    public void remove(Tracked tracked) {
        trackable.remove(tracked);
    }

    public Set<Tracked> getTrackable() {
        return new HashSet<>(trackable.values());
    }

    public List<TrackingEvent> getEvents() {
        return events;
    }
}
