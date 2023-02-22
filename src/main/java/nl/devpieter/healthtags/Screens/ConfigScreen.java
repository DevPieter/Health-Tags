package nl.devpieter.healthtags.Screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import nl.devpieter.healthtags.Config.Config;
import nl.devpieter.healthtags.Config.Setting.Setting;
import nl.devpieter.healthtags.Config.Setting.SliderSetting;
import nl.devpieter.healthtags.Enums.HealthTagRenderer;
import nl.devpieter.healthtags.Screens.Widgets.ItemToggleWidget;

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

        this.addDrawableChild(new ItemToggleWidget(this.right / 2 - 48, 30, this.config.Enabled.get(), this.config.Enabled::set)).setItem(Items.NAME_TAG.getDefaultStack());
        this.addDrawableChild(new ItemToggleWidget(this.right / 2 + 24, 30, this.config.ShowOnSelf.get(), this.config.ShowOnSelf::set)).setItem(Items.SPYGLASS.getDefaultStack());

        this.addDrawableChild(this.config.ExtraHeight.getSliderWidget(widgetLeft, 80, widgetWidth)).setFormat(this.wholeNumberFormat);
        this.addDrawableChild(this.config.TargetHoldTime.getSliderWidget(widgetLeft, 110, widgetWidth)).setFormat(this.wholeNumberFormat);

        this.addDrawableChild(CyclingButtonWidget.builder(HealthTagRenderer::getName)
                .values(HealthTagRenderer.values())
                .initially(this.config.SelectedRenderer.get())
                .omitKeyText()
                .build(widgetLeft, this.bottom - 30, widgetWidth, 20, Text.empty(), (button, renderer) -> this.config.SelectedRenderer.set(renderer)));

        for (HealthTagRenderer renderer : HealthTagRenderer.values()) {
            if (renderer.getRenderer() == null) continue;
            List<Setting<?>> settings = renderer.getRenderer().getSettings();

            for (int i = 0; i < settings.size(); i++) {
                Setting<?> setting = settings.get(i);

                if (!(setting instanceof SliderSetting sliderSetting)) continue;
                this.addDrawableChild(sliderSetting.getSliderWidget(widgetLeft, (this.height / 2) - (settings.size() * 20) + (30 * i) + 40, widgetWidth)).setFormat(this.wholeNumberFormat);
            }
        }
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
