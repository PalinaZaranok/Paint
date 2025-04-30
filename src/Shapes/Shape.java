package Shapes;

import utils.PaintSettings;
import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Shape implements Serializable {
    protected PaintSettings paintSettings;
    protected Point position;
    @Serial
    private static final long serialVersionUID = 1L;
    protected boolean selected;
    protected static final int HANDLE_SIZE = 8;

    public Shape(PaintSettings paintSettings, Point position){
        this.paintSettings = new PaintSettings(paintSettings);
        this.position = position;
    }
    public abstract void draw(Graphics2D g);
    public abstract void resize(Point handle, Point newPosition);
    public abstract void move(Point position);
    public Point getPosition(){
        return new Point(position);
    }
    public PaintSettings getPaintSettings(){return paintSettings;}
    public void updatePaintSettings(PaintSettings newSettings) {
        this.paintSettings = newSettings;
    }
    public abstract boolean isHit(Point point);
    public abstract List<Point> getHandles();
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
