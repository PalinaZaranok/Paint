package Utils;

import java.awt.event.MouseEvent;

public interface Tool {
    void handleMousePress(Canvas canvas, MouseEvent e);

    void handleMouseDrag(Canvas canvas, MouseEvent e);

    void handleMouseRelease(Canvas canvas, MouseEvent e);
}
