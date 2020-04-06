package ru.croc.java.winter.school.zoo.tracking;

import ru.croc.java.winter.school.zoo.tracking.actions.Interaction;
import ru.croc.java.winter.school.zoo.tracking.actions.Work;
import ru.croc.java.winter.school.zoo.tracking.event.EmployeeAndAnimalInteractionEvent;
import ru.croc.java.winter.school.zoo.tracking.event.EmployeeOnWorkdayEvent;
import ru.croc.java.winter.school.zoo.tracking.event.EmployeesMeetingEvent;
import ru.croc.java.winter.school.zoo.tracking.event.TrackingEvent;
import ru.croc.java.winter.school.zoo.tracking.finder.*;
import ru.croc.java.winter.school.zoo.tracking.location.Position;
import ru.croc.java.winter.school.zoo.tracking.location.ZooArea;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
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
    /** Форма зоопарка зоопарка*/
    private final ZooArea zooArea;

    public TrackingService() {
        this.zooArea = new ZooArea(new Position(200, 100), new Position(200, -100),
                new Position(0, -100), new Position(0, 100), new Position(0, 0));

        this.trackable = new HashMap<>();
        this.events = new ArrayList<>();
        this.eventFinders = new ArrayList<>();
        this.eventFinders.add(new EmployeeAndAnimalInteractionEventFinder(3));
        this.eventFinders.add(new EmployeeOnWorkdayEventFinder(this.zooArea));
        this.eventFinders.add(new EmployeesMeetingEventFinder(3));
    }

    /**
     * Возвращает суммарную разницу между началом и концом каждого события встречи
     * выбранного работника с остальными (а так же где сам работник присутствует)
     * не считает те встречи, которые еще не закончены
     * @param employeeID - айди работника
     * @return - объект duration, общее время связей
     */
    public Duration hoursWithOtherEmployees(String employeeID) {
        Duration duration = Duration.between(LocalDateTime.now(), LocalDateTime.now());
        for (TrackingEvent each : this.events) {
            if (each instanceof EmployeesMeetingEvent) {
                Interaction interaction = ((EmployeesMeetingEvent) each).getInteraction();
                if ((interaction.getWho().getId().equals(employeeID) || interaction.getWith().getId().equals(employeeID))
                        && (interaction.getFinishTime() != null)) {
                    duration = duration.plus(Duration.between(interaction.getStartTime(), interaction.getFinishTime()));
                }
            }
        }
        return duration;
    }

    /**
     * Возвращает кол-во выходов персонала с животным с работы
     * @param employeeID - айди объекта
     * @return - int кол-во
     */
    public int employeeWalkWithAnimalCount(String employeeID) {
        int count = 0;
        List<LocalDateTime> exitingWorkDates = new ArrayList<>();
        //ищем все даты выхода объекта из зоопарка
        for (TrackingEvent each : this.events) {
            if (each instanceof EmployeeOnWorkdayEvent) {
                Work work = ((EmployeeOnWorkdayEvent) each).getWork();
                if (work.getWho().getId().equals(employeeID)) {
                    if (work.getFinishTime() != null) {
                        exitingWorkDates.add(work.getFinishTime());
                    }
                }
            }
        }
        //проверяем все случаи контакта сотрудника и животного
        for (TrackingEvent each : this.events) {
            if (each instanceof EmployeeAndAnimalInteractionEvent) {
                Interaction interaction = ((EmployeeAndAnimalInteractionEvent) each).getInteraction();
                //проверяем, если какая то дата выхода была ПОСЛЕ начала контакта И ПЕРЕД концом контакта -
                //фиксируем.
                for (LocalDateTime eachExitDate : exitingWorkDates) {
                    if (eachExitDate.isAfter(interaction.getStartTime()) &&
                            (interaction.getFinishTime() != null && eachExitDate.isBefore(interaction.getFinishTime()))) {
                        count++;
                    }
                }
            }
        }
        return count;
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
            events.addAll(eventFinder.findEvents(trackable.get(id), trackable));
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
