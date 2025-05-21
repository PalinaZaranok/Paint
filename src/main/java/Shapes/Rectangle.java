package Shapes;

import Utils.PaintSettings;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Rectangle extends Shape {
    private int width;
    private int height;

    public Rectangle(PaintSettings paintSettings, Point position, int width, int height){
        super(paintSettings, position);
        this.height = height;
        this.width = width;
    }

    @Override
    public void draw(Graphics2D g) {
        int x = Math.min(position.x, position.x + width);
        int y = Math.min(position.y, position.y + height);
        int w = Math.abs(width);
        int h = Math.abs(height);

        if(paintSettings.getFillColor() != null) {
            g.setColor(paintSettings.getFillColor());
            g.fillRect(position.x, position.y, width, height);
        }
        g.setColor(paintSettings.getColor());
        g.setStroke(new BasicStroke(paintSettings.getStrokeWidth()));
        g.drawRect(x, y, w, h);

    }
    @Override
    public List<Point> getHandles() {
        return Arrays.asList(position,
                new Point(position.x + width, position.y),
                new Point(position.x + width, position.y + height),
                new Point(position.x, position.y + height)
        );
    }
    @Override
    public boolean isHit(Point point) {
        return point.x >= position.x - 3 && point.x <= position.x + width + 3 &&
                point.y >= position.y - 3 && point.y <= position.y + height + 3;
    }
    @Override
    public void resize(Point handlePoint, Point newPosition) {
        int handleIndex = getHandles().indexOf(handlePoint);

        switch (handleIndex) {
            case 0: // Top-left
                width += position.x - newPosition.x;
                height += position.y - newPosition.y;
                position = newPosition;
                break;
            case 1: // Top-right
                width = newPosition.x - position.x;
                height += position.y - newPosition.y;
                position.y = newPosition.y;
                break;
            case 2: // Bottom-right
                width = newPosition.x - position.x;
                height = newPosition.y - position.y;
                break;
            case 3: // Bottom-left
                width += position.x - newPosition.x;
                height = newPosition.y - position.y;
                position.x = newPosition.x;
                break;
        }

        width = Math.max(width, 10);
        height = Math.max(height, 10);
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    private Point getCenter() {
        return new Point(
                position.x + width / 2,
                position.y + height / 2
        );
    }

    private void setCenter(Point center) {
        position.x = center.x - width / 2;
        position.y = center.y - height / 2;
    }

    public void setSize(int width, int height) {
        this.width = Math.abs(width); // Защита от отрицательных значений
        this.height = Math.abs(height);

        // Корректируем позицию для сохранения точки начала рисования
        if (width < 0) position.x += width;
        if (height < 0) position.y += height;
    }
    @Override
    public void move(Point delta) {
        position.translate(delta.x, delta.y);
    }
}
