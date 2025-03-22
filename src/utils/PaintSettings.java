package utils;

import java.awt.*;

public class PaintSettings {
    private Color color;
    private Color fillColor;
    private int strokeWidth;

    public PaintSettings(Color color, Color fillColor, int stokeWidth){
        this.strokeWidth = stokeWidth;
        this.fillColor = fillColor;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public int getStrokeWidth() {
        return strokeWidth;
    }
    public void setStrokeWidth(int stokeWidth) {
        this.strokeWidth = stokeWidth;
    }
    public void setFillColor(Color color){this.fillColor = color;}
    public Color getFillColor(){return fillColor;}
}
