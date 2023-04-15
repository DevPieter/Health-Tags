package nl.devpieter.healthtags.Screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import nl.devpieter.healthtags.Config.Setting.Setting;
import nl.devpieter.healthtags.Config.WidgetSetting.IWidgetSetting;
import nl.devpieter.healthtags.Enums.HealthTagRenderer;
import nl.devpieter.healthtags.Renderers.IHealthTagRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RendererConfigScreen extends ConfigScreenBase {

    /* ===== Variables ===== */
    private final @NotNull HealthTagRenderer renderer;
    private final Identifier iconTexture = new Identifier("healthtags", "textures/gui/pagination_icons.png");
    private final int pageIndex;

    /* ===== Positions ===== */
    private int allowedHeight, buttonHeight;

    public RendererConfigScreen(@NotNull HealthTagRenderer renderer) {
        this(renderer, null, 0);
    }

    public RendererConfigScreen(@NotNull HealthTagRenderer renderer, @Nullable Screen parent) {
        this(renderer, parent, 0);
    }

    private RendererConfigScreen(@NotNull HealthTagRenderer renderer, @Nullable Screen parent, int pageIndex) {
        super(Text.translatable("healthtags.config.renderer_screen.title", renderer.getName()), parent);
        this.renderer = renderer;
        this.pageIndex = pageIndex;
    }

    @Override
    protected void init() {
        super.init();

        // Set the positions.
        this.allowedHeight = this.bottom - 90;
        this.buttonHeight = 20 + 10;

        // Get the fitting settings.
        List<Setting<?>> fittingSettings = this.getFittingSettings();
        if (fittingSettings == null) return;

        // Add the settings to the screen.
        for (int i = 0; i < fittingSettings.size(); i++) {
            if (!(fittingSettings.get(i) instanceof IWidgetSetting<?> widgetSetting)) continue;
            this.addDrawableChild(widgetSetting.getWidget(widgetLeft, 40 + (i * this.buttonHeight), widgetWidth, 20));
        }

        // Left button.
        TexturedButtonWidget leftButton = new TexturedButtonWidget(this.widgetLeft, this.bottom - 30, 14, 20, 15, 1, 21, this.iconTexture, 256, 256, button -> this.setIndex(this.pageIndex - 1));
        leftButton.active = this.hasPreviousPage();
        this.addDrawableChild(leftButton);

        // Back button.
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("healthtags.text.back"), button -> this.close())
                .dimensions(this.widgetLeft + (14 + 5), this.bottom - 30, this.widgetWidth - ((14 + 5) * 2), 20).build());

        // Right button.
        TexturedButtonWidget rightButton = new TexturedButtonWidget(this.widgetLeft + this.widgetWidth - 15, this.bottom - 30, 14, 20, 1, 1, 21, this.iconTexture, 256, 256, button -> this.setIndex(this.pageIndex + 1));
        rightButton.active = this.hasNextPage();
        this.addDrawableChild(rightButton);
    }

    private boolean hasPreviousPage() {
        return this.pageIndex > 0;
    }

    private boolean hasNextPage() {
        // Get the settings.
        List<Setting<?>> settings = this.getSettings();
        if (settings == null) return false;

        // Check if there is a next page.
        return (this.pageIndex + 1) * (this.allowedHeight / this.buttonHeight) < settings.size();
    }

    private void setIndex(int index) {
        // Get the settings.
        List<Setting<?>> settings = this.getSettings();
        if (settings == null) return;

        // Check if the client is null.
        if (this.client == null) return;

        // Check if the index is out of bounds.
        if (index < 0 || index * (this.allowedHeight / this.buttonHeight) >= settings.size()) return;

        // Set the index.
        this.client.setScreen(new RendererConfigScreen(this.renderer, this.parent, index));
    }

    private @Nullable List<Setting<?>> getSettings() {
        // Check if the renderer is null or has no settings.
        IHealthTagRenderer tagRenderer = this.renderer.getRenderer();
        if (tagRenderer == null || tagRenderer.getSettings().isEmpty()) return null;

        // Get the settings from the renderer.
        return tagRenderer.getSettings();
    }

    private @Nullable List<Setting<?>> getFittingSettings() {
        // Get the settings from the renderer.
        List<Setting<?>> settings = this.getSettings();
        if (settings == null) return null;

        // Get the fitting settings.
        return settings.subList(this.pageIndex * (this.allowedHeight / this.buttonHeight), Math.min(settings.size(), (this.pageIndex + 1) * (this.allowedHeight / this.buttonHeight)));
    }
}
