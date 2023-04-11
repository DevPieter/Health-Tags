package nl.devpieter.healthtags.Config.Setting;

import com.google.gson.annotations.Expose;
import org.jetbrains.annotations.NotNull;

public class ClampSetting<T extends Number & Comparable<T>> extends Setting<T> {

    @Expose
    @NotNull
    private final T min, max;

    /***
     * Creates a new clamp setting with the given value, minimum and maximum.
     * @param value The value of this setting.
     * @param min The minimum value of this setting.
     * @param max The maximum value of this setting.
     */
    public ClampSetting(@NotNull T value, @NotNull T min, @NotNull T max) {
        super(value);
        this.min = min;
        this.max = max;
    }

    @Override
    public @NotNull T get() {
        double value = super.get().doubleValue();
        if (value < this.min.doubleValue()) return this.min;
        if (value > this.max.doubleValue()) return this.max;
        return super.get();
    }

    /***
     * Gets the minimum value of this setting.
     * @return The minimum value of this setting.
     */
    public @NotNull T min() {
        return this.min;
    }

    /***
     * Gets the maximum value of this setting.
     * @return The maximum value of this setting.
     */
    public @NotNull T max() {
        return this.max;
    }
}
