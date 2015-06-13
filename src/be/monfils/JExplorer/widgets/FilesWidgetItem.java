package be.monfils.JExplorer.widgets;

import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QListWidgetItem;

import java.io.File;

/**
 * Created by nathan on 12/06/15.
 */
public class FilesWidgetItem extends QListWidgetItem {
    private ExplorerWidgetInterface parent;
    private File file;

    public FilesWidgetItem(File file, ExplorerWidgetInterface parent) {
        super();

        this.file = file;
        this.parent = parent;
        setText(file.getName());
        if(file.isDirectory()) {
            setIcon(new QIcon("res/folder.png"));
        } else {
            setIcon(new QIcon("res/genericFile.png"));
        }
    }

    public FilesWidgetItem(File file, String forcedDisplayName, ExplorerWidgetInterface parent) {
        super();

        this.file = file;
        this.parent = parent;
        setText(forcedDisplayName);
        if(file.isDirectory()) {
            setIcon(new QIcon("res/folder.png"));
        } else {
            setIcon(new QIcon("res/genericFile.png"));
        }
    }

    public File getFile() {
        return file;
    }
}
