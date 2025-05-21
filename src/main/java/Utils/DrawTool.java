package Utils;

import Shapes.Polygon;
import Shapes.Rectangle;
import Shapes.Shape;
import Shapes.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DrawTool implements Tool {
    private Shape prototype;
    private StatePolygon statePolygon;
    private PaintSettings settings;
    private Map<Class<?>, BiConsumer<Shape, Point>> updateActions = new HashMap<>();
    private Map<Class<?>, Function<Point, Shape>> createActions = new HashMap<>();

    public DrawTool(Shape prototype, PaintSettings settings) {
        this.settings = settings;
        this.prototype = prototype;
        initActions();
    }
    private void initActions(){
        createActions.put(Line.class, p -> new Line(settings, p, p));
        createActions.put(Rectangle.class, p -> new Rectangle(settings, p,0, 0));
        createActions.put(Circle.class, p -> new Circle(settings, p, 0));
        createActions.put(BrokenLine.class, p -> new BrokenLine(settings, p, new ArrayList<>()));
        createActions.put(Polygon.class, p -> new Polygon(settings, p, new ArrayList<>()));

        updateActions.put(Line.class, (s, p) -> ((Line) s).setEnd(p));
        updateActions.put(Rectangle.class, (s,p) -> {
            int width = p.x - s.getPosition().x;
            int height = p.y - s.getPosition().y;
            ((Rectangle) s).setSize(width, height);
        } );
        updateActions.put(Circle.class, (s,p) ->{
            int radius = (int) Math.hypot(p.x - s.getPosition().x, p.y - s.getPosition().y);
            ((Circle) s).setRadius(radius);
        });
        updateActions.put(Polygon.class, (s,p) -> ((Polygon) s).updateTempPoint(p));
        updateActions.put(BrokenLine.class, (s,p) -> ((BrokenLine) s).updatePoint(p));
    }
    @Override
    public void handleMousePress(Canvas canvas, MouseEvent event) {
        Function<Point, Shape> creator = createActions.get(prototype.getClass());
        if(creator != null) {
            canvas.currentShape = creator.apply(event.getPoint());
        }
    }

    @Override
    public void handleMouseDrag(Canvas canvas, MouseEvent event) {
        BiConsumer<Shape, Point> updater = updateActions.get(canvas.currentShape.getClass());
        if (updater != null){
            updater.accept(canvas.currentShape, event.getPoint());
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
