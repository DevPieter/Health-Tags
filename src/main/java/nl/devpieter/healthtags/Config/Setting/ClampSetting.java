package nl.devpieter.healthtags.Config.Setting;

public class ClampSetting<T extends Number & Comparable<T>> extends Setting<T> {

    private final T min, max;

    public ClampSetting(T value, T min, T max) {
        super(value);
        this.min = min;
        this.max = max;
    }

    @Override
    public T value() {
        double value = super.value().doubleValue();
        if (value < min.doubleValue()) return min;
        if (value > max.doubleValue()) return max;
        return super.value();
    }
}
