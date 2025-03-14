package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import edu.luc.etl.cs313.android.shapes.model.*;

/**
 * A Visitor for drawing a shape to an Android canvas.
 */
public class Draw implements Visitor<Void> {

    // TODO entirely your job (except onCircle)

    private final Canvas canvas;

    private final Paint paint;

    public Draw(final Canvas canvas, final Paint paint) {
        this.canvas = canvas; // FIXME
        this.paint = paint; // FIXME
        paint.setStyle(Style.STROKE);
    }

    @Override
    public Void onCircle(final Circle c) {
        canvas.drawCircle(0, 0, c.getRadius(), paint);
        return null;
    }

    @Override
    public Void onStrokeColor(final StrokeColor c) {
        int previousColor = paint.getColor();  // Save current color
        paint.setColor(c.getColor());  // Set new stroke color
        c.getShape().accept(this);  // Draw the shape with the new color
        paint.setColor(previousColor);  // Restore previous color
        return null;
    }

    @Override
    public Void onFill(final Fill f) {
        Style previousStyle = paint.getStyle(); // Save previous style
        paint.setStyle(Style.FILL_AND_STROKE); // Set fill while in stroke style
        f.getShape().accept(this); // Draw the shape with fill
        paint.setStyle(previousStyle); // Restore previous style
        return null;
    }
    @Override
    public Void onGroup(final Group g) {
        for (Shape shape : g.getShapes()) {
            shape.accept(this); // Draw each shape in the group
        }
        return null;
    }


    @Override
    public Void onLocation(final Location l) {
        /**canvas.save(); // Save current canvas state
        canvas.translate(l.getX(), l.getY()); // Move to new location
        l.getShape().accept(this); // Draw the shape at new location
        canvas.restore(); // Restore canvas state*/

        canvas.translate(l.getX(), l.getY()); // Moves the canvas to the specified location
        l.getShape().accept(this); // Draws the contained shape at the new translated position
        canvas.translate(-l.getX(), -l.getY()); // Moves the canvas back to the original position
        return null;
    }

    @Override
    public Void onRectangle(final Rectangle r) {
        canvas.drawRect(0, 0, r.getWidth(), r.getHeight(), paint);
        return null;
    }

    @Override
    public Void onOutline(final Outline o) {
        Style previousStyle = paint.getStyle(); // Save previous style
        paint.setStyle(Style.STROKE); // Ensure stroke style
        o.getShape().accept(this); // Draw the outlined shape
        paint.setStyle(previousStyle); // Restore previous style
        return null;
    }

    @Override
    public Void onPolygon(final Polygon s) {
        // Convert list of points into a float array for drawLines
        Point[] points = s.getPoints().toArray(new Point[0]);

        // If there are less than two points, no polygon can be drawn, so return null
        if (points.length < 2) return null;

        // Creates a float array to store the coordinates for drawing lines
        // (points.length - 1) * 4 because each line needs 4 values (x1, y1, x2, y2)
        float[] pts = new float[(points.length - 1) * 4];

        // Iterates over the points and extract pairs of consecutive points to form lines
        for (int i = 0; i < points.length - 1; i++) {
            Point p1 = points[i]; // Current point
            Point p2 = points[i + 1]; // Next point

            // Sets the coordinates for the line between p1 and p2 in the pts array
            pts[i * 4] = p1.getX();
            pts[i * 4 + 1] = p1.getY();
            pts[i * 4 + 2] = p2.getX();
            pts[i * 4 + 3] = p2.getY();
        }

        // Uses the canvas to draw lines based on the points stored in the pts array
        canvas.drawLines(pts, paint);
        return null;
    }
}