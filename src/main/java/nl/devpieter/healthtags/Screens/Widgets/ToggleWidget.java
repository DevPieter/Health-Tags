package nl.devpieter.healthtags.Screens.Widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import nl.devpieter.healthtags.Screens.Callbacks.ToggleCallback;

import java.awt.*;

public class ToggleWidget extends ClickableWidget {

    private final MinecraftClient client = MinecraftClient.getInstance();

    private final Identifier texture = new Identifier("healthtags", "textures/gui/toggle_widget.png");
    private final int color = Color.WHITE.getRGB();
    private String enabledTranslationKey, disabledTranslationKey;

    private boolean toggled;
    private ToggleCallback callback;

    public ToggleWidget(int x, int y, int width, int height, boolean toggled) {
        super(x, y, width, height, Text.empty());
        this.toggled = toggled;
    }

    public void setTranslationKeys(String enabledTranslationKey, String disabledTranslationKey) {
        this.enabledTranslationKey = enabledTranslationKey;
        this.disabledTranslationKey = disabledTranslationKey;
    }

    public void setCallback(ToggleCallback callback) {
        this.callback = callback;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // Set the message
        String translationKey = this.toggled ? this.enabledTranslationKey : this.disabledTranslationKey;
        this.setMessage(Text.translatable(translationKey));

        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        // Set the texture
        RenderSystem.setShaderTexture(0, this.texture);

        // Draw the button
        int imageV = (this.hovered ? 20 : 0) + (this.toggled ? 40 : 0);
        this.drawTexture(matrices, this.getX(), this.getY(), 0, imageV, this.width / 2, this.height);
        this.drawTexture(matrices, this.getX() + this.width / 2, this.getY(), 200 - this.width / 2, imageV, this.width / 2, this.height);

        // Draw the text
        drawCenteredText(matrices, this.client.textRenderer, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, this.color);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean isClicked = super.mouseClicked(mouseX, mouseY, button);
        if (!isClicked) return true;

        this.toggled = !this.toggled;
        if (this.callback != null) this.callback.onToggle(this.toggled);

        return false;
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}