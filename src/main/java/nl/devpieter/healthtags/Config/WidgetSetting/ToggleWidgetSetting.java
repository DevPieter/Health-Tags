package nl.devpieter.healthtags.Config.WidgetSetting;

import com.google.gson.annotations.Expose;
import nl.devpieter.healthtags.Config.Setting.ToggleSetting;
import nl.devpieter.healthtags.Screens.Widgets.ToggleWidget;
import org.jetbrains.annotations.NotNull;

public class ToggleWidgetSetting extends ToggleSetting implements IWidgetSetting<ToggleWidget> {

    @Expose
    private final String enabledTranslationKey, disabledTranslationKey;

    public ToggleWidgetSetting(Boolean value, String translationKey) {
        super(value);
        this.enabledTranslationKey = translationKey;
        this.disabledTranslationKey = translationKey;
    }

    public ToggleWidgetSetting(Boolean value, String enabledTranslationKey, String disabledTranslationKey) {
        super(value);
        this.enabledTranslationKey = enabledTranslationKey;
        this.disabledTranslationKey = disabledTranslationKey;
    }

    @Override
    public @NotNull ToggleWidget getWidget(int x, int y, int width, int height) {
        ToggleWidget toggleWidget = new ToggleWidget(x, y, width, height, this.get());
        toggleWidget.setTranslationKeys(this.enabledTranslationKey, this.disabledTranslationKey);
        toggleWidget.setCallback(this::set);
        return toggleWidget;
    }
}
