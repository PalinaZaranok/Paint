package Shapes;

import utils.PaintSettings;

import java.awt.*;
public abstract class Shape {
    public PaintSettings paintSettings;
    protected Point position;

    public Shape(PaintSettings paintSettings, Point position){
        this.paintSettings = paintSettings;
        this.position = position;
    }
    public abstract void draw(Graphics2D g);
    public abstract void resize(double scale);
    public abstract void move(Point position);

    public Point getPosition(){
        return new Point(position);
    }
}
