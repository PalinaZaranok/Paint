package Shapes;

import utils.PaintSettings;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;


public class Polygon extends Shape {
    private List<Point> points;
    private Point tempPoint;
    private static final long serialVersionUID = 1L;
    public Polygon(PaintSettings paintSettings, Point startPoint, List<Point> points){
        super(paintSettings, startPoint);
        this.points = new ArrayList<>();
        this.points.add(startPoint);
    }

    @Override
    public void draw(Graphics2D g){
        g.setColor(paintSettings.getColor());
        g.setStroke(new BasicStroke(paintSettings.getStrokeWidth()));

        if (points.size() > 1) {
            int[] xPoints = points.stream().mapToInt(p -> p.x).toArray();
            int[] yPoints = points.stream().mapToInt(p -> p.y).toArray();
            g.drawPolygon(xPoints, yPoints, points.size());
        }

        if (tempPoint != null && !points.isEmpty()) {
            Point last = points.get(points.size() - 1);
            g.drawLine(last.x, last.y, tempPoint.x, tempPoint.y);
        }
    }

    @Override
    public void resize(double scale){
        Point center = calculateCenter();

        for (Point p : points) {
            int dx = p.x - center.x;
            int dy = p.y - center.y;
            p.x = center.x + (int) Math.round(dx * scale);
            p.y = center.y + (int) Math.round(dy * scale);
        }
    }

    private Point calculateCenter() {
        if (points.isEmpty()) return new Point(0, 0);

        int sumX = 0;
        int sumY = 0;
        for (Point p : points) {
            sumX += p.x;
            sumY += p.y;
        }
        return new Point(sumX / points.size(), sumY / points.size());
    }

    @Override
    public void move(Point newPosition) {
        int dx = newPosition.x - position.x;
        int dy = newPosition.y - position.y;

        for (Point p : points) {
            p.x += dx;
            p.y += dy;
        }
        position = newPosition;
    }

    public void addVertex(Point point) {
        points.add(point);
    }

    public void updateTempPoint(Point point) {
        this.tempPoint = point;
    }

    public void close() {
        if (points.size() > 2) {
            points.add(new Point(points.get(0)));
        }
    }

}
