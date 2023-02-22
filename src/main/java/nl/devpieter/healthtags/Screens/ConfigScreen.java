package nl.devpieter.healthtags.Screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import nl.devpieter.healthtags.Config.Config;
import nl.devpieter.healthtags.Config.Setting.Setting;
import nl.devpieter.healthtags.Config.Setting.SliderSetting;
import nl.devpieter.healthtags.Enums.HealthTagRenderer;
import nl.devpieter.healthtags.Screens.Widgets.SliderWidget;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

public class ConfigScreen extends Screen {

    private final Config config = Config.getInstance();
    private final DecimalFormat wholeNumberFormat = new DecimalFormat("#");

    private int left, right, top, bottom;
    private final Color backgroundColor = new Color(47, 48, 55, 240);
    private final Color titleColor = new Color(233, 164, 155);
    private final Color textColor = new Color(255, 255, 255);

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

        this.addDrawableChild(CyclingButtonWidget.builder(HealthTagRenderer::getName)
                .values(HealthTagRenderer.values())
                .initially(this.config.SelectedRenderer.get())
                .omitKeyText()
                .build(widgetLeft, this.bottom - 30, widgetWidth, 20, Text.empty(), (button, renderer) -> this.config.SelectedRenderer.set(renderer)));

        /* === Settings for HeartTagRenderer === */

        for (HealthTagRenderer renderer : HealthTagRenderer.values()) {
            if (renderer.getRenderer() == null) continue;
            List<Setting<?>> settings = renderer.getRenderer().getSettings();

            for (int i = 0; i < settings.size(); i++) {
                Setting<?> setting = settings.get(i);

                if (!(setting instanceof SliderSetting sliderSetting)) continue;
                this.addDrawableChild(sliderSetting.getSliderWidget(this.width - widgetWidth - 10, 10 + (30 * i), widgetWidth)).setFormat(this.wholeNumberFormat);
            }
        }

        /* === Settings for TargetManager === */
        // TODO: TargetHoldTime
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        fill(matrices, this.left, this.top, this.right, this.bottom, this.backgroundColor.getRGB());
        drawCenteredText(matrices, this.textRenderer, this.title, this.right / 2, 10, this.titleColor.getRGB());

        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        // Save settings on close
        this.config.save();
        HealthTagRenderer.saveAllSettings();

        super.close();
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
