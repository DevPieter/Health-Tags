package nl.devpieter.healthtags.Screens.Widgets;

import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import nl.devpieter.healthtags.Screens.Callbacks.SliderCallback;

import java.text.DecimalFormat;

public class SliderWidget extends net.minecraft.client.gui.widget.SliderWidget {

    private final String translationKey;
    private DecimalFormat format = new DecimalFormat("#.##");
    private double min, max;
    private SliderCallback callback;

    public SliderWidget(int x, int y, int width, String translationKey) {
        super(x, y, width, 20, Text.empty(), 0);
        this.translationKey = translationKey;
    }

    public void setFormat(DecimalFormat format) {
        this.format = format;
    }

    public void setValues(double value, double min, double max) {
        this.min = min;
        this.max = max;
        this.set(value);
    }

    public void setCallback(SliderCallback callback) {
        this.callback = callback;
    }

    @Override
    protected void updateMessage() {
        this.setMessage(Text.translatable(this.translationKey, this.format.format(this.get())));
    }

    @Override
    protected void applyValue() {
        if (callback != null) this.callback.onSlide(this.get());
    }

    public double get() {
        double value = MathHelper.clamp(this.value, 0.0, 1.0);
        return this.min + value * (this.max - this.min);
    }

    public void set(double value) {
        this.value = MathHelper.clamp((value - this.min) / (this.max - this.min), 0.0, 1.0);
        this.updateMessage();
    }
}