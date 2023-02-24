package nl.devpieter.healthtags.Config.WidgetSetting;

import com.google.gson.annotations.Expose;
import net.minecraft.client.gui.widget.ButtonWidget;
import nl.devpieter.healthtags.Config.Setting.ToggleSetting;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;

public class ToggleWidgetSetting extends ToggleSetting implements IWidgetSetting<ButtonWidget> {

    @Expose
    private final String translationKey;

    public ToggleWidgetSetting(Boolean value, String translationKey) {
        super(value);
        this.translationKey = translationKey;
    }

    @Override
    public @NotNull ButtonWidget getWidget(int x, int y, int width, int height) {
        throw new NotImplementedException("Not implemented yet");
    }
}
