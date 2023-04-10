package nl.devpieter.healthtags.Config.Setting;

import com.google.gson.annotations.Expose;

public class Setting<T> {

    @Expose
    private T value;

    /***
     * Creates a new setting with the given value.
     * @param value The value of this setting.
     */
    public Setting(T value) {
        this.value = value;
    }

    /***
     * Gets the value of this setting.
     * @return The value of this setting.
     */
    public T get() {
        return this.value;
    }

    /***
     * Sets the value of this setting.
     * @param value The new value of this setting.
     */
    public void set(T value) {
        this.value = value;
    }
}