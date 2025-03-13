package edu.luc.etl.cs313.android.shapes.model;

/**
 * A visitor to compute the number of basic shapes in a (possibly complex)
 * shape.
 */
public class Count implements Visitor<Integer> {

    // TODO entirely your job

    @Override
    public Integer onPolygon(final Polygon p) {
        /** A polygon is a basic shape, so return 1. */
        return 1;
    }

    @Override
    public Integer onCircle(final Circle c) {
        /** A circle is a basic shape, so return 1. */
        return 1;
    }

    @Override
    public Integer onGroup(final Group g) {
        /** A group consists of multiple shapes. We recursively count all shapes inside it. */
        int count = 0;
        for (Shape s : g.getShapes()) {
            count += s.accept(this); // Recursively count shapes in the group
        }
        return count;
    }

    @Override
    public Integer onRectangle(final Rectangle q) {
        /** A rectangle is a basic shape, so return 1. */
        return 1;
    }

    @Override
    public Integer onOutline(final Outline o) {
        /** An outlined shape still contains the same shape, so delegate the counting to it. */
        return o.getShape().accept(this);
    }

    @Override
    public Integer onFill(final Fill c) {
        /** A filled shape still contains the same shape, so delegate the counting to it. */
        return c.getShape().accept(this);
    }

    @Override
    public Integer onLocation(final Location l) {
        /** places an existing shape...
         Delegate the counting to the contained shape. */
        return l.getShape().accept(this);
    }

    @Override
    public Integer onStrokeColor(final StrokeColor c) {
        /** modifies an existing shape...
         Delegate the counting to the contained shape. */
        return c.getShape().accept(this);
    }
}
