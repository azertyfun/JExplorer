package be.monfils.JExplorer.windows;

import be.monfils.JExplorer.JExplorer;
import be.monfils.JExplorer.JExplorerApplication;
import be.monfils.JExplorer.Prefs;
import be.monfils.JExplorer.widgets.*;
import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.gui.*;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by nathan on 12/06/15.
 */
public class JExplorerMainWindow extends QMainWindow implements ResizableContainerInterface, ExplorerWidgetInterface {
    private QVBoxLayout mainLayout;
    private QHBoxLayout listsLayout, controlsLayout;
    private FilesWidget files;
    private QuickAccessWidget quickAccess;
    private QWidget mainWidget;
    private WidgetResizer resizer;

    private QPushButton button_back, button_forward;
    private QLineEdit edit_folder;

    private String currentDirectory;

    private LinkedList<String> backwardHistory = new LinkedList<>();
    private LinkedList<String> forwardHistory = new LinkedList<>();

    public JExplorerMainWindow() {
        super();

        setMinimumSize(800, 600);

        listsLayout = new QHBoxLayout();
        listsLayout.addWidget(quickAccess = new QuickAccessWidget(this));
        listsLayout.addWidget(resizer = new WidgetResizer((Integer) Prefs.getInstance().getPref("quickaccess_width", Integer.class), this));
        listsLayout.addWidget(files = new FilesWidget(this));
        quickAccess.setFixedWidth((Integer) Prefs.getInstance().getPref("quickaccess_width", Integer.class));
        quickAccess.setSizePolicy(QSizePolicy.Policy.Fixed, QSizePolicy.Policy.Ignored);
        files.setSizePolicy(QSizePolicy.Policy.MinimumExpanding, QSizePolicy.Policy.Ignored);

        controlsLayout = new QHBoxLayout();
        controlsLayout.addWidget(button_back = new QPushButton(new QIcon("res/back.png"), ""));
        controlsLayout.addWidget(button_forward = new QPushButton(new QIcon("res/forward.png"), ""));
        controlsLayout.addWidget(edit_folder = new QLineEdit());
        button_back.setFlat(true);
        button_forward.setFlat(true);
        edit_folder.returnPressed.connect(this, "folderOpenedByEdit()");
        button_back.pressed.connect(this, "back()");
        button_forward.pressed.connect(this, "forward()");

        mainLayout = new QVBoxLayout();
        mainLayout.addLayout(controlsLayout);
        mainLayout.addLayout(listsLayout);

        mainWidget = new QWidget();
        mainWidget.setLayout(mainLayout);
        setCentralWidget(mainWidget);

        JExplorer.jExplorerApplication.backButtonPressed.connect(this, "back()");
        JExplorer.jExplorerApplication.forwardButtonPressed.connect(this, "forward()");

        QAction toggle_hiddenFiles = new QAction("Toggle hidden files", this);
        toggle_hiddenFiles.setShortcut(new QKeySequence("Ctrl+H"));
        toggle_hiddenFiles.triggered.connect(this, "toggle_hiddenFiles(Boolean)");
        addAction(toggle_hiddenFiles);

        QAction refresh = new QAction("Refresh folders", this);
        refresh.setShortcut("F5");
        refresh.triggered.connect(this, "refreshFiles(Boolean)");
        addAction(refresh);

        show();

        changeDirectory((String) Prefs.getInstance().getPref("home_directory", String.class), false);
    }

    @Override
    public void changeDirectory(String dir, boolean saveInHistory) {
        if(saveInHistory) {
            backwardHistory.add(currentDirectory);
            button_back.setEnabled(true);
            forwardHistory.clear();
            button_forward.setEnabled(false);
        }

        this.currentDirectory = dir;
        setWindowTitle("JExplorer: " + dir);
        edit_folder.setText(dir);
        files.load(dir);
    }

    public void back() {
        if(backwardHistory.size() > 0) {
            forwardHistory.add(currentDirectory);
            button_forward.setEnabled(true);
            changeDirectory(backwardHistory.getLast(), false);
            backwardHistory.removeLast();
        }

        if(backwardHistory.size() == 0)
            button_back.setEnabled(false);
    }

    public void forward() {
        if(forwardHistory.size() > 0) {
            backwardHistory.add(currentDirectory);
            button_back.setEnabled(true);

            changeDirectory(forwardHistory.getLast(), false);
            forwardHistory.removeLast();
        }

        if(forwardHistory.size() == 0)
            button_forward.setEnabled(false);
    }

    public void folderOpenedByEdit() {
        if(!edit_folder.text().equals("")) {
            File folder = new File(edit_folder.text());
            if (folder.isDirectory()) {
                changeDirectory(edit_folder.text(), true);
            } else {
                edit_folder.setStyleSheet("* {background-color: #D50000;}");
                QTimer.singleShot(500, this, "resetFolderEditStyle()");
            }
        } else {
            edit_folder.setText(currentDirectory);
        }
    }

    public void toggle_hiddenFiles(Boolean b) {
        Boolean hidden_files = (Boolean) Prefs.getInstance().getPref("show_hiddenFiles", Boolean.class);
        Prefs.getInstance().setPref("show_hiddenFiles", !hidden_files);
        changeDirectory(currentDirectory, false);
    }

    public void refreshFiles(Boolean b) {
        changeDirectory(currentDirectory, false);
    }

    public void resetFolderEditStyle() {
        edit_folder.setStyleSheet("");
    }

    @Override
    public void resizeChildren() {
        quickAccess.setFixedWidth(resizer.getWidth());
    }

    @Override
    public void resizeChildren_finished() {
        quickAccess.setFixedWidth(resizer.getWidth());
        Prefs.getInstance().setPref("quickaccess_width", resizer.getWidth());
    }

    @Override
    public String getCurrentDirectory() {
        return currentDirectory;
    }
}
