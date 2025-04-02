package utils;

import Commands.ColorCommand;
import Commands.HistoryManager;
import Commands.ShapeCommand;
import Shapes.Polygon;
import Shapes.Shape;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Canvas {
    public List<Shape> shapes = new ArrayList<>();
    private final HistoryManager historyManager = new HistoryManager();
    private Tool currentTool;
    public Shape currentShape;

    public void setTool(Tool tool) {
        this.currentTool = tool;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
        historyManager.executeCommand(new ShapeCommand(this, shape));
    }

    public void removeShape(Shape shape){
        shapes.remove(shape);
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
        if (currentTool != null) {
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

    public void changeShapeColor(Shape shape, Color newColor) {
        historyManager.executeCommand(new ColorCommand(shape, newColor));
    }

    public void undo() {
        historyManager.undo();
        //repaint();
    }

    public void redo() {
        historyManager.redo();
       // repaint();
    }
}