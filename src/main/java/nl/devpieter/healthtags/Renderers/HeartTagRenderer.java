package nl.devpieter.healthtags.Renderers;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import nl.devpieter.healthtags.Enums.HeartType;
import nl.devpieter.healthtags.HealthTags;

public class HeartTagRenderer implements IHealthTagRenderer {

    private final Identifier icons = new Identifier("textures/gui/icons.png");

    @Override
    public void renderHealthTag(MatrixStack matrices, PlayerEntity player, boolean hasLabel, float tickDelta, int light) {
        // Get the x position
        float maxHealth = MathHelper.clamp(player.getMaxHealth(), 0, HealthTags.rowsOf * 2);
        int x = -(MathHelper.ceil(maxHealth / 2.0) * HealthTags.spacedBy) / 2;

        // Get the y position
        int y = (hasLabel ? -10 : 0) - HealthTags.extraHeight;

        // Get the amount of hearts and containers
        int hearts = MathHelper.ceil(player.getHealth() / 2.0);
        int containers = MathHelper.ceil(player.getMaxHealth() / 2.0);
        boolean isLastHalf = MathHelper.ceil(player.getHealth() % 2) != 0;

        // Get the amount of absorption hearts
        int absorptions = MathHelper.ceil(player.getAbsorptionAmount() / 2.0);
        boolean isLastAbsorptionHalf = MathHelper.ceil(player.getAbsorptionAmount() % 2) != 0;

        // Set the shader texture, enable the depth test and polygon offset
        RenderSystem.setShaderTexture(0, this.icons);
        RenderSystem.enableDepthTest();
        RenderSystem.enablePolygonOffset();

        // Calculate the extra height for the absorption hearts
        int rows = MathHelper.ceil(containers / (float) HealthTags.rowsOf);
        int extraHeight = -(rows * HealthTags.spacedBy);

        // Draw the containers
        RenderSystem.polygonOffset(1, 1);
        this.drawContainers(matrices, x, y, containers);
        this.drawContainers(matrices, x, y + extraHeight, absorptions);

        // Draw the hearts
        RenderSystem.polygonOffset(0, 0);
        this.drawHearts(matrices, x, y, HeartType.fromState(player), hearts, isLastHalf);
        this.drawHearts(matrices, x, y + extraHeight, HeartType.ABSORPTION, absorptions, isLastAbsorptionHalf);

        // Disable the depth test and polygon offset
        RenderSystem.disableDepthTest();
        RenderSystem.disablePolygonOffset();
    }

    private void drawContainers(MatrixStack matrices, int x, int y, int amount) {
        int width = 0, height = 0;

        for (int i = 0; i < amount; i++) {
            DrawableHelper.drawTexture(matrices, x + width, y + height, 16, 0, 9, 9, 256, 256);
            width += HealthTags.spacedBy;

            if (width < (HealthTags.spacedBy * HealthTags.rowsOf)) continue;
            width = 0;
            height -= HealthTags.spacedBy;
        }
    }

    private void drawHearts(MatrixStack matrices, int x, int y, HeartType type, int amount, boolean isLastHalf) {
        int width = 0, height = 0;

        for (int i = 0; i < amount; i++) {
            boolean isHalf = (i + 1) == amount && isLastHalf;
            DrawableHelper.drawTexture(matrices, x + 1 + width, y + 1 + height, type.getU(isHalf), 1, 7, 7, 256, 256);
            width += HealthTags.spacedBy;

            if (width < (HealthTags.spacedBy * HealthTags.rowsOf)) continue;
            width = 0;
            height -= HealthTags.spacedBy;
        }
    }
}
