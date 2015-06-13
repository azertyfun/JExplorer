package be.monfils.JExplorer.widgets;

import be.monfils.JExplorer.widgets.ExplorerWidgetInterface;
import be.monfils.JExplorer.windows.JExplorerMainWindow;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QListWidgetItem;

/**
 * Created by nathan on 12/06/15.
 */
public class QuickAccessWidgetItem extends QListWidgetItem {
    private String label, path;
    private ExplorerWidgetInterface parent;
    boolean dummy = false;

    public QuickAccessWidgetItem(String label, String path, ExplorerWidgetInterface parent) {
        super();

        this.parent = parent;

        this.label = label;
        this.path = path;
        setText(label);
    }

    public QuickAccessWidgetItem(String label) {
        dummy = true;
        this.label = label;
        setText(label);
        path = "";
    }

    public void clicked() {
        if(!dummy)
            parent.changeDirectory(path, true);
    }

    public String getLabel() {
        return label;
    }

    public String getPath() {
        return path;
    }
}
