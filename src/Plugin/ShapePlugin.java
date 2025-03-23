package Plugin;

import Shapes.Shape;
import utils.PaintSettings;

import java.awt.*;

public interface ShapePlugin {
    String getShapeName();
    Class<? extends Shape> getShapeClass();
    Shape createShape(PaintSettings settings, Point... points);
    int getRequiredPoints();
}
