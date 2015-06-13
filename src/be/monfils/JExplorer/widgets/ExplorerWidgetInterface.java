package be.monfils.JExplorer.widgets;

/**
 * Created by nathan on 12/06/15.
 */
public interface ExplorerWidgetInterface {
    public void changeDirectory(String dir, boolean saveInHistory);
    public String getCurrentDirectory();
}
