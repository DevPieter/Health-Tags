package nl.devpieter.healthtags.Config.Setting;

import com.google.gson.annotations.Expose;
import org.jetbrains.annotations.NotNull;

public class Setting<T> {

    @Expose
    @NotNull
    private T value;

    /***
     * Creates a new setting with the given value.
     * @param value The value of this setting.
     */
    public Setting(@NotNull T value) {
        this.value = value;
    }

    /***
     * Gets the value of this setting.
     * @return The value of this setting.
     */
    public @NotNull T get() {
        return this.value;
    }

    /***
     * Sets the value of this setting.
     * @param value The new value of this setting.
     */
    public void set(@NotNull T value) {
        this.value = value;
    }
}