package nl.devpieter.healthtags.Renderers;

import com.google.gson.annotations.Expose;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import nl.devpieter.healthtags.Config.Setting.Setting;
import nl.devpieter.healthtags.Config.Setting.ToggleSetting;
import nl.devpieter.healthtags.Config.WidgetSetting.EnumWidgetSetting;
import nl.devpieter.healthtags.Config.WidgetSetting.SliderWidgetSetting;
import nl.devpieter.healthtags.Config.WidgetSetting.ToggleWidgetSetting;
import nl.devpieter.healthtags.Enums.HealthTagRenderer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TestSettingTagRenderer implements IHealthTagRenderer {

    @Expose
    public final SliderWidgetSetting TestSlider1 = new SliderWidgetSetting(10, 5, 25, "test slider 1 %s");
    @Expose
    public final SliderWidgetSetting TestSlider2 = new SliderWidgetSetting(10, -20, 20, "test slider 2 %s");
    @Expose
    public final SliderWidgetSetting TestSlider3 = new SliderWidgetSetting(10, -100, 100, "test slider 3 %s");

    @Expose
    public final ToggleSetting TestToggle1 = new ToggleWidgetSetting(true, "test toggle 1");
    @Expose
    public final ToggleSetting TestToggle2 = new ToggleWidgetSetting(false, "test toggle 2");
    @Expose
    public final ToggleSetting TestToggle3 = new ToggleWidgetSetting(false, "test toggle 3");

    @Expose
    public final EnumWidgetSetting<HealthTagRenderer> TestEnum1 = new EnumWidgetSetting<>(HealthTagRenderer.NONE);
    @Expose
    public final EnumWidgetSetting<HealthTagRenderer> TestEnum2 = new EnumWidgetSetting<>(HealthTagRenderer.PERCENTAGE);

    @Override
    public @NotNull List<Setting<?>> getSettings() {
        return List.of(TestSlider1, TestSlider2, TestSlider3, TestToggle1, TestToggle2, TestToggle3, TestEnum1, TestEnum2);
    }

    @Override
    public void renderHealthTag(MatrixStack matrices, PlayerEntity player, boolean hasLabel, float tickDelta, int light) {

    }
}
