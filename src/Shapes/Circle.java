package Shapes;

import utils.PaintSettings;

import java.awt.*;
import java.io.Serializable;

public class Circle extends Shape {
    private double radius;
    private static final long serialVersionUID = 1L;
    public Circle(PaintSettings paintSettings, Point position, double radius){
        super(paintSettings, position);
        this.radius = radius;
    }

    @Override
    public void draw(Graphics2D g){
        g.setColor(paintSettings.getColor());
        g.setStroke(new BasicStroke(paintSettings.getStrokeWidth()));
        int diameter = (int) radius * 2;
        g.drawOval(position.x, position.y, diameter, diameter);
    }

    @Override
    public void resize(double scale){
        radius *= scale;
    }

    @Override
    public void move(Point newPosition){
        this.position = newPosition;
    }

    public void setRadius(double radius){
        this.radius = radius;
    }
}
