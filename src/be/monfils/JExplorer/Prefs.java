package be.monfils.JExplorer;

import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

/**
 * Created by nathan on 12/06/15.
 */
public class Prefs {
    private static Prefs INSTANCE = new Prefs();

    Wini ini;

    private Prefs() {
        try {
            File f = new File("JExplorer.ini");
            if(!f.exists()) {
                f.createNewFile();
            }
            ini = new Wini(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Prefs getInstance() {
        return INSTANCE;
    }

    public Object getPref(String pref, Class type) {
        return ini.get("JExplorer", pref, type);
    }

    public void setPref(String pref, Object value) {
        ini.put("JExplorer", pref, value);
        try {
            ini.store();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
