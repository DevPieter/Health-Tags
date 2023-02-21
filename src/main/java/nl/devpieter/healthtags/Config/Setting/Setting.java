package nl.devpieter.healthtags.Config.Setting;

import com.google.gson.annotations.Expose;

public class Setting<T> {

    @Expose
    private T value;

    public Setting(T value) {
        this.value = value;
    }

    public T value() {
        return value;
    }

    public void value(T value) {
        this.value = value;
    }
}