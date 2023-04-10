package nl.devpieter.healthtags.Config.Setting;

public class ToggleSetting extends Setting<Boolean> {

    /***
     * Creates a new toggle setting with the given value.
     * @param value The value of this setting.
     */
    public ToggleSetting(Boolean value) {
        super(value);
    }

    /***
     * Toggles the value of this setting.
     */
    public void toggle() {
        this.set(!this.get());
    }
}
