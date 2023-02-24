package nl.devpieter.healthtags.Config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import nl.devpieter.healthtags.Config.Setting.Setting;
import nl.devpieter.healthtags.Config.WidgetSetting.SliderSetting;
import nl.devpieter.healthtags.Config.WidgetSetting.ToggleSetting;
import nl.devpieter.healthtags.Enums.HealthTagRenderer;
import nl.devpieter.healthtags.Utils.FileUtils;

import java.io.*;

public class Config {

    /* INSTANCE */
    private static final Config INSTANCE = Config.createInstance();

    public static Config getInstance() {
        return INSTANCE;
    }

    /* === Global settings === */
    @Expose
    public ToggleSetting Enabled = new ToggleSetting(true, "todo");
    @Expose
    public ToggleSetting ShowOnSelf = new ToggleSetting(true, "todo");
    @Expose
    public SliderSetting ExtraHeight = new SliderSetting(2, -20, 60, "config.healthtags.extra_height");
    @Expose
    public Setting<HealthTagRenderer> SelectedRenderer = new Setting<>(HealthTagRenderer.HEART);

    /* === TargetManager settings === */
    @Expose
    public SliderSetting TargetHoldTime = new SliderSetting(5, 1, 60, "config.healthtags.target_hold_time");

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

    private static Config createInstance() {
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
