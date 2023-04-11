package nl.devpieter.healthtags.Config.Setting;

import org.jetbrains.annotations.NotNull;

public class ToggleSetting extends Setting<Boolean> {

    /***
     * Creates a new toggle setting with the given value.
     * @param value The value of this setting.
     */
    public ToggleSetting(@NotNull Boolean value) {
        super(value);
    }

    /***
     * Toggles the value of this setting.
     */
    public void toggle() {
        this.set(!this.get());
    }
}
