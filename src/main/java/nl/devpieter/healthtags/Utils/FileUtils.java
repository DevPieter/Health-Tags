package nl.devpieter.healthtags.Utils;

import nl.devpieter.healthtags.Enums.HealthTagRenderer;

import java.io.File;

public class FileUtils {

    /***
     * Gets the location of the config folder.
     * @return The location of the config folder.
     */
    public static String getConfigLocation() {
        return "config/HealthTags/";
    }

    /***
     * Gets the config file with the given name.
     * @param name The name of the config file.
     * @return The config file with the given name.
     */
    public static File getConfigFile(String name) {
        return new File(getConfigLocation() + name + ".json");
    }

    /***
     * Gets the config file for the given renderer.
     * @param renderer The renderer to get the config file for.
     * @return The config file for the given renderer.
     */
    public static File getRendererConfigFile(HealthTagRenderer renderer) {
        return new File(getConfigLocation() + "renderers/" + renderer.name().toLowerCase() + "_renderer.json");
    }

    /***
     * Creates the given file if it does not exist.
     * @param file The file to create.
     */
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
