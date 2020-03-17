package ru.croc.java.school.lecture3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.croc.java.school.lecture3.Array;

public class ArrayTest {
    private Array array;

    @BeforeEach
    public void init() {
        System.out.println("Init");
        array = new Array();
    }

    @Test
    public void testAddArray() {
        System.out.println("testAddArray");
        Assertions.assertArrayEquals(new String[]{}, array.values());
        array.add("Java");
        Assertions.assertArrayEquals(new String[]{"Java"}, array.values());
        array.add("Croc");
        Assertions.assertArrayEquals(new String[]{"Java", "Croc"}, array.values());
    }

    @Test
    public void testAddAndRemoveArray() {
        System.out.println("testRemoveArray");
        array.add("Java");
        array.add("Croc");
        Assertions.assertArrayEquals(new String[]{"Java", "Croc"}, array.values());
        array.remove("Croc");
        Assertions.assertArrayEquals(new String[]{"Java"}, array.values());
    }

    @AfterEach
    public void after() {
        System.out.println("After");
    }
}
