package edu.luc.etl.cs313.android.shapes.model;

/**
 * A decorator indicating that a shape should be drawn as an outlined shape
 *  instead of a filled one.
 */
public class Outline implements Shape {

    protected final Shape shape;

    public Outline(final Shape shape) {
        this.shape = shape;
    }

    public Shape getShape() {
        return shape;
    }

    @Override
    public <Result> Result accept(final Visitor<Result> v) {
        // TODO your job
        /**
         * Accept method for the visitor pattern.
         * Calls the appropriate visitor method for an outlined shape.
         */
        return v.onOutline(this);
    }
}
