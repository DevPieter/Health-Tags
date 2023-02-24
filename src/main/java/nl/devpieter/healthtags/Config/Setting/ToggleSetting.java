package nl.devpieter.healthtags.Config.Setting;

public class ToggleSetting extends Setting<Boolean> {

    public ToggleSetting(Boolean value) {
        super(value);
    }

    public void toggle() {
        this.set(!this.get());
    }
}
