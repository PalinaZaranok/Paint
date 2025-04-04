package utils;

import Shapes.Polygon;
import Shapes.Rectangle;
import Shapes.Shape;
import Shapes.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DrawTool implements Tool {
    private Shape prototype;
    private PaintSettings settings;

    public DrawTool(Shape prototype, PaintSettings settings) {
        this.settings = settings;
        this.prototype = prototype;
    }

    @Override
    public void handleMousePress(Canvas canvas, MouseEvent event) {
        Point point = event.getPoint();

        List<Point> points = new ArrayList<>();
        List<Point> polygonPoints = new ArrayList<>();

        switch (prototype.getClass().getSimpleName()){
            case "Line":
                canvas.currentShape = new Line(settings, point, point);
                break;
            case "Rectangle":
                canvas.currentShape = new Rectangle(settings, point,0,0);
                break;
            case "Circle":
                canvas.currentShape = new Circle(settings, point, 0);
                break;
            case "Polygon":
                canvas.currentShape = new Polygon(settings, point, polygonPoints);
                break;
            case "BrokenLine":
                canvas.currentShape = new BrokenLine(settings, point, points);
                break;
        }
    }

    @Override
    public void handleMouseDrag(Canvas canvas, MouseEvent event) {
        Point current = event.getPoint();
        if (canvas.currentShape == null) return;
        switch (canvas.currentShape.getClass().getSimpleName()) {
            case "Line":
                ((Line) canvas.currentShape).setEnd(current);
                break;
            case "Rectangle":
                int width = current.x - canvas.currentShape.getPosition().x;
                int height = current.y - canvas.currentShape.getPosition().y;
                ((Rectangle) canvas.currentShape).setSize(width, height);
                break;
            case "Circle":
                int radius = (int) Math.hypot(
                        current.x - canvas.currentShape.getPosition().x,
                        current.y - canvas.currentShape.getPosition().y
                );
                ((Circle) canvas.currentShape).setRadius(radius);
                break;
            case "Polygon":
                ((Polygon)canvas.currentShape).updateTempPoint(current);
                break;
            case "BrokenLine":
                ((BrokenLine)canvas.currentShape).updatePoint(current);
                break;
        }
    }


    @Override
    public void handleMouseRelease(Canvas canvas, MouseEvent e) {
        if (canvas.currentShape != null){
            if(canvas.currentShape instanceof Polygon){
                ((Polygon)canvas.currentShape).addVertex(e.getPoint());
            }
        }
    }

}
