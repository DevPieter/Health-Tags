package nl.devpieter.healthtags.Config.WidgetSetting;

import com.google.gson.annotations.Expose;
import nl.devpieter.healthtags.Config.Setting.ClampSetting;
import nl.devpieter.healthtags.Screens.Widgets.SliderWidget;
import org.jetbrains.annotations.NotNull;

public class SliderWidgetSetting extends ClampSetting<Integer> implements IWidgetSetting<SliderWidget> {

    @Expose
    private final String translationKey;

    public SliderWidgetSetting(Integer value, Integer min, Integer max, String translationKey) {
        super(value, min, max);
        this.translationKey = translationKey;
    }

    @Override
    public @NotNull SliderWidget getWidget(int x, int y, int width, int height) {
        SliderWidget sliderWidget = new SliderWidget(x, y, width, this.translationKey);
        sliderWidget.setValues(this.get(), this.min(), this.max());
        sliderWidget.setCallback(value -> this.set((int) Math.round(value)));
        return sliderWidget;
    }
}
