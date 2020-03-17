package ru.croc.java.school.lecture3.region;

import ru.croc.java.school.lecture3.shape.Area;
import ru.croc.java.school.lecture3.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class RegionArea {
    private List<Area> areas = new ArrayList<>();

    public void addArea(Area area) {
        areas.add(area);
    }

    public void removeArea(Area area) {
        areas.remove(area);
    }

    public double area() {
        double totalArea = 0.0;
        for (Area area : areas) {
            totalArea += area.area();
        }
        return totalArea;
    }
}
