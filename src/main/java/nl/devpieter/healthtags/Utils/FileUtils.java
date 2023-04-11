package nl.devpieter.healthtags.Utils;

import com.mojang.logging.LogUtils;
import nl.devpieter.healthtags.Enums.HealthTagRenderer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.File;

public class FileUtils {

    private static final Logger LOGGER = LogUtils.getLogger();

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
            boolean createdFolder = file.getParentFile().mkdirs();
            boolean createdFile = file.createNewFile();

            if (createdFolder) LOGGER.info("Created folder at: {}", file.getParentFile().getAbsolutePath());
            if (createdFile) LOGGER.info("Created file at: {}", file.getAbsolutePath());
        } catch (Exception e) {
            LOGGER.error("Failed to create file at: {}", file.getAbsolutePath());
            e.printStackTrace();
        }
    }
}
