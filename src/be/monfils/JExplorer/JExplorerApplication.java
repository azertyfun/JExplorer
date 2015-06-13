package be.monfils.JExplorer;

import com.trolltech.qt.core.QEvent;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QMouseEvent;

/**
 * Created by nathan on 13/06/15.
 */
public class JExplorerApplication extends QApplication {
    public Signal0 backButtonPressed;
    public Signal0 forwardButtonPressed;

    public JExplorerApplication(String[] args) {
        super("JExplorer", args);
        backButtonPressed = new Signal0();
        forwardButtonPressed = new Signal0();
    }

    /*
     * We want to override the back and forward buttons on >= 5 buttons mice.
     */
    @Override
    public boolean notify(QObject object, QEvent e) {
        if(e.type().equals(QEvent.Type.MouseButtonPress) || e.type().equals(QEvent.Type.MouseButtonDblClick)) {
            QMouseEvent event = (QMouseEvent) e;
            if(event.button().equals(Qt.MouseButton.XButton1)) {
                backButtonPressed.emit();
                return false;
            } else if(event.button().equals(Qt.MouseButton.XButton2)) {
                forwardButtonPressed.emit();
                return false;
            }
        }
        return super.notify(object, e);
    }
}
