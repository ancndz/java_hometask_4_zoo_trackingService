package ru.croc.java.winter.school.zoo.tracking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.croc.java.winter.school.zoo.Zoo;
import ru.croc.java.winter.school.zoo.animal.Animal;
import ru.croc.java.winter.school.zoo.employee.Employee;
import ru.croc.java.winter.school.zoo.tracking.actions.Work;
import ru.croc.java.winter.school.zoo.tracking.event.EmployeeAndAnimalInteractionEvent;
import ru.croc.java.winter.school.zoo.tracking.actions.Interaction;
import ru.croc.java.winter.school.zoo.tracking.event.EmployeeOnWorkdayEvent;
import ru.croc.java.winter.school.zoo.tracking.event.EmployeesMeetingEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Проверка сервиса отслеживания объектов в зоопарке.
 */
public class TrackingServiceTest {
    private Zoo zoo;
    private Employee bob;
    private Employee alice;
    private Animal elephant;

    @BeforeEach
    public void init() {
        // Сотрудники
        bob = new Employee("Боб", LocalDate.of(1980, 3, 1));
        alice = new Employee("Алиса", LocalDate.of(1987, 7, 1));
        // Животные
        elephant = new Animal("Слон", LocalDate.now());
        final Animal monkey = new Animal("Обезьяна", LocalDate.now());

        final Zoo zoo = new Zoo("Африка рядом");
        zoo.add(bob, alice);
        zoo.add(elephant, bob);
        zoo.add(monkey, alice);

        this.zoo = zoo;
    }

    @DisplayName("Проверка журнала отслеживания животных")
    @Test
    public void testJournalOfAnimalTracking() throws InterruptedException {
        final TrackingService trackingService = zoo.getTrackingService();

        final Animal lion = new Animal("Лев", LocalDate.of(1990, 3, 8));
        final Set<Tracked> animalsAndEmployees = new HashSet<>();
        animalsAndEmployees.addAll(zoo.getAnimals());
        animalsAndEmployees.addAll(zoo.getEmployees());
        Assertions.assertEquals(animalsAndEmployees, trackingService.getTrackable());

        Assertions.assertFalse(trackingService.getTrackable().contains(lion));
        zoo.add(lion, bob);
        Assertions.assertTrue(trackingService.getTrackable().contains(lion));

        final LocalDateTime beforeTime = LocalDateTime.now();
        Thread.sleep(1);
        trackingService.update(lion.getId(), 0, 0);
        Thread.sleep(1);
        final LocalDateTime betweenTime = LocalDateTime.now();
        Thread.sleep(1);
        trackingService.update(lion.getId(), 10, 10);
        Thread.sleep(1);
        final LocalDateTime afterTime = LocalDateTime.now();

        Assertions.assertTrue(lion.getLocations().get(0).time.isAfter(beforeTime));
        Assertions.assertTrue(lion.getLocations().get(0).time.isBefore(betweenTime));

        Assertions.assertTrue(lion.getLocations().get(1).time.isAfter(betweenTime));
        Assertions.assertTrue(lion.getLocations().get(1).time.isBefore(afterTime));

    }


    @DisplayName("Проверка отслеживания событий взаимодействия животных")
    @Test
    public void testInteractionEmployeeAndAnimal() {
        /*
        final TrackingService trackingService = zoo.getTrackingService();

        // начальные позиции
        trackingService.update(bob.getId(), 0, 0);
        trackingService.update(elephant.getId(), 10, 10);
        Assertions.assertTrue(trackingService.getEvents().isEmpty());

        // Боб подошел к слону
        trackingService.update(bob.getId(), 10, 10);
        Assertions.assertEquals(1, trackingService.getEvents().size());
        final Interaction interaction = ((EmployeeAndAnimalInteractionEvent) trackingService.getEvents().get(0))
                .getInteraction();
        Assertions.assertEquals(interaction.getWho(), bob);
        Assertions.assertEquals(interaction.getWith(), elephant);
        Assertions.assertNull(interaction.getFinishTime());

        // Боб продолжает стоять рядом со слоном
        trackingService.update(bob.getId(), 10.01, 9.99);
        trackingService.update(elephant.getId(), 9.98, 10.001);
        Assertions.assertEquals(1, trackingService.getEvents().size());
        Assertions.assertNull(interaction.getFinishTime());

        // Слон убежал от Боба
        trackingService.update(elephant.getId(), 5.01, 5.99);
        Assertions.assertEquals(1, trackingService.getEvents().size());
        Assertions.assertNotNull(interaction.getFinishTime());

        // Боб догнал слона
        trackingService.update(bob.getId(), 4.98, 6.02);
        Assertions.assertEquals(2, trackingService.getEvents().size());
         */
    }

    @Test
    public void testOnWorkEvent() throws InterruptedException {
        final TrackingService trackingService = zoo.getTrackingService();
        // начальные позиции
        trackingService.update(bob.getId(), -1, 0);
        trackingService.update(elephant.getId(), 10, 10);
        Assertions.assertTrue(trackingService.getEvents().isEmpty());

        LocalDateTime timeBefore = LocalDateTime.now();
        Thread.sleep(10);
        //боб пришел на работу
        trackingService.update(bob.getId(), 15, 15);
        Thread.sleep(1000);
        LocalDateTime timeAfter = LocalDateTime.now();
        Assertions.assertTrue(trackingService.getEvents().get(0).getTime().isAfter(timeBefore));
        Assertions.assertTrue(trackingService.getEvents().get(0).getTime().isBefore(timeAfter));

        //боб ушел с работы, отработав секунду
        trackingService.update(bob.getId(), -1, 0);
        Assertions.assertNotNull(((EmployeeOnWorkdayEvent) trackingService.getEvents().get(0)).getWork().getFinishTime());
    }

    @Test
    public void testEmployeesMeeting() {
        final TrackingService trackingService = zoo.getTrackingService();
        // начальные позиции - на работе
        trackingService.update(bob.getId(), 10, 0);
        trackingService.update(alice.getId(), 15, 0);

        //проверили, что оба на работе
        Assertions.assertEquals(((EmployeeOnWorkdayEvent) trackingService.getEvents().get(0)).getWork().getWho().getId(), bob.getId());
        Assertions.assertEquals(((EmployeeOnWorkdayEvent) trackingService.getEvents().get(1)).getWork().getWho().getId(), alice.getId());
        //проверили, что больше событий нет
        Assertions.assertEquals(2, trackingService.getEvents().size());

        //алиса подошла к бобу, это событие встречи сотрудников
        trackingService.update(alice.getId(), 10.9, 0);
        Assertions.assertTrue(trackingService.getEvents().get(2) instanceof EmployeesMeetingEvent);

        //слон подошел к бобу, это НЕ событие встречи сотрудников
        trackingService.update(elephant.getId(), 10, 0);
        Assertions.assertFalse(trackingService.getEvents().get(3) instanceof EmployeesMeetingEvent);
    }


    @Test
    void testHoursWithOtherEmployees() throws InterruptedException {
        final TrackingService trackingService = zoo.getTrackingService();
        // начальные позиции - на работе
        trackingService.update(bob.getId(), 10, 0);
        trackingService.update(alice.getId(), 15, 0);

        //алиса подошла к бобу, это событие встречи сотрудников
        trackingService.update(alice.getId(), 10.9, 0);
        Assertions.assertTrue(trackingService.getEvents().get(2) instanceof EmployeesMeetingEvent);
        Thread.sleep(1000);
        //алиса ушла от боба спустя ОДНУ секунду
        trackingService.update(alice.getId(), 15, 0);
        //нам что то вернулось, и это что то не нулл
        Assertions.assertNotNull(trackingService.hoursWithOtherEmployees(bob.getId()));

        //алиса еще раз подошла к бобу
        trackingService.update(alice.getId(), 10.9, 0);
        Thread.sleep(2000);
        //алиса ушла от боба спустя ДВЕ секунды
        trackingService.update(alice.getId(), 15, 0);

        Assertions.assertEquals(3, trackingService.hoursWithOtherEmployees(bob.getId()).getSeconds());
    }

    @Test
    void testEmployeeWalkWithAnimalCount() throws InterruptedException {
        final TrackingService trackingService = zoo.getTrackingService();
        // начальные позиции - на работе
        trackingService.update(bob.getId(), 1, 0);
        trackingService.update(elephant.getId(), 10, 10);
        //слон подошел к бобу
        trackingService.update(elephant.getId(), 1.1, 0);
        //второе событие - взаимодействие
        Assertions.assertTrue(trackingService.getEvents().get(1) instanceof EmployeeAndAnimalInteractionEvent);

        //боб ушел с работы
        Thread.sleep(1000);
        trackingService.update(bob.getId(), -1, 0);
        Assertions.assertNotNull(((EmployeeOnWorkdayEvent) trackingService.getEvents().get(0)).getWork().getFinishTime());
        //слон ушел с бобом
        trackingService.update(elephant.getId(), -1.1, 0);
        //оба пришли обратно
        Thread.sleep(2000);
        trackingService.update(bob.getId(), 1, 0);
        //новое событие - боб пришел на работу
        Assertions.assertTrue(trackingService.getEvents().get(2) instanceof EmployeeOnWorkdayEvent);
        //слон тоже пришел
        trackingService.update(elephant.getId(), 1.1, 0);
        Thread.sleep(1000);
        //слон ушел в вольер
        trackingService.update(elephant.getId(), 10, 0);
        Assertions.assertNotNull(((EmployeeAndAnimalInteractionEvent) trackingService.getEvents().get(1)).getInteraction().getFinishTime());

        //боб один раз вывел слона из зоопарка
        Assertions.assertEquals(1, trackingService.employeeWalkWithAnimalCount(bob.getId()));

        //повторим

        //слон подошел к бобу
        trackingService.update(elephant.getId(), 1.1, 0);
        //боб ушел с работы
        Thread.sleep(1000);
        trackingService.update(bob.getId(), -1, 0);
        Assertions.assertNotNull(((EmployeeOnWorkdayEvent) trackingService.getEvents().get(2)).getWork().getFinishTime());
        //слон ушел с бобом
        trackingService.update(elephant.getId(), -1.1, 0);
        //оба пришли обратно
        Thread.sleep(2000);
        trackingService.update(bob.getId(), 1, 0);
        //новое событие - боб пришел на работу
        Assertions.assertTrue(trackingService.getEvents().get(4) instanceof EmployeeOnWorkdayEvent);
        //слон тоже пришел
        trackingService.update(elephant.getId(), 1.1, 0);
        Thread.sleep(1000);
        //слон ушел в вольер
        trackingService.update(elephant.getId(), 10, 0);
        Assertions.assertNotNull(((EmployeeAndAnimalInteractionEvent) trackingService.getEvents().get(3)).getInteraction().getFinishTime());

        //боб два раза вывел слона из зоопарка
        Assertions.assertEquals(2, trackingService.employeeWalkWithAnimalCount(bob.getId()));
    }

}
