package edu.luc.etl.cs313.android.shapes.model;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

    // TODO entirely your job (except onCircle)

    @Override
    public Location onCircle(final Circle c) {
        final int radius = c.getRadius();
        return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
    }

    @Override
    public Location onFill(final Fill f) {
        return f.getShape().accept(this); // Bounding box is same as the inner shape
    }

    @Override
    public Location onGroup(final Group g) {
        if (g.getShapes().isEmpty()) {
            return new Location(0, 0, new Rectangle(0, 0)); // Empty group has no bounding box
        }

        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

        for (Shape s : g.getShapes()) {
            Location bbox = s.accept(this);
            if (bbox != null) {
                int x = bbox.getX();
                int y = bbox.getY();
                int width = ((Rectangle) bbox.getShape()).getWidth();
                int height = ((Rectangle) bbox.getShape()).getHeight();

                minX = Math.min(minX, x);
                minY = Math.min(minY, y);
                maxX = Math.max(maxX, x + width);
                maxY = Math.max(maxY, y + height);
            }
        }

        return new Location(minX, minY, new Rectangle(maxX - minX, maxY - minY));
    }

    @Override
    public Location onLocation(final Location l) {
        Location innerBox = l.getShape().accept(this);
        if (innerBox == null) return null;
        return new Location(l.getX() + innerBox.getX(), l.getY() + innerBox.getY(), innerBox.getShape());
    }

    @Override
    public Location onRectangle(final Rectangle r) {
        return new Location(0, 0, r); // The rectangle is its own bounding box
    }

    @Override
    public Location onStrokeColor(final StrokeColor c) {
        return c.getShape().accept(this); // Bounding box is same as the inner shape
    }

    @Override
    public Location onOutline(final Outline o) {
        return o.getShape().accept(this); // Outline doesn't change bounding box
    }

    @Override
    public Location onPolygon(final Polygon p) {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

        for (Point pt : p.getPoints()) {
            minX = Math.min(minX, pt.getX());
            minY = Math.min(minY, pt.getY());
            maxX = Math.max(maxX, pt.getX());
            maxY = Math.max(maxY, pt.getY());
        }

        return new Location(minX, minY, new Rectangle(maxX - minX, maxY - minY));
    }
}