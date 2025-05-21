package Utils;

import Shapes.Polygon;
import Shapes.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DrawingPanel extends JPanel {
    private Shape selectedShape;
    private Point selectedHandle;
    private Point dragStart;
    private Canvas canvas;
    private boolean ctrlPressed = false;
    private Polygon currentPolygon = null;

    public DrawingPanel(Canvas canvas) {
        this.canvas = canvas;
        setDoubleBuffered(true);
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_CONTROL){
                    ctrlPressed = true;
                }
            }
            @Override
            public void keyReleased(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_CONTROL){
                    ctrlPressed = false;

                    if (currentPolygon != null && currentPolygon.getVertices().size() >= 2) {
                        // Добавляем соединение начальной и конечной точки
                        currentPolygon.close();
                        canvas.addShape(currentPolygon);
                        currentPolygon = null;
                        repaint();
                    }
                }
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocusInWindow();
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if(e.isControlDown()){
                        if (ctrlPressed){
                            if (currentPolygon == null) {
                                currentPolygon = new Polygon(new PaintSettings(canvas.currentSettings), e.getPoint(), new ArrayList<>());
                                currentPolygon.addVertex(e.getPoint());
                            }
                            repaint();
                            return;
                        }
                    }
                    if (selectedShape != null) {
                        selectedShape.setSelected(false);
                        selectedShape = null;
                    }
                    for (int i = canvas.shapes.size() - 1; i >= 0; i--) {
                        Shape shape = canvas.shapes.get(i);
                        if (shape.isHit(e.getPoint())) {
                            selectedShape = shape;
                            selectedShape.setSelected(true);
                            dragStart = e.getPoint();
                            selectedHandle = null;

                            repaint();
                            return;
                        }
                    }
                    canvas.handleMousePress(e);
                    repaint();
                }
            }

            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount() == 2 && canvas.currentShape instanceof Polygon){
                    ((Polygon)canvas.currentShape).close();
                    canvas.currentShape = null;
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (ctrlPressed && currentPolygon != null) {
                        currentPolygon.addVertex(e.getPoint());
                    } else if (canvas.currentShape != null) {
                        canvas.addShape(canvas.currentShape);
                        canvas.currentShape = null;
                    }
                    repaint();
                }
                selectedShape = null;
                selectedHandle = null;
                dragStart = null;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (ctrlPressed && currentPolygon != null) {
                        // Обновление временной линии для многоугольника
                        currentPolygon.updateTempPoint(e.getPoint());
                    } else if (canvas.currentShape != null) {
                        // Обновление параметров текущей фигуры
                        canvas.handleMouseDrag(e);
                    }
                    repaint();
                    // Если линия выбрана - обрабатываем только её перемещение
                    if (selectedShape != null && dragStart != null) {
                        Point delta = new Point(
                                e.getX() - dragStart.x,
                                e.getY() - dragStart.y
                        );

                        if (selectedHandle != null) {
                            selectedShape.resize(selectedHandle, e.getPoint());
                        } else {
                            selectedShape.move(delta);
                        }
                        dragStart = e.getPoint();
                        repaint();
                    } else {
                        canvas.handleMouseDrag(e);
                    }
                    repaint();
                }
            }

        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        canvas.paintComponent(g2d);

        // Отрисовываем текущую фигуру (предпросмотр)
        if (canvas.currentShape != null) {
            canvas.currentShape.draw(g2d);
        }

        // Отрисовываем временную линию для многоугольника
        if (currentPolygon != null) {
            currentPolygon.draw(g2d);
        }
    }
}


