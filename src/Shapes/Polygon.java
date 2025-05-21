package Shapes;

import Utils.PaintSettings;

import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class Polygon extends Shape {
    private List<Point> points;
    private Point tempPoint;
    public boolean isClosed = false;

    //private static final long serialVersionUID = 1L;
    public Polygon(PaintSettings paintSettings, Point startPoint, List<Point> points){
        super(paintSettings, startPoint);
        this.points = new ArrayList<>();
        this.points.add(startPoint);
        if (points != null) {
            this.points.addAll(points);
        }
    }


    @Override
    public void draw(Graphics2D g) {

        // Конвертируем точки в массивы координат
        int[] xPoints = points.stream().mapToInt(p -> p.x).toArray();
        int[] yPoints = points.stream().mapToInt(p -> p.y).toArray();

        // 1. Отрисовка контура (всегда)
        g.setColor(paintSettings.getColor());
        g.setStroke(new BasicStroke(paintSettings.getStrokeWidth()));

        // Рисуем линии между точками
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        // Временная линия (если полигон не замкнут)
        if (tempPoint != null && !isClosed) {
            Point last = points.get(points.size() - 1);
            g.drawLine(last.x, last.y, tempPoint.x, tempPoint.y);
        }

        // 2. Заливка и замыкающая линия (только после закрытия)
        if (isClosed) {
            // Замыкаем фигуру
            Point first = points.get(0);
            Point last = points.get(points.size() - 1);
            g.drawLine(first.x, first.y, last.x, last.y);

            // Заливаем, если задан цвет
            if (paintSettings.getFillColor() != null) {
                g.setColor(paintSettings.getFillColor());
                g.fillPolygon(xPoints, yPoints, points.size());
            }
        }
    }

    @Override
    public boolean isHit(Point point) {
        // stream создает поток из коллекции
        // mapToInt(p -> p.x) преобразует точку в ее координату и возвращет поток IntStream
        java.awt.Polygon awtPolygon = new java.awt.Polygon(
            points.stream().mapToInt(p->p.x).toArray(),
            points.stream().mapToInt(p->p.y).toArray(),
            points.size()
        );
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
        if (index != -1) {
            points.set(index, newPosition);
        }
    }
    public void addVertex(Point point) {
        if (!isClosed) {
            points.add(point);
        }
    }

    public void updateLastPoint(Point point) {
        if (!points.isEmpty() && !isClosed) {
            points.set(points.size() - 1, point);
        }
    }

    public void updateTempPoint(Point point) {
        this.tempPoint = point;
    }

    public void close() {
        if (points.size() >= 2) {
            isClosed = true;
        }
    }

    public List<Point> getVertices() {
        return points;
    }

}
