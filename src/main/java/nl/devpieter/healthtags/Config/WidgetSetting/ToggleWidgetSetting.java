package nl.devpieter.healthtags.Config.WidgetSetting;

import com.google.gson.annotations.Expose;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.text.Text;
import nl.devpieter.healthtags.Config.Setting.ToggleSetting;
import nl.devpieter.healthtags.Screens.Widgets.ToggleWidget;
import org.jetbrains.annotations.NotNull;

public class ToggleWidgetSetting extends ToggleSetting implements IWidgetSetting<ToggleWidget> {

    @Expose
    @NotNull
    private final String enabledTranslationKey, disabledTranslationKey;
    @Expose
    @NotNull
    private final String tooltipTranslationKey;

    public ToggleWidgetSetting(@NotNull Boolean value, @NotNull String translationKey) {
        this(value, translationKey, translationKey, translationKey + ".tooltip");
    }

    public ToggleWidgetSetting(@NotNull Boolean value, @NotNull String translationKey, @NotNull String tooltipTranslationKey) {
        super(value);
        this.enabledTranslationKey = translationKey;
        this.disabledTranslationKey = translationKey;
        this.tooltipTranslationKey = tooltipTranslationKey;
    }

    public ToggleWidgetSetting(@NotNull Boolean value, @NotNull String enabledTranslationKey, @NotNull String disabledTranslationKey, @NotNull String tooltipTranslationKey) {
        super(value);
        this.enabledTranslationKey = enabledTranslationKey;
        this.disabledTranslationKey = disabledTranslationKey;
        this.tooltipTranslationKey = tooltipTranslationKey;
    }

    @Override
    public @NotNull ToggleWidget getWidget(int x, int y, int width, int height) {
        ToggleWidget toggleWidget = new ToggleWidget(x, y, width, height, this.get());
        toggleWidget.setTranslationKeys(this.enabledTranslationKey, this.disabledTranslationKey);
        toggleWidget.setTooltip(Tooltip.of(Text.translatable(this.tooltipTranslationKey)));
        toggleWidget.setCallback(this::set);
        return toggleWidget;
    }
}
