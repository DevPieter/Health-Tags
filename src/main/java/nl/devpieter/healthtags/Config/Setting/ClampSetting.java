package nl.devpieter.healthtags.Config.Setting;

public class ClampSetting<T extends Number & Comparable<T>> extends Setting<T> {

    private final T min, max;

    public ClampSetting(T value, T min, T max) {
        super(value);
        this.min = min;
        this.max = max;
    }

    @Override
    public T get() {
        double value = super.get().doubleValue();
        if (value < this.min.doubleValue()) return this.min;
        if (value > this.max.doubleValue()) return this.max;
        return super.get();
    }
}
