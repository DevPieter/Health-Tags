package nl.devpieter.healthtags.Renderers;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import nl.devpieter.healthtags.Config.Config;
import nl.devpieter.healthtags.Enums.HeartType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class PercentageTagRenderer implements IHealthTagRenderer {

    private final Identifier icons = new Identifier("textures/gui/icons.png");

    @Override
    public void renderHealthTag(@NotNull MatrixStack matrices, @NotNull PlayerEntity player, boolean hasLabel, float tickDelta, int light) {
        // Get the config values.
        int extraHeight = Config.getInstance().ExtraHeight.get();

        // Calculate the percentage.
        float health = player.getHealth() + player.getAbsorptionAmount();
        int percentage = (int) (health / player.getMaxHealth() * 100);

        // Get the width of the text.
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        int width = textRenderer.getWidth(percentage + "%") + 12;

        // Get the x position.
        int x = -width / 2;

        // Get the y position.
        int y = (hasLabel ? -12 : -2) - extraHeight;

        // Enable the depth test and polygon offset.
        RenderSystem.enableDepthTest();
        RenderSystem.enablePolygonOffset();

        // Draw the background.
        RenderSystem.polygonOffset(1, 2);
        float opacity = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F);
        DrawableHelper.fill(matrices, x, y, x + width, y + 11, (int) (opacity * 255.0F) << 24);

        // Draw the percentage and heart.
        RenderSystem.polygonOffset(1, 1);
        this.drawPercentage(matrices, textRenderer, x + 12, y + 2, percentage);
        this.drawHeart(matrices, x + 1, y + 1, HeartType.fromState(player), percentage);

        // Disable the depth test and polygon offset.
        RenderSystem.disableDepthTest();
        RenderSystem.disablePolygonOffset();
    }

    private void drawPercentage(MatrixStack matrices, TextRenderer textRenderer, int x, int y, int percentage) {
        Color color;

        // Calculate the color.
        if (percentage < 25) color = new Color(170, 0, 0);
        else if (percentage < 50) color = new Color(255, 85, 85);
        else if (percentage < 75) color = new Color(255, 255, 85);
        else if (percentage <= 100) color = new Color(85, 255, 85);
        else color = new Color(255, 170, 0);

        // Draw the text.
        textRenderer.draw(matrices, percentage + "%", x, y, color.getRGB());
    }

    private void drawHeart(MatrixStack matrices, int x, int y, HeartType type, int percentage) {
        // Set the shader texture and enable the depth test.
        RenderSystem.setShaderTexture(0, this.icons);
        RenderSystem.enableDepthTest();

        // Draw a container.
        RenderSystem.polygonOffset(1, 1);
        DrawableHelper.drawTexture(matrices, x, y, 16, 0, 9, 9, 256, 256);

        // Draw a heart.
        RenderSystem.polygonOffset(1, 0);
        if (percentage > 100) type = HeartType.ABSORPTION;
        DrawableHelper.drawTexture(matrices, x + 1, y + 1, type.getU(percentage < 50), 1, 7, 7, 256, 256);

        // Disable the depth test.
        RenderSystem.disableDepthTest();
    }
}
