package ru.croc.java.winter.school.zoo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.croc.java.winter.school.zoo.animal.Animal;
import ru.croc.java.winter.school.zoo.animal.IllnessRecord;
import ru.croc.java.winter.school.zoo.employee.Employee;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;

public class ZooTest {


    @DisplayName("Простой сценарий")
    @Test
    public void test() {
        // Сотрудники
        final Employee bob = new Employee("Боб", LocalDate.of(1980, 3, 1));
        final Employee alise = new Employee("Алиса", LocalDate.of(1987, 7, 1));
        // Животные
        final Animal elephant = new Animal("Слон", LocalDate.now());
        final Animal monkey = new Animal("Обезьяна", LocalDate.now());

        final Zoo zoo = new Zoo("Африка рядом");
        zoo.add(bob, alise);
        zoo.add(elephant, bob);
        zoo.add(monkey, alise);

        monkey.add(new IllnessRecord("Грипп", LocalDateTime.now(), "-"));

        Assertions.assertEquals(new HashSet<>(Arrays.asList(bob, alise)), new HashSet<>(zoo.getEmployees()));
        zoo.remove(bob);
        Assertions.assertEquals(new HashSet<>(Arrays.asList(bob, alise)), new HashSet<>(zoo.getEmployees()));
        zoo.remove(elephant);
        zoo.remove(bob);
        Assertions.assertEquals(new HashSet<>(Arrays.asList(alise)), new HashSet<>(zoo.getEmployees()));
    }
}
