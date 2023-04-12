package nl.devpieter.healthtags.Renderers;

import com.google.gson.annotations.Expose;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import nl.devpieter.healthtags.Config.Setting.Setting;
import nl.devpieter.healthtags.Config.Setting.ToggleSetting;
import nl.devpieter.healthtags.Config.WidgetSetting.EnumWidgetSetting;
import nl.devpieter.healthtags.Config.WidgetSetting.SliderWidgetSetting;
import nl.devpieter.healthtags.Config.WidgetSetting.ToggleWidgetSetting;
import nl.devpieter.healthtags.Enums.HealthTagRenderer;
import nl.devpieter.healthtags.Enums.IWidgetableEnum;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TestSettingTagRenderer implements IHealthTagRenderer {

    @Expose
    public final SliderWidgetSetting TestSlider1 = new SliderWidgetSetting(10, 5, 25, "test slider 1 %s");
    @Expose
    public final SliderWidgetSetting TestSlider2 = new SliderWidgetSetting(10, -20, 20, "test slider 2 %s");
    @Expose
    public final SliderWidgetSetting TestSlider3 = new SliderWidgetSetting(10, -100, 100, "test slider 3 %s");
    @Expose
    public final SliderWidgetSetting TestSlider4 = new SliderWidgetSetting(10, -100, 100, "test slider 4 %s");
    @Expose
    public final SliderWidgetSetting TestSlider5 = new SliderWidgetSetting(10, -100, 100, "test slider 5 %s");

    @Expose
    public final ToggleWidgetSetting TestToggle1 = new ToggleWidgetSetting(true, "test toggle 1");
    @Expose
    public final ToggleWidgetSetting TestToggle2 = new ToggleWidgetSetting(false, "test toggle 2");
    @Expose
    public final ToggleWidgetSetting TestToggle3 = new ToggleWidgetSetting(false, "test toggle 3");
    @Expose
    public final ToggleWidgetSetting TestToggle4 = new ToggleWidgetSetting(false, "test toggle 4");
    @Expose
    public final ToggleWidgetSetting TestToggle5 = new ToggleWidgetSetting(false, "test toggle 5");

    @Expose
    public final EnumWidgetSetting<MyTestEnum2> TestEnum1 = new EnumWidgetSetting<>(MyTestEnum2.Dit);
    @Expose
    public final EnumWidgetSetting<MyTestEnum2> TestEnum2 = new EnumWidgetSetting<>(MyTestEnum2.Is);
    @Expose
    public final EnumWidgetSetting<MyTestEnum1> TestEnum3 = new EnumWidgetSetting<>(MyTestEnum1.Pro);
    @Expose
    public final EnumWidgetSetting<MyTestEnum1> TestEnum4 = new EnumWidgetSetting<>(MyTestEnum1.Gamer);
    @Expose
    public final EnumWidgetSetting<MyTestEnum2> TestEnum5 = new EnumWidgetSetting<>(MyTestEnum2.Test);

    @Override
    public @NotNull List<Setting<?>> getSettings() {
        List<Setting<?>> settings = new ArrayList<>();

        settings.addAll(List.of(TestSlider1, TestSlider2, TestSlider3, TestSlider4, TestSlider5));
        settings.addAll(List.of(TestToggle1, TestToggle2, TestToggle3, TestToggle4, TestToggle5));
        settings.addAll(List.of(TestEnum1, TestEnum2, TestEnum3, TestEnum4, TestEnum5));

        return settings;
    }

    @Override
    public void renderHealthTag(@NotNull MatrixStack matrices, @NotNull PlayerEntity player, boolean hasLabel, float tickDelta, int light) {

    }

    enum MyTestEnum1 implements IWidgetableEnum {
        Epic,
        Pro,
        Gamer;

        @Override
        public @NotNull Text getName() {
            return Text.of(this.name());
        }

        @Override
        public @NotNull Tooltip getTooltip() {
            return Tooltip.of(Text.of(this.name()));
        }
    }

    enum MyTestEnum2 implements IWidgetableEnum {
        Dit,
        Is,
        Test;

        @Override
        public @NotNull Text getName() {
            return Text.of(this.name());
        }

        @Override
        public @NotNull Tooltip getTooltip() {
            return Tooltip.of(Text.of(this.name()));
        }
    }
}
