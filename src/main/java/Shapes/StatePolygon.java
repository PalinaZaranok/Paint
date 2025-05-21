package Shapes;

import java.awt.*;

public abstract class StatePolygon {
    protected Polygon polygon;

    public StatePolygon(Polygon polygon){
        this.polygon = polygon;
    }

    public void changeAddVertex(Point point){
        polygon.addVertex(point);
    }



}
