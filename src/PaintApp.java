
import Commands.HistoryManager;
import Files.UserFile;
import Shapes.Polygon;
import Shapes.Rectangle;
import Shapes.*;
import Shapes.Shape;
import utils.*;
import Files.UserFile;
import utils.Canvas;

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
    private JButton undoButton;
    private JButton redoButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaintApp().createAndShowGUI());
    }

    private void createAndShowGUI(){
        frame = new JFrame("Paint");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200,700);

        settings = new PaintSettings(Color.BLACK, null, 2);
        canvas = new Canvas();
        canvas.currentSettings = new PaintSettings(settings);

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
        JButton fillColorBtn = new JButton("Fill color");

        lineButton.setBackground(Color.orange);
        rectButton.setBackground(Color.orange);
        circleButton.setBackground(Color.orange);
        brokenLineButton.setBackground(Color.orange);
        polygonButton.setBackground(Color.orange);
        colorButton.setBackground(Color.pink);
        fillColorBtn.setBackground(Color.pink);

        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(frame, "Choose color", settings.getColor());
            if(newColor != null) {
                settings.setColor(newColor);
                canvas.currentSettings.setColor(newColor);
            }
        });

        fillColorBtn.addActionListener(e -> {
            Color color = JColorChooser.showDialog(null, "Choose fill color", settings.getFillColor());
            if(color != null) {
                settings.setFillColor(color);
                canvas.currentSettings.setFillColor(color);
            }
        });

        undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> {
            canvas.undo();
            updateUndoRedoButtons();
            frame.repaint();
        });
        redoButton = new JButton("Redo");
        redoButton.addActionListener(e -> {
            canvas.redo();
            updateUndoRedoButtons();
            frame.repaint();
        });

        undoButton.setBackground(Color.orange);
        redoButton.setBackground(Color.orange);

        JSpinner spinner = new JSpinner(new SpinnerNumberModel(2, 1, 10, 1));
        spinner.addChangeListener(e -> {
            int width = (Integer) spinner.getValue();
            settings.setStrokeWidth(width);
            canvas.currentSettings.setStrokeWidth(width);
        });
        spinner.setBackground(Color.orange);

        toolBar.add(menuBar);
        toolBar.add(lineButton);
        toolBar.add(rectButton);
        toolBar.add(circleButton);
        toolBar.add(brokenLineButton);
        toolBar.add(polygonButton);
        toolBar.addSeparator();
        toolBar.add(colorButton);
        toolBar.add(fillColorBtn);
        toolBar.add(undoButton);
        toolBar.add(redoButton);
        toolBar.add(new JLabel("Thickness"));
        toolBar.add(spinner);

        frame.add(toolBar, BorderLayout.NORTH);
        frame.add(new DrawingPanel(canvas), BorderLayout.CENTER);

        frame.setVisible(true);

    }

    private void updateUndoRedoButtons() {
        undoButton.setEnabled(canvas.canUndo());
        redoButton.setEnabled(canvas.canRedo());
    }

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



}
