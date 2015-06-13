package be.monfils.JExplorer.widgets;

import com.trolltech.qt.core.QPoint;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QCursor;
import com.trolltech.qt.gui.QMouseEvent;
import com.trolltech.qt.gui.QWidget;

/**
 * Created by nathan on 12/06/15.
 */
public class WidgetResizer extends QWidget {
    private QPoint mouse_initial;
    private int width, width_initial;
    private ResizableContainerInterface parent;

    public WidgetResizer(int width, ResizableContainerInterface parent) {
        this.width = width;
        this.width_initial = width;
        this.parent = parent;
        setCursor(new QCursor(Qt.CursorShape.SizeHorCursor));
        setFixedWidth(5);
    }

    @Override
    public void mousePressEvent(QMouseEvent e) {
        mouse_initial = e.globalPos();
        width_initial = width;
    }

    @Override
    public void mouseMoveEvent(QMouseEvent e) {
        if(e.buttons().isSet(Qt.MouseButton.LeftButton) && !mouse_initial.equals(new QPoint(-1, -1))) {
            width = width_initial + (e.globalPos().subtract(mouse_initial)).x();
            if(width < 50)
                width = 50;
            parent.resizeChildren();
        }
    }

    @Override
    public void mouseReleaseEvent(QMouseEvent e) {
        mouse_initial= new QPoint(-1, -1);
        parent.resizeChildren_finished();
    }

    public int getWidth() {
        return width;
    }
}
