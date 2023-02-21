package nl.devpieter.healthtags.Config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import nl.devpieter.healthtags.Config.Setting.ClampSetting;
import nl.devpieter.healthtags.Config.Setting.Setting;
import nl.devpieter.healthtags.Enums.HealthTagRenderer;

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
    public Setting<Integer> ExtraHeight = new ClampSetting<>(2, -20, 60);
    @Expose
    public Setting<Boolean> ShowOnSelf = new Setting<>(true);
    @Expose
    public Setting<HealthTagRenderer> SelectedRenderer = new Setting<>(HealthTagRenderer.HEART);

    /* === Settings for HeartTagRenderer === */
    @Expose
    public Setting<Integer> RowsOfHearts = new ClampSetting<>(10, 5, 25);
    @Expose
    public Setting<Integer> HeartsSpacedBy_X = new ClampSetting<>(8, 6, 16);
    @Expose
    public Setting<Integer> HeartsSpacedBy_Y = new ClampSetting<>(10, 6, 16);

    /* === Settings for TargetManager === */
    @Expose
    public Setting<Integer> TargetHoldTime = new ClampSetting<>(5 * 1000, 0, 60 * 1000);

    /* === Settings for Config === */
    private static final String CONFIG_FILE_NAME = "HealthTags.json";

    public void save() {
        File configFile = new File("config", CONFIG_FILE_NAME);

        if (!configFile.exists()) {
            try {
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try (Writer writer = new FileWriter(configFile)) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            gson.toJson(this, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Config createInstance() {
        File configFile = new File("config", CONFIG_FILE_NAME);
        if (!configFile.exists()) return new Config();

        try (Reader reader = new FileReader(configFile)) {
            Config config = new Gson().fromJson(reader, Config.class);
            return config == null ? new Config() : config;
        } catch (Exception e) {
            e.printStackTrace();
            return new Config();
        }
    }
}
