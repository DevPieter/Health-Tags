package nl.devpieter.healthtags.Enums;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.text.Text;
import nl.devpieter.healthtags.Renderers.HeartTagRenderer;
import nl.devpieter.healthtags.Renderers.IHealthTagRenderer;
import nl.devpieter.healthtags.Renderers.PercentageTagRenderer;
import nl.devpieter.healthtags.Utils.FileUtils;
import org.jetbrains.annotations.Nullable;

import java.io.*;

public enum HealthTagRenderer {

    NONE("healthtags.renderer.none", null),
    HEART("healthtags.renderer.heart", HeartTagRenderer.class),
    PERCENTAGE("healthtags.renderer.percentage", PercentageTagRenderer.class);

    private final String translationKey, tooltipTranslationKey;
    @Nullable
    private final IHealthTagRenderer renderer;

    HealthTagRenderer(String translationKey, @Nullable Class<? extends IHealthTagRenderer> rendererClass) {
        this.translationKey = translationKey;
        this.tooltipTranslationKey = translationKey + ".tooltip";
        this.renderer = this.createNewRenderer(rendererClass);
        this.saveSettings();
    }

    private IHealthTagRenderer createNewRenderer(Class<? extends IHealthTagRenderer> rendererClass) {
        if (rendererClass == null) return null;

        File configFile = FileUtils.getRendererConfigFile(this.name().toLowerCase());
        FileUtils.createFileIfNotExists(configFile);

        try (Reader reader = new FileReader(configFile)) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            IHealthTagRenderer newRenderer = gson.fromJson(reader, rendererClass);

            return newRenderer == null ? rendererClass.getConstructor().newInstance() : newRenderer;
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            return rendererClass.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveSettings() {
        if (this.renderer == null) return;

        File configFile = FileUtils.getRendererConfigFile(this.name().toLowerCase());
        FileUtils.createFileIfNotExists(configFile);

        try (Writer writer = new FileWriter(configFile)) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            gson.toJson(this.renderer, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* === Getters === */
    public Text getName() {
        return Text.translatable(this.translationKey);
    }

    public Tooltip getTooltip() {
        return Tooltip.of(Text.translatable(this.tooltipTranslationKey));
    }

    @Nullable
    public IHealthTagRenderer getRenderer() {
        return this.renderer;
    }

    /* === Static methods === */
    public static void saveAllSettings() {
        for (HealthTagRenderer renderer : HealthTagRenderer.values()) renderer.saveSettings();
    }
}
