package ru.croc.java.school.lecture3.region;

import ru.croc.java.school.lecture3.shape.Area;

public class AnyName implements Area {
    private final double value;

    public AnyName(double value) {
        this.value = value;
    }

    @Override
    public double area() {
        return value;
    }
}