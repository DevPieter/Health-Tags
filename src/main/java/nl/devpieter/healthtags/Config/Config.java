package nl.devpieter.healthtags.Config;

import nl.devpieter.healthtags.Config.Setting.ClampSetting;
import nl.devpieter.healthtags.Config.Setting.Setting;
import nl.devpieter.healthtags.Enums.HealthTagRenderer;
import org.apache.commons.lang3.NotImplementedException;

public class Config {

    /* INSTANCE */
    private static final Config INSTANCE = new Config();

    public static Config getInstance() {
        return INSTANCE;
    }

    /* === Global settings === */
    public Setting<Integer> ExtraHeight = new ClampSetting<>(2, -20, 60);
    public Setting<Boolean> ShowOnSelf = new Setting<>(true);
    public Setting<HealthTagRenderer> SelectedRenderer = new Setting<>(HealthTagRenderer.HEART);

    /* === Settings for HeartTagRenderer === */
    public Setting<Integer> RowsOfHearts = new ClampSetting<>(10, 5, 25);
    public Setting<Integer> HeartsSpacedBy_X = new ClampSetting<>(8, 6, 16);
    public Setting<Integer> HeartsSpacedBy_Y = new ClampSetting<>(10, 6, 16);

    /* === Settings for TargetManager === */
    public Setting<Integer> TargetHoldTime = new ClampSetting<>(5 * 1000, 0, 60 * 1000);

    public Config() {
//        this.load();
    }

    public void save() {
        throw new NotImplementedException("Not implemented yet!");
    }

    public void load() {
        throw new NotImplementedException("Not implemented yet!");
    }
}
