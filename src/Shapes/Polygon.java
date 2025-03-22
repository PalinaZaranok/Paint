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
        int []xPoints = new int[points.size()];
        int[] yPoints = new int[points.size()];

        g.setColor(paintSettings.getColor());
        g.setStroke(new BasicStroke(paintSettings.getStrokeWidth()));

        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            xPoints[i] = points.get(i).x;
            yPoints[i] = points.get(i).y;
            Point p2 = points.get(i + 1);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        if (tempPoint != null && !points.isEmpty()) {
            Point last = points.get(points.size() - 1);
            g.drawLine(last.x, last.y, tempPoint.x, tempPoint.y);
        }

        if(paintSettings.getFillColor() != null) {
            g.setColor(paintSettings.getFillColor());
            g.fillPolygon(xPoints, yPoints, points.size());
        }

        if(selected) drawHandles(g);
    }

    @Override
    public boolean isHit(Point point) {
        // Создаем объект java.awt.Polygon для точной проверки
        int[] xPoints = points.stream().mapToInt(p -> p.x).toArray();
        int[] yPoints = points.stream().mapToInt(p -> p.y).toArray();
        java.awt.Polygon awtPolygon = new java.awt.Polygon(xPoints, yPoints, points.size());

        // Проверяем попадание в сам полигон или близко к ребрам
        return awtPolygon.contains(point) || isNearEdge(point);
    }

    @Override
    public List<Point> getHandles() {
        return new ArrayList<>(points);
    }

    private boolean isNearEdge(Point p) {
        for (int i = 0; i < points.size(); i++) {
            Point a = points.get(i);
            Point b = points.get((i+1) % points.size());
            if (distanceToSegment(a, b, p) < HANDLE_SIZE) {
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
    public void move(Point delta) {
        // Обновляем все точки полигона
        for (Point point : points) {
            point.x += delta.x;
            point.y += delta.y;
        }
        // Обновляем базовую позицию
        position.x += delta.x;
        position.y += delta.y;
    }

    @Override
    public void resize(Point handle, Point newPosition) {
        int index = points.indexOf(handle);
        if (index >= 0) {
            points.set(index, newPosition);
        }
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
