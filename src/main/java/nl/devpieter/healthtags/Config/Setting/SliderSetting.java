package nl.devpieter.healthtags.Config.Setting;

import com.google.gson.annotations.Expose;
import nl.devpieter.healthtags.Screens.Widgets.SliderWidget;

public class SliderSetting extends ClampSetting<Integer> {

    @Expose
    private final String translationKey;

    public SliderSetting(Integer value, Integer min, Integer max, String translationKey) {
        super(value, min, max);
        this.translationKey = translationKey;
    }

    public SliderWidget getSliderWidget(int x, int y, int width) {
        SliderWidget sliderWidget = new SliderWidget(x, y, width, this.translationKey);
        sliderWidget.setValues(this.get(), this.min(), this.max());
        sliderWidget.setCallback(value -> this.set((int) Math.round(value)));
        return sliderWidget;
    }
}
