package nl.devpieter.healthtags.Config.WidgetSetting;

import com.google.gson.annotations.Expose;
import net.minecraft.client.gui.widget.ButtonWidget;
import nl.devpieter.healthtags.Config.Setting.Setting;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;

public class ToggleSetting extends Setting<Boolean> implements WidgetSetting<ButtonWidget> {

    @Expose
    private final String translationKey;

    public ToggleSetting(Boolean value, String translationKey) {
        super(value);
        this.translationKey = translationKey;
    }

    public void toggle() {
        this.set(!this.get());
    }

    @Override
    public @NotNull ButtonWidget getWidget(int x, int y, int width, int height) {
        throw new NotImplementedException("Not implemented yet");
    }
}
