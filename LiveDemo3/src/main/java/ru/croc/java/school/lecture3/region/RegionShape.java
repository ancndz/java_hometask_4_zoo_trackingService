package ru.croc.java.school.lecture3.region;

import ru.croc.java.school.lecture3.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class RegionShape {
    private List<Shape> areas = new ArrayList<Shape>();

    public void addArea(Shape area) { areas.add(area); }
    public void removeArea(Shape area) { areas.remove(area); }

    public double area() {
        double totalArea = 0.0;
        for (Shape area : areas) {
            totalArea += area.area();
        }
        return totalArea;
    }
}
