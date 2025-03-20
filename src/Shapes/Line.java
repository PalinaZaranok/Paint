package Shapes;

import utils.PaintSettings;

import java.awt.*;
import java.io.Serializable;

public class Line extends Shape {
    private Point endPoint;
    private static final long serialVersionUID = 1L;
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
    public void resize(double scale){

        Point start = position;
        Point end = endPoint;

        // Вычисляем центр линии
        int centerX = (start.x + end.x) / 2;
        int centerY = (start.y + end.y) / 2;

        // Рассчитываем смещения относительно центра
        int dxStart = start.x - centerX;
        int dyStart = start.y - centerY;
        int dxEnd = end.x - centerX;
        int dyEnd = end.y - centerY;

        // Применяем масштабирование с округлением
        int scaledDxStart = (int) Math.round(dxStart * scale);
        int scaledDyStart = (int) Math.round(dyStart * scale);
        int scaledDxEnd = (int) Math.round(dxEnd * scale);
        int scaledDyEnd = (int) Math.round(dyEnd * scale);

        // Обновляем координаты точек
        position = new Point(centerX + scaledDxStart, centerY + scaledDyStart);
        endPoint = new Point(centerX + scaledDxEnd, centerY + scaledDyEnd);
    }

    @Override
    public void move(Point newPosition){
        this.position = newPosition;
    }

    public void setEnd(Point end){
        this.endPoint= end;
    }
}
