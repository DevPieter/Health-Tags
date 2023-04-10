package nl.devpieter.healthtags.Screens.Widgets;

import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.text.DecimalFormat;

public class SliderWidget extends net.minecraft.client.gui.widget.SliderWidget {

    private final String translationKey;
    private DecimalFormat format = new DecimalFormat("#.##");
    private double min, max;
    private ICallback<Double> callback;

    public SliderWidget(int x, int y, int width, String translationKey) {
        super(x, y, width, 20, Text.empty(), 0);
        this.translationKey = translationKey;
    }

    /***
     * Sets the decimal format for the value.
     * @param format The decimal format.
     */
    public void setFormat(DecimalFormat format) {
        this.format = format;
    }

    /***
     * Sets the value, min and max of the slider.
     * @param value The value of the slider.
     * @param min The minimum value of the slider.
     * @param max The maximum value of the slider.
     */
    public void setValues(double value, double min, double max) {
        this.min = min;
        this.max = max;
        this.set(value);
    }

    /***
     * Sets the callback for when the value is changed.
     * @param callback The callback.
     */
    public void setCallback(ICallback<Double> callback) {
        this.callback = callback;
    }

    @Override
    protected void updateMessage() {
        this.setMessage(Text.translatable(this.translationKey, this.format.format(this.get())));
    }

    @Override
    protected void applyValue() {
        if (callback != null) this.callback.onCallback(this.get());
    }

    /***
     * Gets the value of the slider.
     * @return The value of the slider.
     */
    public double get() {
        double value = MathHelper.clamp(this.value, 0.0, 1.0);
        return this.min + value * (this.max - this.min);
    }

    /***
     * Sets the value of the slider.
     * @param value The value of the slider.
     */
    public void set(double value) {
        this.value = MathHelper.clamp((value - this.min) / (this.max - this.min), 0.0, 1.0);
        this.updateMessage();
    }
}
