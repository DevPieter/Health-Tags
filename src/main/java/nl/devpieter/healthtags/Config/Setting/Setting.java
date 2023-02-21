package nl.devpieter.healthtags.Config.Setting;

public class Setting<T> {

    private T value;
    private final T defaultValue;

    public Setting(T value) {
        this.value = value;
        this.defaultValue = value;
    }

    public T value() {
        return value;
    }

    public void value(T value) {
        this.value = value;
    }

    public T defaultValue() {
        return defaultValue;
    }
}
