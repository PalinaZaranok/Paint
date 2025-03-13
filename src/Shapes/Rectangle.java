package Shapes;

import utils.PaintSettings;

import java.awt.*;

public class Rectangle extends Shape {
    protected Point pointRight;
    private int width;
    private int height;
    public Rectangle(PaintSettings paintSettings, Point position, int width, int height){
        super(paintSettings, position);
        this.height = height;
        this.width = width;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(paintSettings.getColor());
        g.setStroke(new BasicStroke(paintSettings.getStrokeWidth()));

        int x = Math.min(position.x, position.x + width);
        int y = Math.min(position.y, position.y + height);
        int w = Math.abs(width);
        int h = Math.abs(height);

        g.drawRect(x, y, w, h);
    }

    @Override
    public void resize(double scale) {
        int newWidth = (int) Math.round(width * scale);
        int newHeight = (int) Math.round(height * scale);
        updateSize(newWidth, newHeight);
    }

    private void updateSize(int newWidth, int newHeight) {
        Point center = getCenter();
        this.width = Math.max(newWidth, 1);
        this.height = Math.max(newHeight, 1);
        setCenter(center);
    }

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
    public void move(Point newPosition) {
        this.position = newPosition;
    }
}
