import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Figure extends JPanel {
    private JToggleButton line;
    private JToggleButton rectangle;
    private JToggleButton ellipse;
    private JToggleButton polygon;
    private JToggleButton brokenLine;

    private boolean drawLine;
    private boolean drawRectangle;
    private boolean drawEllipse;
    private boolean drawPolygon;
    private boolean drawBrokenLine;


    public Figure(){
        ButtonGroup figures = new ButtonGroup();

        line = new JToggleButton("Line");
        rectangle = new JToggleButton("Rectangle");
        ellipse = new JToggleButton("Ellipse");
        polygon = new JToggleButton("Polygon");
        brokenLine = new JToggleButton("Broken Line");


        figures.add(line);
        figures.add(rectangle);
        figures.add(ellipse);
        figures.add(polygon);
        figures.add(brokenLine);


        line.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (line.isSelected()){
                    drawLine = true;
                    drawRectangle = false;
                    drawEllipse = false;
                    drawPolygon = false;
                    drawBrokenLine = false;
                }
                repaint();
            }
        });

        rectangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (line.isSelected()){
                    drawLine = false;
                    drawRectangle = true;
                    drawEllipse = false;
                    drawPolygon = false;
                    drawBrokenLine = false;
                }
                repaint();
            }
        });

        ellipse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (line.isSelected()){
                    drawLine = false;
                    drawRectangle = false;
                    drawEllipse = true;
                    drawPolygon = false;
                    drawBrokenLine = false;
                }
                repaint();
            }
        });

        polygon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (line.isSelected()){
                    drawLine = false;
                    drawRectangle = false;
                    drawEllipse = false;
                    drawPolygon = true;
                    drawBrokenLine = false;
                }
                repaint();
            }
        });

        brokenLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (line.isSelected()){
                    drawLine = false;
                    drawRectangle = false;
                    drawEllipse = false;
                    drawPolygon = false;
                    drawBrokenLine = true;
                }
                repaint();
            }
        });

        JPanel buttons = new JPanel();
        buttons.add(line);
        buttons.add(rectangle);
        buttons.add(ellipse);
        buttons.add(polygon);
        buttons.add(brokenLine);

        add(buttons, BorderLayout.NORTH);
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.black);
        int numberOfPoints = 5;

        //g.clipRect();
        if (line.isSelected()){
            g.drawLine(100, 200, 300, 300);
        } else if (rectangle.isSelected()) {
            g.fillRect(100, 200, 300, 300);
        } else if (ellipse.isSelected()){
            g.fillOval(100,200,100,100);
        } else if (polygon.isSelected()) {
            int[] x = {100, 200, 250, 150, 50};
            int[] y = {100, 100, 200, 300, 200};
            g.fillPolygon(x, y, numberOfPoints);
        } else if (brokenLine.isSelected()) {
            int[] xPoints = {100, 150, 125, 200, 250};
            int[] yPoints = {200, 250, 300, 350, 400};
            for (int i = 0; i < numberOfPoints - 1; i++) {
                g.drawLine(xPoints[i], yPoints[i], xPoints[i + 1], yPoints[i + 1]);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Figure");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Figure());
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


}
