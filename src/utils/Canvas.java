package utils;

import Shapes.Shape;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Canvas {
    public List<Shape> shapes = new ArrayList<>();
    private Tool currentTool;
    protected Shape currentShape;

    public void setTool(Tool tool) {
        this.currentTool = tool;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public void clear(){
        shapes.clear();
    }

    public void paintComponent(Graphics2D g) {
        for (Shape shape : shapes) {
            shape.draw(g);
        }
        if (currentShape != null) {
            currentShape.draw(g);
        }
    }

    public void handleMousePress(MouseEvent event) {
        if (currentTool != null && currentShape == null) {
            currentTool.handleMousePress(this, event);
        }
    }

    public void handleMouseDrag(MouseEvent event) {
        if (currentTool != null && currentShape != null) {
            currentTool.handleMouseDrag(this, event);
        }
    }

    public void handleMouseRelease(MouseEvent event) {
        if (currentTool != null && currentShape != null) {
            currentTool.handleMouseRelease(this, event);
            shapes.add(currentShape);
            currentShape = null;
        }
    }
}