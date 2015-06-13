package be.monfils.JExplorer.widgets;

import be.monfils.JExplorer.Prefs;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QListWidgetItem;

import java.io.*;

/**
 * Created by nathan on 12/06/15.
 */
public class FilesWidget extends QListWidget {
    ExplorerWidgetInterface parent;
    public FilesWidget(ExplorerWidgetInterface parent) {
        this.parent = parent;

        this.itemDoubleClicked.connect(this, "itemDoubleClicked(QListWidgetItem)");
    }

    public void load(String directory) {
        clear();
        File f = new File(directory);
        if(!f.isDirectory()) {
            System.err.println("Attempted to load " + f.getPath() + ", which isn't a directory.");
            setStyleSheet("* {background-color: #D50000; background-image: url('res/directoryError.png'); background-repeat: no-repeat; background-position: center; background-attachment: fixed; } ");
            return;
        }
        setStyleSheet(""); //In case the previous directory wasn't one
        if(!f.getPath().equals("/"))
            addItem(new FilesWidgetItem(f.getParentFile(), "..", parent));
        File files[] = f.listFiles();
        for(File currentFile : files) {
            if((Boolean) Prefs.getInstance().getPref("show_hiddenFiles", Boolean.class) || currentFile.getName().charAt(0) != '.')
                addItem(new FilesWidgetItem(currentFile, parent));
        }
    }

    public void itemDoubleClicked(QListWidgetItem item) {
        File file = ((FilesWidgetItem) item).getFile();
        if(file.isDirectory()) {
            parent.changeDirectory(file.getAbsolutePath(), true);
        } else {
            try {
                ProcessBuilder xdg_open_builder = new ProcessBuilder("xdg-open", file.getAbsolutePath());
                xdg_open_builder.redirectErrorStream(true).redirectOutput(ProcessBuilder.Redirect.INHERIT);
                Process xdg_open = xdg_open_builder.start();
                try {
                    xdg_open.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
