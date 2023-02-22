package nl.devpieter.healthtags.Utils;

import java.io.File;

public class FileUtils {

    public static String getConfigLocation() {
        return "config/HealthTags/";
    }

    public static File getConfigFile(String name) {
        return new File(getConfigLocation() + name + ".json");
    }

    public static File getRendererConfigFile(String name) {
        return new File(getConfigLocation() + "renderers/" + name + "_renderer.json");
    }

    public static void createFileIfNotExists(File file) {
        if (file.exists()) return;
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
