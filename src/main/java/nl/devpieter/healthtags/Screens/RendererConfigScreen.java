package nl.devpieter.healthtags.Screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import nl.devpieter.healthtags.Config.Setting.Setting;
import nl.devpieter.healthtags.Config.WidgetSetting.IWidgetSetting;
import nl.devpieter.healthtags.Enums.HealthTagRenderer;
import nl.devpieter.healthtags.Renderers.IHealthTagRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RendererConfigScreen extends ConfigScreenBase {

    private final @NotNull HealthTagRenderer renderer;
    private final int settingsPageIndex;

    public RendererConfigScreen(@NotNull HealthTagRenderer renderer) {
        this(renderer, null, 0);
    }

    public RendererConfigScreen(@NotNull HealthTagRenderer renderer, @Nullable Screen parent) {
        this(renderer, parent, 0);
    }

    private RendererConfigScreen(@NotNull HealthTagRenderer renderer, @Nullable Screen parent, int settingsPageIndex) {
        super(Text.translatable("healthtags.config.renderer_screen.title", renderer.getName()), parent);
        this.renderer = renderer;
        this.settingsPageIndex = settingsPageIndex;
    }

    @Override
    protected void init() {
        super.init();

        // Back button.
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("healthtags.text.back"), button -> this.close())
                .dimensions(this.widgetLeft, this.bottom - 30, this.widgetWidth, 20)
                .build());

        // Return if the health tag renderer is null or has no settings.
        IHealthTagRenderer tagRenderer = this.renderer.getRenderer();
        if (tagRenderer == null || tagRenderer.getSettings().isEmpty()) return;

        // Get the settings from the renderer.
        List<Setting<?>> settings = tagRenderer.getSettings();

        int totalHeight = 40 + (settings.size() * 30);
        int allowedHeight = this.bottom - 70;

        // TODO: Handle the settings that don't fit on the screen
        List<Setting<?>> fitSettings = settings.subList(0, Math.min(settings.size(), allowedHeight / 30));

        // Add the settings to the screen.
        for (int i = 0; i < fitSettings.size(); i++) {
            if (!(fitSettings.get(i) instanceof IWidgetSetting<?> widgetSetting)) continue;
            this.addDrawableChild(widgetSetting.getWidget(widgetLeft, 40 + (i * 30), widgetWidth, 20));
        }
    }
}
