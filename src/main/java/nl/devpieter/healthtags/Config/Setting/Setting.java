package nl.devpieter.healthtags.Config.Setting;

import com.google.gson.annotations.Expose;

public class Setting<T> {

    @Expose
    private T value;

    public Setting(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}