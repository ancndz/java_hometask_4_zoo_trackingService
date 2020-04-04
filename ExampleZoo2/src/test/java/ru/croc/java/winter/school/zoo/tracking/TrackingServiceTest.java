package ru.croc.java.winter.school.zoo.tracking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.croc.java.winter.school.zoo.Zoo;
import ru.croc.java.winter.school.zoo.animal.Animal;
import ru.croc.java.winter.school.zoo.employee.Employee;
import ru.croc.java.winter.school.zoo.tracking.event.EmployeeAndAnimalInteractionEvent;
import ru.croc.java.winter.school.zoo.tracking.interaction.Interaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Проверка сервиса отслеживания объектов в зоопарке.
 */
public class TrackingServiceTest {
    private Zoo zoo;
    private Employee bob;
    private Animal elephant;

    @BeforeEach
    public void init() {
        // Сотрудники
        bob = new Employee("Боб", LocalDate.of(1980, 3, 1));
        final Employee alise = new Employee("Алиса", LocalDate.of(1987, 7, 1));
        // Животные
        elephant = new Animal("Слон", LocalDate.now());
        final Animal monkey = new Animal("Обезьяна", LocalDate.now());

        final Zoo zoo = new Zoo("Африка рядом");
        zoo.add(bob, alise);
        zoo.add(elephant, bob);
        zoo.add(monkey, alise);

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
        Assertions.assertEquals(interaction.getA(), bob);
        Assertions.assertEquals(interaction.getB(), elephant);
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
    }

}
