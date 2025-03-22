package Shapes;

import utils.PaintSettings;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class BrokenLine extends Shape {
    private List<Point> points;
    private Point tempPoint;
    private static final long serialVersionUID = 1L;
    public BrokenLine(PaintSettings paintSettings, Point startPoint, List<Point> points){
        super(paintSettings, startPoint);
        this.points = new ArrayList<>();
        this.points.add(startPoint);
    }

    public void addPoint(Point point){
        points.add(point);
    }

    public void updatePoint(Point newPoint){
        this.tempPoint = newPoint;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(paintSettings.getColor());
        g.setStroke(new BasicStroke(paintSettings.getStrokeWidth()));

        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        if (tempPoint != null && !points.isEmpty()) {
            Point last = points.get(points.size() - 1);
            g.drawLine(last.x, last.y, tempPoint.x, tempPoint.y);
        }

        if(selected) drawHandles(g);
    }

    @Override
    public void resize(Point handle, Point newPosition) {
        int index = points.indexOf(handle);
        if (index >= 0) {
            points.set(index, newPosition);
        }
    }

    @Override
    public void move(Point newPosition) {
        int dx = newPosition.x - position.x;
        int dy = newPosition.y - position.y;

        // Обновляем все точки
        for (Point p : points) {
            p.x += dx;
            p.y += dy;
        }
        position = newPosition;
    }

    @Override
    public List<Point> getHandles() {
        return new ArrayList<>(points);
    }

    private boolean isNearEdge(Point point) {
        for (int i = 0; i < points.size(); i++) {
            Point p1 = points.get(i);
            Point p2 = points.get((i+1) % points.size());
            if (distanceToSegment(p1, p2, point) <= 5) {
                return true;
            }
        }
        return false;
    }

    private double distanceToSegment(Point a, Point b, Point p) {
        double lengthSq = a.distanceSq(b);
        if (lengthSq == 0) return a.distance(p);

        double t = ((p.x - a.x)*(b.x - a.x) + (p.y - a.y)*(b.y - a.y)) / lengthSq;
        t = Math.max(0, Math.min(1, t));

        Point projection = new Point(
                (int)(a.x + t*(b.x - a.x)),
                (int)(a.y + t*(b.y - a.y))
        );
        return p.distance(projection);
    }

    @Override
    public boolean isHit(Point point) {
        java.awt.Polygon poly = new java.awt.Polygon(
                points.stream().mapToInt(p -> p.x).toArray(),
                points.stream().mapToInt(p -> p.y).toArray(),
                points.size()
        );
        return poly.contains(point) || isNearEdge(point);
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

    public List<Point> getPoints(){
        return new ArrayList<>(points);
    }

    public List<Point> getVertices() {
        return new ArrayList<>(points);
    }
}
