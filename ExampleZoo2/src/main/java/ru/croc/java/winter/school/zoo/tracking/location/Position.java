package ru.croc.java.winter.school.zoo.tracking.location;

/**
 * Местоположение в зоопарке.
 */
public class Position {
    /** Координата OX. */
    public final double x;
    /** Координата OY. */
    public final double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Фабричный метод для {@link Position}.
     *
     * @param x координата OX
     * @param y коордианта OY
     * @return позиция
     */
    public static Position of(double x, double y) {
        return new Position(x, y);
    }
}
