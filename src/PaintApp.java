
import Commands.HistoryManager;
import Files.UserFile;
import Shapes.Polygon;
import Shapes.Rectangle;
import Shapes.*;
import Shapes.Shape;
import utils.Canvas;
import utils.DrawTool;
import utils.PaintSettings;
import utils.Tool;
import Files.UserFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaintApp {
    private Canvas canvas;
    private PaintSettings settings;
    private JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaintApp().createAndShowGUI());
    }

    private void createAndShowGUI(){
        frame = new JFrame("Paint");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200,700);

        settings = new PaintSettings(Color.BLACK, Color.WHITE, 2);
        canvas = new Canvas();

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem loadItem = new JMenuItem("Load");
        saveItem.addActionListener(this::saveFile);
        loadItem.addActionListener(this::loadFile);

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        menuBar.add(fileMenu);

        JToolBar toolBar = new JToolBar();

        List<Point> points = new ArrayList<>();
        List<Point> polygonPoints = new ArrayList<>();

        JButton lineButton = createToolButton("Line", new DrawTool(new Line(settings, new Point(100,200), new Point(20, 30)),settings));
        JButton rectButton = createToolButton("Rectangle", new DrawTool(new Rectangle(settings, new Point(100,200), 80, 60),settings));
        JButton circleButton = createToolButton("Circle", new DrawTool(new Circle(settings, new Point(100, 100), 20),settings));
        JButton brokenLineButton = createToolButton("Broken Line", new DrawTool(new BrokenLine(settings, new Point(100,200), points),settings));
        JButton polygonButton = createToolButton("Polygon", new DrawTool(new Polygon(settings, new Point(100,200), polygonPoints),settings));

        JButton colorButton = new JButton("Color");

        lineButton.setBackground(Color.orange);
        rectButton.setBackground(Color.orange);
        circleButton.setBackground(Color.orange);
        brokenLineButton.setBackground(Color.orange);
        polygonButton.setBackground(Color.orange);
        colorButton.setBackground(Color.orange);

        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(frame, "Choose Color", settings.getColor());
            if(newColor != null) {
                settings.setColor(newColor);
            }
        });

        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> {
            canvas.undo();
           // updateButtonStates();
        });
        JButton redoButton = new JButton("Redo");
        redoButton.addActionListener(e -> {
            canvas.redo();
           // updateButtonStates();
        });

        undoButton.setBackground(Color.orange);
        redoButton.setBackground(Color.orange);

        JSpinner spinner = new JSpinner(new SpinnerNumberModel(2, 1, 10, 1));
        spinner.addChangeListener(e -> settings.setStrokeWidth((Integer)spinner.getValue()));
        spinner.setBackground(Color.orange);

        toolBar.add(menuBar);
        toolBar.add(lineButton);
        toolBar.add(rectButton);
        toolBar.add(circleButton);
        toolBar.add(brokenLineButton);
        toolBar.add(polygonButton);
        toolBar.addSeparator();
        toolBar.add(colorButton);
        toolBar.add(undoButton);
        toolBar.add(redoButton);
        toolBar.add(new JLabel("Thickness"));
        toolBar.add(spinner);

        frame.add(toolBar, BorderLayout.NORTH);
        frame.add(new DrawingPanel(), BorderLayout.CENTER);

        frame.setVisible(true);

    }

    /*
    private void updateButtonStates() {
        undoButton.setEnabled(canvas.canUndo());
        redoButton.setEnabled(canvas.canRedo());
    }

     */

    private JButton createToolButton(String text, Tool tool) {
        JButton button = new JButton(text);
        button.addActionListener(e -> canvas.setTool(tool));
        return button;
    }

    private void saveFile(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            UserFile userFile = new UserFile();
            userFile.saveToFile(fileChooser.getSelectedFile().getPath(), canvas.shapes);
        }
    }

    private void loadFile(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            canvas.clear();
            UserFile userFile = new UserFile();
            canvas.shapes = userFile.loadFromFile(fileChooser.getSelectedFile().getPath());
            frame.repaint();
        }
    }


    class DrawingPanel extends JPanel {
        private Shape selectedShape;
        private Point selectedHandle;
        private Point dragStart;

        public DrawingPanel() {
            setDoubleBuffered(true);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        if (selectedShape != null) {
                            selectedShape.setSelected(false);
                        }
                        for (int i = canvas.shapes.size()-1; i >= 0; i--) {
                            Shape shape = canvas.shapes.get(i);
                            if (shape.isHit(e.getPoint())) {
                                selectedShape = shape;
                                selectedShape.setSelected(true);
                                dragStart = e.getPoint();
                                selectedHandle = null;

                                // Проверка на попадание в маркеры
                                for (Point handle : selectedShape.getHandles()) {
                                    if (Math.abs(handle.x - e.getX()) < 5
                                            && Math.abs(handle.y - e.getY()) < 5) {
                                        selectedHandle = handle;
                                        break;
                                    }
                                }
                                repaint();
                                return;
                            }
                        }
                        // Если не выбрана линия - обрабатываем новое рисование
                        if (selectedShape == null) {
                            canvas.handleMousePress(e);
                        }

                        repaint();  // Единый вызов перерисовки
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        canvas.handleMouseRelease(e);
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
                        }
                        else {
                            canvas.handleMouseDrag(e);
                        }
                        repaint();
                    }
                }

            });

        }

        private boolean isNear(Point a, Point b) {
            return Math.abs(a.x - b.x) < 5 &&
                    Math.abs(a.y - b.y) < 5;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Очистка фона
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // Отрисовка фигур
            canvas.paintComponent(g2d);
        }
    }
}
