package edu.luc.etl.cs313.android.shapes.model;

/**
 * A point, implemented as a location without a shape.
 */
public class Point extends Location {

    // TODO your job
    // HINT: use a circle with radius 0 as the shape!

    public Point(final int x, final int y) {
        /**
         * Constructs a point at (x, y).
         *  A point has no real shape, so we represent it using a circle of radius 0.
         */
        super(x, y, new Circle(0)); // Represent a point using a zero-radius circle
        assert x >= 0;
        assert y >= 0;
    }
}
