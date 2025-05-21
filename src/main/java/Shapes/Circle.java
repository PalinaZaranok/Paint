package Shapes;

import Utils.PaintSettings;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Circle extends Shape{
    private int radius;

    //private static final long serialVersionUID = 1L;
    private static final int MIN_RADIUS = 5;
    public Circle(PaintSettings paintSettings, Point position, int radius){
        super(paintSettings, position);
        this.radius = radius;
    }

    @Override
    public void draw(Graphics2D g) {
        int diameter = radius * 2;

        if (paintSettings.getFillColor() != null) {
            g.setColor(paintSettings.getFillColor());
            g.fillOval(position.x, position.y, diameter, diameter);
        }

        g.setColor(paintSettings.getColor());
        g.setStroke(new BasicStroke(paintSettings.getStrokeWidth()));
        g.drawOval(position.x, position.y, diameter, diameter);

    }
    @Override
    public List<Point> getHandles() {
        return Arrays.asList(position, new Point(position.x + radius, position.y));
    }
    @Override
    public boolean isHit(Point point) {
        return position.distance(point) <= radius + 5;
    }

    @Override
    public void resize(Point handle, Point newPosition){
        if (handle.equals(position)) {
            move(new Point(
                    newPosition.x - position.x,
                    newPosition.y - position.y
            ));
        } else {
            radius = (int) position.distance(newPosition);
            radius = Math.max(radius, MIN_RADIUS);
        }
    }
    @Override
    public void move(Point delta) {
        position.translate(delta.x, delta.y);
    }

    public void setRadius(int radius){
        this.radius = radius;
    }

}
