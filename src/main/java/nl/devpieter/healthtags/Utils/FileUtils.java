package nl.devpieter.healthtags.Utils;

import nl.devpieter.healthtags.Enums.HealthTagRenderer;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class FileUtils {

    /***
     * Gets the location of the config folder.
     * @return The location of the config folder.
     */
    public static @NotNull String getConfigLocation() {
        return "config/HealthTags/";
    }

    /***
     * Gets the config file with the given name.
     * @param name The name of the config file.
     * @return The config file with the given name.
     */
    public static @NotNull File getConfigFile(@NotNull String name) {
        return new File(getConfigLocation() + name + ".json");
    }

    /***
     * Gets the config file for the given renderer.
     * @param renderer The renderer to get the config file for.
     * @return The config file for the given renderer.
     */
    public static @NotNull File getRendererConfigFile(@NotNull HealthTagRenderer renderer) {
        return new File(getConfigLocation() + "renderers/" + renderer.name().toLowerCase() + "_renderer.json");
    }

    /***
     * Creates the given file if it does not exist.
     * @param file The file to create.
     */
    public static void createFileIfNotExists(@NotNull File file) {
        if (file.exists()) return;
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
