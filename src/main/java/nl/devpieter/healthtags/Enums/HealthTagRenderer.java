package nl.devpieter.healthtags.Enums;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.text.Text;
import nl.devpieter.healthtags.Renderers.*;
import nl.devpieter.healthtags.Utils.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.*;

public enum HealthTagRenderer implements IWidgetableEnum {

    NONE("healthtags.renderer.none", null),
    HEART("healthtags.renderer.heart", HeartTagRenderer.class),
    PERCENTAGE("healthtags.renderer.percentage", PercentageTagRenderer.class),
    TEST("test", TestSettingTagRenderer.class);

    private final @NotNull String translationKey;
    private final @Nullable IHealthTagRenderer renderer;

    HealthTagRenderer(@NotNull String translationKey, @Nullable Class<? extends IHealthTagRenderer> rendererClass) {
        this.translationKey = translationKey;
        this.renderer = this.createNewRenderer(rendererClass);
        this.saveSettings();
    }

    /***
     * Creates a new instance of the given renderer class and loads the settings from the config file.
     * If the config file does not exist, a new instance is created and saved to the config file.
     * @param rendererClass The class of the renderer to create.
     * @return The new renderer instance.
     */
    private @Nullable IHealthTagRenderer createNewRenderer(@Nullable Class<? extends IHealthTagRenderer> rendererClass) {
        if (rendererClass == null) return null;

        File configFile = FileUtils.getRendererConfigFile(this);
        FileUtils.createFileIfNotExists(configFile);

        Logger logger = LogUtils.getLogger();

        try (Reader reader = new FileReader(configFile)) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            IHealthTagRenderer newRenderer = gson.fromJson(reader, rendererClass);

            if (newRenderer == null) logger.warn("Failed to load the config for '{}', trying to create a new instance.", this.name());
            else logger.info("Loaded the config for '{}'", this.name());

            return newRenderer == null ? rendererClass.getConstructor().newInstance() : newRenderer;
        } catch (Exception e) {
            logger.error("Failed to load the config for '{}', trying to create a new instance.", this.name());
            e.printStackTrace();
        }

        try {
            logger.info("Trying to create a new instance of '{}'", this.name());
            return rendererClass.getConstructor().newInstance();
        } catch (Exception e) {
            logger.error("Failed to create a new instance of '{}'", this.name());
            e.printStackTrace();
            return null;
        }
    }

    /***
     * Saves the settings of the renderer to the config file.
     */
    public void saveSettings() {
        if (this.renderer == null) return;

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
     * Saves the settings of all renderers to their config files.
     */
    public static void saveAllSettings() {
        Logger logger = LogUtils.getLogger();
        logger.info("Saving all the settings of the renderers...");
        for (HealthTagRenderer renderer : HealthTagRenderer.values()) renderer.saveSettings();
    }
}
