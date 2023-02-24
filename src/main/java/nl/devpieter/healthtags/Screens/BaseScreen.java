package nl.devpieter.healthtags.Screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import nl.devpieter.healthtags.Config.Config;
import nl.devpieter.healthtags.Enums.HealthTagRenderer;

import java.awt.*;

public class BaseScreen extends Screen {

    protected final Config config = Config.getInstance();

    protected final Color backgroundColor = new Color(47, 48, 55, 240);
    protected final Color titleColor = new Color(233, 164, 155);
    protected final Color textColor = new Color(255, 255, 255);

    protected int left, right, top, bottom;
    protected int widgetLeft, widgetRight, widgetWidth;

    protected BaseScreen(Text title) {
        super(title);
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
        drawCenteredText(matrices, this.textRenderer, this.title, this.right / 2, 10, this.titleColor.getRGB());
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void renderBackground(MatrixStack matrices) {
        fill(matrices, this.left, this.top, this.right, this.bottom, this.backgroundColor.getRGB());
    }

    @Override
    public void close() {
        this.config.save();
        HealthTagRenderer.saveAllSettings();

        super.close();
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
