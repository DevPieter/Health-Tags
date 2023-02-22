package nl.devpieter.healthtags.Config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import nl.devpieter.healthtags.Config.Setting.ClampSetting;
import nl.devpieter.healthtags.Config.Setting.Setting;
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
    public Setting<Boolean> Enabled = new Setting<>(true);
    @Expose
    public Setting<Boolean> ShowOnSelf = new Setting<>(true);
    @Expose
    public ClampSetting<Integer> ExtraHeight = new ClampSetting<>(2, -20, 60);
    @Expose
    public Setting<HealthTagRenderer> SelectedRenderer = new Setting<>(HealthTagRenderer.HEART);

    /* === Settings for TargetManager === */
    @Expose
    public ClampSetting<Integer> TargetHoldTime = new ClampSetting<>(5 * 1000, 0, 60 * 1000);

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
