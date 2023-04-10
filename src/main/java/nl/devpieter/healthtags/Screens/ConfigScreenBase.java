package nl.devpieter.healthtags.Screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import nl.devpieter.healthtags.Config.Config;
import nl.devpieter.healthtags.Enums.HealthTagRenderer;

import java.awt.*;

public class ConfigScreenBase extends Screen {

    /* ===== Variables ===== */
    protected final Config config = Config.getInstance();
    protected final Screen parent;

    /* ===== Colors ===== */
    protected final Color backgroundColor = new Color(47, 48, 55, 240);
    protected final Color titleColor = new Color(233, 164, 155);
    protected final Color textColor = new Color(255, 255, 255);

    /* ===== Positions ===== */
    protected int left, right, top, bottom;
    protected int widgetLeft, widgetRight, widgetWidth;

    protected ConfigScreenBase(Text title) {
        this(title, null);
    }

    protected ConfigScreenBase(Text title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        this.left = 0;
        this.right = this.width / 3;

        this.top = 0;
        this.bottom = this.height;

        this.widgetLeft = this.left + 10;
        this.widgetRight = this.right - 10;
        this.widgetWidth = (this.right - 10) - (widgetLeft);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);

        drawCenteredText(matrices, this.textRenderer, this.title, this.right / 2, 10, this.titleColor.getRGB());
    }

    @Override
    public void renderBackground(MatrixStack matrices) {
        fill(matrices, this.left, this.top, this.right, this.bottom, this.backgroundColor.getRGB());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) return true;
        if (button != 0 || this.isMouseOver(mouseX, mouseY)) return false;
        this.close();

        return true;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= this.left && mouseX <= this.right && mouseY >= this.top && mouseY <= this.bottom;
    }

    @Override
    public void close() {
        this.config.save();
        HealthTagRenderer.saveAllSettings();

        if (this.client == null) return;
        this.client.setScreen(this.parent);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
