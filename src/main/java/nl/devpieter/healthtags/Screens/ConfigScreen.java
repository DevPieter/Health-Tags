package nl.devpieter.healthtags.Screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import nl.devpieter.healthtags.Config.Config;
import nl.devpieter.healthtags.Enums.HealthTagRenderer;
import nl.devpieter.healthtags.Screens.Widgets.SliderWidget;

import java.text.DecimalFormat;

public class ConfigScreen extends Screen {

    private final Config config = Config.getInstance();
    private final DecimalFormat wholeNumberFormat = new DecimalFormat("#");

    /* === Texture === */
    private final Identifier texture = new Identifier("healthtags", "textures/gui/config.png");
    private final int textureWidth = 147, textureHeight = 166;
    private int top, left;

    /* === Widgets === */
    private SliderWidget heartsInRowSlider, heartsSpacedBy_XSlider, heartsSpacedBy_YSlider;

    public ConfigScreen() {
        super(Text.of("Config"));
    }

    @Override
    protected void init() {
        this.top = (this.height - this.textureHeight) / 2;
        this.left = (this.width - this.textureWidth) / 2;

        int center = this.width / 2;
        int bottom = this.textureHeight + top;

        /* === Global settings === */
        // TODO: Enabled

        SliderWidget extraHeightSlider = new SliderWidget(20, 20, 150, "config.healthtags.extra_height");
        extraHeightSlider.setFormat(this.wholeNumberFormat);
        extraHeightSlider.setValues(this.config.ExtraHeight.get(), -20, 60);
        extraHeightSlider.setCallback(value -> this.config.ExtraHeight.set((int) Math.round(value)));
        this.addDrawableChild(extraHeightSlider);

        // TODO: ShowOnSelf

        CyclingButtonWidget<HealthTagRenderer> selectedRendererButton = CyclingButtonWidget.builder(HealthTagRenderer::getName)
                .values(HealthTagRenderer.values())
                .initially(this.config.SelectedRenderer.get())
                .omitKeyText()
                .build(20, 140, 128, 20, Text.empty(), (button, renderer) -> this.config.SelectedRenderer.set(renderer));
        this.addDrawableChild(selectedRendererButton);

        /* === Settings for HeartTagRenderer === */
        this.heartsInRowSlider = new SliderWidget(20, 50, 150, "config.healthtags.hearts_in_row");
        this.heartsInRowSlider.setFormat(this.wholeNumberFormat);
        this.heartsInRowSlider.setValues(this.config.HeartsInRow.get(), 5, 25);
        this.heartsInRowSlider.setCallback(value -> this.config.HeartsInRow.set((int) Math.round(value)));
        this.addDrawableChild(heartsInRowSlider);

        this.heartsSpacedBy_XSlider = new SliderWidget(20, 80, 150, "config.healthtags.hearts_spaced_by_x");
        this.heartsSpacedBy_XSlider.setFormat(this.wholeNumberFormat);
        this.heartsSpacedBy_XSlider.setValues(this.config.HeartsSpacedBy_X.get(), 6, 16);
        this.heartsSpacedBy_XSlider.setCallback(value -> this.config.HeartsSpacedBy_X.set((int) Math.round(value)));
        this.addDrawableChild(heartsSpacedBy_XSlider);

        this.heartsSpacedBy_YSlider = new SliderWidget(20, 110, 150, "config.healthtags.hearts_spaced_by_y");
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


        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void renderBackground(MatrixStack matrices) {
        super.renderBackground(matrices);

        RenderSystem.setShaderTexture(0, this.texture);
        this.drawTexture(matrices, this.left, this.top, 0, 0, this.textureWidth, this.textureHeight);
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
