package be.monfils.JExplorer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 * Created by nathan on 13/06/15.
 */
public class MountPoint {
    private static final String[] blacklisted_mountPoints = new String[] {"/dev", "/run", "/dev/shm", "/sys/fs/cgroup", "/run/user/1000", "-"};

    private String label, mountPoint;
    private int size, use;

    public MountPoint(String mountPoint, String label, int size, int use) {
        this.mountPoint = mountPoint;
        this.label = label;
        this.size = size;
        this.use = use;
    }

    public String getLabel() {
        return label;
    }

    public String getMountPoint() {
        return mountPoint;
    }

    public int getSize() {
        return size;
    }

    public int getUse() {
        return use;
    }

    public static LinkedList<MountPoint> getMountPoints() {
        LinkedList<MountPoint> mountPoints = new LinkedList<>();
        ProcessBuilder pb = new ProcessBuilder("df");
        pb.redirectErrorStream(true);
        try {
            Process df = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(df.getInputStream()));
            LinkedList<String> lines = new LinkedList<>();
            String line;
            while((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();

            for(int i = 1; i < lines.size(); ++i) {
                String line_splitted_raw[] = lines.get(i).split(" ");
                LinkedList<String> line_splitted = new LinkedList<>();
                for(String split : line_splitted_raw) {
                    if(!split.equals(""))
                        line_splitted.add(split);
                }
                if(line_splitted.getFirst() != "" && line_splitted.getFirst() != "dev" && line_splitted.getFirst() != "run") {
                    boolean blacklisted = false;
                    for(String s : blacklisted_mountPoints) {
                        if (line_splitted.getLast().equals(s)) {
                            blacklisted = true;
                            break;
                        }
                    }
                    if(!blacklisted)
                        mountPoints.add(new MountPoint(line_splitted.getLast(), line_splitted.getFirst(), Integer.parseInt(line_splitted.get(1)), Integer.parseInt(line_splitted.get(2))));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mountPoints;
    }
}
