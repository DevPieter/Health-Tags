package nl.devpieter.healthtags.Enums;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.text.Text;
import nl.devpieter.healthtags.Renderers.*;
import nl.devpieter.healthtags.Utils.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.*;
import java.util.Arrays;

public enum HealthTagRenderer implements IWidgetableEnum {

    NONE("healthtags.renderer.none", null),
    HEART("healthtags.renderer.heart", HeartTagRenderer.class),
    PERCENTAGE("healthtags.renderer.percentage", PercentageTagRenderer.class);
//    TEST("test", TestSettingTagRenderer.class);

    private final @NotNull String translationKey;
    private final @Nullable Class<? extends IHealthTagRenderer> rendererClass;
    private final @Nullable IHealthTagRenderer renderer;

    HealthTagRenderer(@NotNull String translationKey, @Nullable Class<? extends IHealthTagRenderer> rendererClass) {
        this.translationKey = translationKey;
        this.rendererClass = rendererClass;
        this.renderer = this.createNewRenderer();
        this.saveSettings();
    }

    /***
     * Creates a new instance of the renderer.
     * @return A new instance of the renderer, or null if it failed to create.
     */
    private @Nullable IHealthTagRenderer createNewRenderer() {
        if (this.rendererClass == null) return null;

        if (this.hasSavableSettings()) {
            IHealthTagRenderer renderer = this.createNewRendererFromConfig();
            if (renderer != null) return renderer;
        }

        try {
            return this.rendererClass.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * Creates a new instance of the renderer from its config file.
     * @return A new instance of the renderer. If the config file doesn't exist, null will be returned.
     */
    private @Nullable IHealthTagRenderer createNewRendererFromConfig() {
        if (this.rendererClass == null) return null;

        File configFile = FileUtils.getRendererConfigFile(this);
        if (!configFile.exists()) return null;

        try (Reader reader = new FileReader(configFile)) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            IHealthTagRenderer newRenderer = gson.fromJson(reader, this.rendererClass);

            return newRenderer == null ? this.rendererClass.getConstructor().newInstance() : newRenderer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * Saves the settings of the renderer to its config file.
     */
    public void saveSettings() {
        if (this.renderer == null || !this.hasSavableSettings()) return;

        File configFile = FileUtils.getRendererConfigFile(this);
        FileUtils.createFileIfNotExists(configFile);

        Logger logger = LogUtils.getLogger();

        try (Writer writer = new FileWriter(configFile)) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            gson.toJson(this.renderer, writer);

            logger.info("Saved the config for '{}' to '{}'", this.name(), configFile.getAbsolutePath());
        } catch (Exception e) {
            logger.error("Failed to save the config for '{}' to '{}'", this.name(), configFile.getAbsolutePath());
            e.printStackTrace();
        }
    }

    /***
     * Gets the name of the renderer.
     * @return The name of the renderer.
     */
    @Override
    public @NotNull Text getName() {
        return Text.translatable(this.translationKey);
    }

    /***
     * Gets the tooltip of the renderer.
     * @return The tooltip of the renderer.
     */
    @Override
    public @NotNull Tooltip getTooltip() {
        return Tooltip.of(Text.translatable(this.translationKey + ".tooltip"));
    }

    /***
     * Gets the renderer instance.
     * @return The renderer instance.
     */
    public @Nullable IHealthTagRenderer getRenderer() {
        return this.renderer;
    }

    /***
     * Checks if the renderer has savable settings.
     * @return True if the renderer has savable settings, false otherwise.
     */
    public boolean hasSavableSettings() {
        if (this.rendererClass == null) return false;
        return Arrays.stream(this.rendererClass.getFields()).anyMatch(field -> field.isAnnotationPresent(Expose.class));
    }

    /***
     * Saves the settings of all renderers to their config files.
     */
    public static void saveAllSettings() {
        Logger logger = LogUtils.getLogger();
        logger.info("Saving all the settings of the renderers...");
        for (HealthTagRenderer renderer : HealthTagRenderer.values()) {
            if (renderer.hasSavableSettings()) renderer.saveSettings();
        }
    }
}
