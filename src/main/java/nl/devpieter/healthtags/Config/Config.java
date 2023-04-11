package nl.devpieter.healthtags.Config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import nl.devpieter.healthtags.Config.WidgetSetting.EnumWidgetSetting;
import nl.devpieter.healthtags.Config.WidgetSetting.SliderWidgetSetting;
import nl.devpieter.healthtags.Config.WidgetSetting.ToggleWidgetSetting;
import nl.devpieter.healthtags.Enums.HealthTagRenderer;
import nl.devpieter.healthtags.Utils.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class Config {

    /* INSTANCE */
    private static final @NotNull Config INSTANCE = Config.createInstance();

    /***
     * Gets the instance of the config.
     * @return The instance of the config.
     */
    public static @NotNull Config getInstance() {
        return INSTANCE;
    }

    /* ========= Global settings ========= */
    @Expose
    public ToggleWidgetSetting Enabled = new ToggleWidgetSetting(true, "healthtags.text.enabled", "healthtags.text.disabled", "healthtags.config.setting.enabled.tooltip");
    @Expose
    public ToggleWidgetSetting ShowOnSelf = new ToggleWidgetSetting(true, "healthtags.config.setting.show_on_self");
    @Expose
    public SliderWidgetSetting ExtraHeight = new SliderWidgetSetting(2, -20, 60, "healthtags.config.setting.extra_height");
    @Expose
    public EnumWidgetSetting<HealthTagRenderer> SelectedRenderer = new EnumWidgetSetting<>(HealthTagRenderer.HEART);

    /* ========= TargetManager settings ========= */
    @Expose
    public SliderWidgetSetting TargetHoldTime = new SliderWidgetSetting(5, 1, 60, "healthtags.config.setting.target_hold_time");

    /***
     * Saves the config to the config file.
     */
    public void save() {
        File configFile = FileUtils.getConfigFile("config");
        FileUtils.createFileIfNotExists(configFile);

        try (Writer writer = new FileWriter(configFile)) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            gson.toJson(this, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * Loads the config from the config file.
     * @return The loaded config.
     */
    private static @NotNull Config createInstance() {
        File configFile = FileUtils.getConfigFile("config");
        if (!configFile.exists()) return new Config();

        try (Reader reader = new FileReader(configFile)) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            Config config = gson.fromJson(reader, Config.class);
            return config == null ? new Config() : config;
        } catch (Exception e) {
            e.printStackTrace();
            return new Config();
        }
    }
}
