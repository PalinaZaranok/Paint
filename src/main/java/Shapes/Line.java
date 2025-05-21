package Shapes;

import Utils.PaintSettings;

import java.awt.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Line extends Shape implements Serializable {
    private Point endPoint;

    //private static final long serialVersionUID = 1L;
    private static final int HIT_TOLERANCE = 5;
    public Line(PaintSettings paintSettings, Point position, Point endPoint){
        super(paintSettings, position);
        this.endPoint = endPoint;
    }

    @Override
    public void draw(Graphics2D g){
        g.setColor(paintSettings.getColor());
        g.setStroke(new BasicStroke(paintSettings.getStrokeWidth()));
        g.drawLine(position.x, position.y, endPoint.x, endPoint.y);

    }

    @Override
    public void resize(Point handle, Point newPosition) {
        if (handle.equals(position)) {
            position = newPosition;
        } else if (handle.equals(endPoint)) {
            endPoint = newPosition;
        } else if (handle.equals(getCenter())) {
            Point delta = new Point(
                    newPosition.x - getCenter().x,
                    newPosition.y - getCenter().y
            );
            move(delta);
        }
    }


    @Override
    public void move(Point delta) {
        position.translate(delta.x, delta.y);
        endPoint.translate(delta.x, delta.y);
    }

    public void setEnd(Point end){
        this.endPoint= end;
    }
    public Point getEndPoint() {
        return endPoint;
    }
    @Override
    public boolean isHit(Point point) {
        return distanceToLine(point) <= HIT_TOLERANCE;
    }
    private Point getCenter() {
        return new Point(
                (position.x + endPoint.x) / 2,
                (position.y + endPoint.y) / 2
        );
    }
    @Override
    public List<Point> getHandles() {
        return Arrays.asList(position, endPoint, getCenter());
    }
    private double distanceToLine(Point p) {
        // Формула расстояния от точки до прямой
        double numerator = Math.abs(
                (endPoint.y - position.y)*p.x
                        - (endPoint.x - position.x)*p.y
                        + endPoint.x*position.y
                        - endPoint.y*position.x
        );
        double denominator = Math.sqrt(
                Math.pow(endPoint.y - position.y, 2)
                        + Math.pow(endPoint.x - position.x, 2)
        );
        return denominator == 0 ?
                position.distance(p) :
                numerator / denominator;
    }

}
