package be.monfils.JExplorer;

import be.monfils.JExplorer.windows.JExplorerMainWindow;

import java.util.LinkedList;

/**
 * Created by nathan on 12/06/15.
 */
public class JExplorer {
    public static JExplorerApplication jExplorerApplication;

    public static void main(String[] args) {
        /*
         * Check config
         */
        Prefs prefs = Prefs.getInstance();
        String home_directory = (String) prefs.getPref("home_directory", String.class);
        if(home_directory == null || home_directory.equals("")) {
            prefs.setPref("home_directory", System.getProperty("user.home"));
        }
        Integer quickaccess_width = (Integer) prefs.getPref("quickaccess_width", Integer.class);
        if(quickaccess_width == null || quickaccess_width < 50) {
            prefs.setPref("quickaccess_width", 100);
        }
        Boolean show_hiddenFiles = (Boolean) prefs.getPref("show_hiddenFiles", Boolean.class);
        if(show_hiddenFiles == null) {
            prefs.setPref("show_hiddenFiles", false);
        }

        jExplorerApplication = new JExplorerApplication(args);

        JExplorerMainWindow mainWindow = new JExplorerMainWindow();

        jExplorerApplication.exec();
    }
}
