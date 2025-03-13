package utils;

import java.awt.*;

public class PaintSettings {
    private Color color;
    private int strokeWidth;

    public PaintSettings(Color color, int stokeWidth){
        this.strokeWidth = stokeWidth;
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
}
