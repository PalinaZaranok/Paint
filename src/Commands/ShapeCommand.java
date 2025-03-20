package Commands;

import Shapes.Shape;
import utils.Canvas;

public class ShapeCommand implements Command{

    private final Canvas canvas;
    private final Shape shape;

    public ShapeCommand(Canvas canvas, Shape shape){
        this.shape = shape;
        this.canvas = canvas;
    }

    @Override
    public void undo() {
        canvas.removeShape(shape);
    }

    @Override
    public void execute() {
        canvas.addShape(shape);
    }
}
