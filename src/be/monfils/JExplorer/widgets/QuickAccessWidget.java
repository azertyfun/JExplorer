package be.monfils.JExplorer.widgets;

import be.monfils.JExplorer.MountPoint;
import be.monfils.JExplorer.Prefs;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QListWidgetItem;

import java.util.LinkedList;

/**
 * Created by nathan on 12/06/15.
 */
public class QuickAccessWidget extends QListWidget {
    private ExplorerWidgetInterface parent;

    public QuickAccessWidget(ExplorerWidgetInterface parent) {
        super();

        this.parent = parent;

        String home_directory = (String) Prefs.getInstance().getPref("home_directory", String.class);

        addItem(new QuickAccessWidgetItem("Home", home_directory, parent));
        addItem(new QuickAccessWidgetItem("Documents", home_directory + "/Documents", parent));
        addItem(new QuickAccessWidgetItem("Music", home_directory + "/Music", parent));
        addItem(new QuickAccessWidgetItem("Pictures", home_directory + "/Pictures", parent));
        addItem(new QuickAccessWidgetItem("Videos", home_directory + "/Videos", parent));
        addItem(new QuickAccessWidgetItem("----------"));
        LinkedList<MountPoint> mountPoints = MountPoint.getMountPoints();
        for(MountPoint mp : mountPoints) {
            addItem(new QuickAccessWidgetItem(mp.getMountPoint(), mp.getMountPoint(), parent));
        }

        this.itemSelectionChanged.connect(this, "itemSelectionChanged()");
    }

    public void itemSelectionChanged() {
        ((QuickAccessWidgetItem) item(currentRow())).clicked();
    }
}
