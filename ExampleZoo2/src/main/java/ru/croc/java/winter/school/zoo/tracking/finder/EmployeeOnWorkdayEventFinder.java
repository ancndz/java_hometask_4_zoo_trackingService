package ru.croc.java.winter.school.zoo.tracking.finder;

import ru.croc.java.winter.school.zoo.animal.Animal;
import ru.croc.java.winter.school.zoo.tracking.Tracked;
import ru.croc.java.winter.school.zoo.tracking.actions.Work;
import ru.croc.java.winter.school.zoo.tracking.event.EmployeeOnWorkdayEvent;
import ru.croc.java.winter.school.zoo.tracking.location.ZooArea;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Анализатор события {@link EmployeeOnWorkdayEvent}.
 */
public class EmployeeOnWorkdayEventFinder implements EventFinder {
    /**Фигура зоопарка*/
    private final ZooArea zooArea;
    /**Находится ли объект в зоопарке*/
    private final Map<String, Work> onWork = new HashMap<>();

    public EmployeeOnWorkdayEventFinder(ZooArea zooArea) {
        this.zooArea = zooArea;
    }

    @Override
    public List<EmployeeOnWorkdayEvent> findEvents(final Tracked updatedTracked, final Map<String, Tracked> trackable) {
        List<EmployeeOnWorkdayEvent> newEvents = new ArrayList<>();

        if (updatedTracked instanceof Animal) {
            return newEvents;
        }

        //проверили, на работае или нет
        boolean onWorkNow = zooArea.isInZoo(updatedTracked.getCurrentLocation().position);

        //если на работе
        if (onWorkNow) {
            //если уже отметили до этого
            if (onWork.containsKey(updatedTracked.getId())) {
                return Collections.emptyList();

                //если не отметили
            } else {
                Work event = new Work(updatedTracked, LocalDateTime.now());
                onWork.put(updatedTracked.getId(), event);
                newEvents.add(new EmployeeOnWorkdayEvent(event));
            }
            //если не на работе
        } else {
            //отмечаем уход
            if (onWork.containsKey(updatedTracked.getId())) {
                // работник не на работе, событие уже было, но проставим дату завершения
                // и удалим с отслеживания
                final Work work = onWork.get(updatedTracked.getId());
                work.setFinishTime(LocalDateTime.now());
                onWork.remove(work.getWho().getId());
                return Collections.emptyList();
                //его и не было
            } else {
                return Collections.emptyList();
            }
        }
        return newEvents;
    }

}
