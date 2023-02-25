package nl.devpieter.healthtags.Config.WidgetSetting;

import com.google.gson.annotations.Expose;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.text.Text;
import nl.devpieter.healthtags.Config.Setting.ClampSetting;
import nl.devpieter.healthtags.Screens.Widgets.SliderWidget;
import org.jetbrains.annotations.NotNull;

public class SliderWidgetSetting extends ClampSetting<Integer> implements IWidgetSetting<SliderWidget> {

    @Expose
    private final String translationKey, tooltipTranslationKey;

    public SliderWidgetSetting(Integer value, Integer min, Integer max, String translationKey) {
        this(value, min, max, translationKey, translationKey + ".tooltip");
    }

    public SliderWidgetSetting(Integer value, Integer min, Integer max, String translationKey, String tooltipTranslationKey) {
        super(value, min, max);
        this.translationKey = translationKey;
        this.tooltipTranslationKey = tooltipTranslationKey;
    }

    @Override
    public @NotNull SliderWidget getWidget(int x, int y, int width, int height) {
        SliderWidget sliderWidget = new SliderWidget(x, y, width, this.translationKey);
        sliderWidget.setTooltip(Tooltip.of(Text.translatable(this.tooltipTranslationKey)));
        sliderWidget.setValues(this.get(), this.min(), this.max());
        sliderWidget.setCallback(value -> this.set((int) Math.round(value)));
        return sliderWidget;
    }
}
