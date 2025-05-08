package utils;

import Commands.HistoryManager;
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
    public PaintSettings currentSettings;

    public void setTool(Tool tool) {
        this.currentTool = tool;
    }


    public void addShape(Shape shape) {
        historyManager.addShape(shape);
    }

    public void removeShape(Shape shape){
        historyManager.removeShape(shape);
    }
    public void clear(){
        historyManager.clear();
    }
    public void undo(){
        historyManager.undo();
    }
    public void redo(){
        historyManager.redo();
    }

    public boolean canUndo() {
        return historyManager.canUndo();
    }

    public boolean canRedo() {
        return historyManager.canRedo();
    }

    public void paintComponent(Graphics2D g) {
        historyManager.drawAll(g);
        if (currentShape != null) {
            currentShape.draw(g);
        }
    }

    public PaintSettings getCurrentPaintSettings() {
        return currentSettings;
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
            addShape(currentShape);
            currentShape = null;
        }
    }

}