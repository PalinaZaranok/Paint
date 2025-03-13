package Shapes;

import utils.PaintSettings;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BrokenLine extends Shape {
    private List<Point> points;
    private Point tempPoint;
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
    }

    @Override
    public void resize(double scale) {
        Point center = calculateCenter();

        for (Point p : points) {
            int dx = p.x - center.x;
            int dy = p.y - center.y;
            p.x = center.x + (int) Math.round(dx * scale);
            p.y = center.y + (int) Math.round(dy * scale);
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

    // Метод для завершения построения многоугольника
    public void close() {
        if (points.size() > 2) {
            // Удаляем временную точку и замыкаем контур
            tempPoint = null;
        }
    }

    public List<Point> getVertices() {
        return new ArrayList<>(points);
    }
}
