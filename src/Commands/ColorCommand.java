package Commands;

import Shapes.Shape;

import java.awt.*;

public class ColorCommand implements Command{
    private final Shape shape;
    private final Color oldColor;
    private final Color newColor;

    public ColorCommand(Shape shape, Color newColor){
        this.shape = shape;
        this.oldColor = shape.getPaintSettings().getColor();
        this.newColor = newColor;
    }
    @Override
    public void undo() {
        shape.getPaintSettings().setColor(oldColor);
    }

    @Override
    public void  execute() {
        shape.getPaintSettings().setColor(newColor);
    }
}
