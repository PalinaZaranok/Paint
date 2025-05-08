package Commands;

import Shapes.Shape;
import java.awt.*;
import java.util.*;
import java.util.List;

public class HistoryManager {
    private List<Shape> currentShapes = new ArrayList<>();
    private Deque<List<Shape>> undoStack = new ArrayDeque<>();
    private Deque<List<Shape>> redoStack = new ArrayDeque<>();

    public void addShape(Shape shape) {
        if (!redoStack.isEmpty()) {
            redoStack.clear();
        }
        undoStack.push(new ArrayList<>(currentShapes));
        currentShapes.add(shape);
    }

    public void removeShape(Shape shape) {
        if (!redoStack.isEmpty()) {
            redoStack.clear();
        }
        undoStack.push(new ArrayList<>(currentShapes));
        currentShapes.remove(shape);
    }

    public void clear() {
        if (!currentShapes.isEmpty()) {
            undoStack.push(new ArrayList<>(currentShapes));
            redoStack.clear();
            currentShapes.clear();
        }
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    public void undo() {
        if (!canUndo()) return;
        redoStack.push(new ArrayList<>(currentShapes));
        currentShapes = undoStack.pop();
    }

    public void redo() {
        if (!canRedo()) return;
        undoStack.push(new ArrayList<>(currentShapes));
        currentShapes = redoStack.pop();
    }

    public List<Shape> getShapes() {
        return new ArrayList<>(currentShapes);
    }

    public void drawAll(Graphics2D g) {
        for (Shape shape : currentShapes) {
            shape.draw(g);
        }
    }
}