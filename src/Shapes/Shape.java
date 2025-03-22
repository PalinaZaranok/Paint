package Shapes;

import utils.PaintSettings;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Shape implements Serializable {
    protected PaintSettings paintSettings;
    protected Point position;
    private static final long serialVersionUID = 1L;
    protected boolean selected;
    protected static final int HANDLE_SIZE = 8;

    public Shape(PaintSettings paintSettings, Point position){
        this.paintSettings = paintSettings;
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
   protected void drawHandles(Graphics2D g) {
       g.setColor(Color.BLUE);
       for (Point handle : getHandles()) {
           g.fillRect(handle.x - HANDLE_SIZE/2,
                   handle.y - HANDLE_SIZE/2,
                   HANDLE_SIZE, HANDLE_SIZE);
       }
   }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
