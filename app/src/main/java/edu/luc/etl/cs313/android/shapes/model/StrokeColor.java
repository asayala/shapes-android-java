package edu.luc.etl.cs313.android.shapes.model;

/**
 * A decorator for specifying the stroke (foreground) color for drawing the
 * shape.
 */
public class StrokeColor implements Shape {

    // TODO entirely your job

    private final int color; // The color of the stroke
    private final Shape shape; // The shape being decorated

    /**
     * Constructs a shape with a specific stroke color.
     * @param color the stroke color
     * @param shape the shape to be colored
     */
    public StrokeColor(final int color, final Shape shape) {
        this.color = color;
        this.shape = shape;
    }

    public int getColor() {
        /**
         * Returns the color of the stroke.
         */
        return color;
    }

    public Shape getShape() {
        /**
         * Returns the shape that is being decorated.
         */
        return shape;
    }

    @Override
    public <Result> Result accept(Visitor<Result> v) {
        /**
         * Accept method for the visitor pattern.
         * Calls the appropriate visitor method for a stroke-colored shape.
         */
        return v.onStrokeColor(this);
    }
}
