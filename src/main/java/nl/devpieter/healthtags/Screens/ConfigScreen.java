package nl.devpieter.healthtags.Screens;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import nl.devpieter.healthtags.Config.WidgetSetting.EnumWidgetSetting;
import nl.devpieter.healthtags.Enums.HealthTagRenderer;
import nl.devpieter.healthtags.Renderers.IHealthTagRenderer;

import java.text.DecimalFormat;

public class ConfigScreen extends ConfigScreenBase {

    private final DecimalFormat wholeNumberFormat = new DecimalFormat("#");

    public ConfigScreen() {
        super(Text.translatable("healthtags.config.screen.title"));
    }

    private ButtonWidget editButton;

    @Override
    protected void init() {
        super.init();
        int buttonWidth = (this.widgetWidth / 2) - 5;

        // Enabled and ShowOnSelf buttons.
        this.addDrawableChild(this.config.Enabled.getWidget(this.widgetLeft, 40, buttonWidth, 20));
        this.addDrawableChild(this.config.ShowOnSelf.getWidget(this.widgetRight - buttonWidth, 40, buttonWidth, 20));

        // Extra Height and Target Hold Time sliders.
        this.addDrawableChild(this.config.ExtraHeight.getWidget(this.widgetLeft, 70, this.widgetWidth, 20)).setFormat(this.wholeNumberFormat);
        this.addDrawableChild(this.config.TargetHoldTime.getWidget(this.widgetLeft, 100, this.widgetWidth, 20)).setFormat(this.wholeNumberFormat);

        // Selected Renderer.
        EnumWidgetSetting<HealthTagRenderer> selectedRenderer = this.config.SelectedRenderer;
        this.addDrawableChild(selectedRenderer.getWidget(this.widgetLeft, this.bottom - 30, buttonWidth + 30, 20));

        // Edit Button.
        this.editButton = this.addDrawableChild(ButtonWidget.builder(Text.translatable("healthtags.text.settings"), button -> {
            if (this.client == null) return;
            this.client.setScreen(new RendererConfigScreen(selectedRenderer.get(), this));
        }).dimensions(this.widgetRight - buttonWidth + 30, this.bottom - 30, buttonWidth - 30, 20).build());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        IHealthTagRenderer tagRenderer = this.config.SelectedRenderer.get().getRenderer();
        this.editButton.active = tagRenderer != null && !tagRenderer.getSettings().isEmpty();

        super.render(matrices, mouseX, mouseY, delta);
    }
}
