package ru.croc.java.winter.school.zoo.tracking.location;

public class ZooArea {
    /** Координаты углов*/
    private final Position rightUpperCorner;
    private final Position rightBottomCorner;
    private final Position leftBottomCorner;
    private final Position leftUpperCorner;
    /** Координаты выхода*/
    private final Position exitPosition;

    public ZooArea(Position rightUpperCorner, Position rightBottomCorner, Position leftBottomCorner, Position leftUpperCorner, Position exitPosition) {
        this.rightUpperCorner = rightUpperCorner;
        this.rightBottomCorner = rightBottomCorner;
        this.leftBottomCorner = leftBottomCorner;
        this.leftUpperCorner = leftUpperCorner;
        this.exitPosition = exitPosition;
    }

    /**
     * Проверяет, находится ли данный объект (его позиция) внутри зоопарка
     * @param entityPos - позиция объекта
     * @return - истина или ложь
     */
    public boolean isInZoo(Position entityPos) {
        return ((entityPos.x < this.rightUpperCorner.x && entityPos.x > this.leftBottomCorner.x) &&
                (entityPos.y < this.rightUpperCorner.y && entityPos.y > this.leftBottomCorner.y));
    }

}
