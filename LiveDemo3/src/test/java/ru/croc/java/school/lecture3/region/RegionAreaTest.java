package ru.croc.java.school.lecture3.region;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.croc.java.school.lecture3.shape.Area;
import ru.croc.java.school.lecture3.shape.Rectangle;

import java.util.ArrayList;
import java.util.Collections;

public class RegionAreaTest {

    @Test
    public void test() {
        final RegionArea region = new RegionArea();
        Assertions.assertEquals(0.0, region.area());

        region.addArea(new Rectangle(1, 1));
        Assertions.assertEquals(1.0, region.area());

        region.addArea(new Area() {
            @Override
            public double area() {
                return 5;
            }
        });
        Assertions.assertEquals(6.0, region.area());

        region.addArea(new AnyName(7));
        Assertions.assertEquals(13.0, region.area());
    }
}
