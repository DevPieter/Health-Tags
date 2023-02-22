package nl.devpieter.healthtags.Screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import nl.devpieter.healthtags.Config.Config;
import nl.devpieter.healthtags.Enums.HealthTagRenderer;
import nl.devpieter.healthtags.Screens.Widgets.SliderWidget;

import java.awt.*;
import java.text.DecimalFormat;

public class ConfigScreen extends Screen {

    private final Config config = Config.getInstance();
    private final DecimalFormat wholeNumberFormat = new DecimalFormat("#");

    private int left, right, top, bottom;
    private final Color backgroundColor = new Color(47, 48, 55, 240);
    private final Color titleColor = new Color(233, 164, 155);
    private final Color textColor = new Color(255, 255, 255);

    private SliderWidget heartsInRowSlider, heartsSpacedBy_XSlider, heartsSpacedBy_YSlider;

    public ConfigScreen() {
        // TODO: Move to translation file
        super(Text.of("Health Tags Config"));
    }

    @Override
    protected void init() {
        this.left = 0;
        this.right = this.width / 3;

        this.top = 0;
        this.bottom = this.height;

        int widgetLeft = this.left + 10;
        int widgetWidth = (this.right - 10) - (widgetLeft);

        /* === Global settings === */
        // TODO: Enabled

        SliderWidget extraHeightSlider = new SliderWidget(widgetLeft, 20, widgetWidth, "config.healthtags.extra_height");
        extraHeightSlider.setFormat(this.wholeNumberFormat);
        extraHeightSlider.setValues(this.config.ExtraHeight.get(), -20, 60);
        extraHeightSlider.setCallback(value -> this.config.ExtraHeight.set((int) Math.round(value)));
        this.addDrawableChild(extraHeightSlider);

        // TODO: ShowOnSelf

        CyclingButtonWidget<HealthTagRenderer> selectedRendererButton = CyclingButtonWidget.builder(HealthTagRenderer::getName)
                .values(HealthTagRenderer.values())
                .initially(this.config.SelectedRenderer.get())
                .omitKeyText()
                .build(widgetLeft, this.bottom - 30, widgetWidth, 20, Text.empty(), (button, renderer) -> this.config.SelectedRenderer.set(renderer));
        this.addDrawableChild(selectedRendererButton);

        /* === Settings for HeartTagRenderer === */
        this.heartsInRowSlider = new SliderWidget(widgetLeft, 50, widgetWidth, "config.healthtags.hearts_in_row");
        this.heartsInRowSlider.setFormat(this.wholeNumberFormat);
        this.heartsInRowSlider.setValues(this.config.HeartsInRow.get(), 5, 25);
        this.heartsInRowSlider.setCallback(value -> this.config.HeartsInRow.set((int) Math.round(value)));
        this.addDrawableChild(heartsInRowSlider);

        this.heartsSpacedBy_XSlider = new SliderWidget(widgetLeft, 80, widgetWidth, "config.healthtags.hearts_spaced_by_x");
        this.heartsSpacedBy_XSlider.setFormat(this.wholeNumberFormat);
        this.heartsSpacedBy_XSlider.setValues(this.config.HeartsSpacedBy_X.get(), 6, 16);
        this.heartsSpacedBy_XSlider.setCallback(value -> this.config.HeartsSpacedBy_X.set((int) Math.round(value)));
        this.addDrawableChild(heartsSpacedBy_XSlider);

        this.heartsSpacedBy_YSlider = new SliderWidget(widgetLeft, 110, widgetWidth, "config.healthtags.hearts_spaced_by_y");
        this.heartsSpacedBy_YSlider.setFormat(this.wholeNumberFormat);
        this.heartsSpacedBy_YSlider.setValues(this.config.HeartsSpacedBy_Y.get(), 6, 16);
        this.heartsSpacedBy_YSlider.setCallback(value -> this.config.HeartsSpacedBy_Y.set((int) Math.round(value)));
        this.addDrawableChild(heartsSpacedBy_YSlider);

        /* === Settings for TargetManager === */
        // TODO: TargetHoldTime
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        boolean isHeartRendererSelected = this.config.SelectedRenderer.get() == HealthTagRenderer.HEART;
        this.heartsInRowSlider.active = isHeartRendererSelected;
        this.heartsSpacedBy_XSlider.active = isHeartRendererSelected;
        this.heartsSpacedBy_YSlider.active = isHeartRendererSelected;

        fill(matrices, this.left, this.top, this.right, this.bottom, this.backgroundColor.getRGB());
        drawCenteredText(matrices, this.textRenderer, this.title, this.right / 2, 10, this.titleColor.getRGB());

        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void renderBackground(MatrixStack matrices) {
        super.renderBackground(matrices);
    }

    @Override
    public void close() {
        this.config.save();
        super.close();
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
