package Plugin;

import Shapes.Shape;
import Utils.PaintSettings;
import java.awt.*;

public interface ShapePlugin {
    String getShapeName(); // Название фигуры (отображается в UI)
    Shape createShape(PaintSettings settings, Point startPoint); // Создание экземпляра
}
