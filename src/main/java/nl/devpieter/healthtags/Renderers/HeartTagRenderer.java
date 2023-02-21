package nl.devpieter.healthtags.Renderers;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import nl.devpieter.healthtags.Config.Config;
import nl.devpieter.healthtags.Enums.HeartType;

public class HeartTagRenderer implements IHealthTagRenderer {

    private final Identifier icons = new Identifier("textures/gui/icons.png");

    @Override
    public void renderHealthTag(MatrixStack matrices, PlayerEntity player, boolean hasLabel, float tickDelta, int light) {
        // Get the config values
        Config config = Config.getInstance();
        int extraHeight = config.ExtraHeight.value();
        int rowsOfHearts = config.RowsOfHearts.value();
        int heartsSpacedBy_X = config.HeartsSpacedBy_X.value();
        int heartsSpacedBy_Y = config.HeartsSpacedBy_Y.value();

        // Get the x position
        float maxHealth = MathHelper.clamp(player.getMaxHealth(), 0, rowsOfHearts * 2);
        int x = -(MathHelper.ceil(maxHealth / 2.0) * heartsSpacedBy_X) / 2;

        // Get the y position
        int y = (hasLabel ? -10 : 0) - extraHeight;

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
        int rows = MathHelper.ceil(containers / (float) rowsOfHearts);
        int absorptionHeight = -(rows * heartsSpacedBy_Y);

        // Draw the containers
        RenderSystem.polygonOffset(1, 1);
        this.drawContainers(matrices, x, y, containers);
        this.drawContainers(matrices, x, y + absorptionHeight, absorptions);

        // Draw the hearts
        RenderSystem.polygonOffset(0, 0);
        this.drawHearts(matrices, x, y, HeartType.fromState(player), hearts, isLastHalf);
        this.drawHearts(matrices, x, y + absorptionHeight, HeartType.ABSORPTION, absorptions, isLastAbsorptionHalf);

        // Disable the depth test and polygon offset
        RenderSystem.disableDepthTest();
        RenderSystem.disablePolygonOffset();
    }

    private void drawContainers(MatrixStack matrices, int x, int y, int amount) {
        // Get the config values
        Config config = Config.getInstance();
        int rowsOfHearts = config.RowsOfHearts.value();
        int heartsSpacedBy_X = config.HeartsSpacedBy_X.value();
        int heartsSpacedBy_Y = config.HeartsSpacedBy_Y.value();

        // Set the width and height
        int width = 0, height = 0;

        // Draw the containers and increase the width and height
        for (int i = 0; i < amount; i++) {
            DrawableHelper.drawTexture(matrices, x + width, y + height, 16, 0, 9, 9, 256, 256);
            width += heartsSpacedBy_X;

            if (width < (heartsSpacedBy_X * rowsOfHearts)) continue;
            width = 0;
            height -= heartsSpacedBy_Y;
        }
    }

    private void drawHearts(MatrixStack matrices, int x, int y, HeartType type, int amount, boolean isLastHalf) {
        // Get the config values
        Config config = Config.getInstance();
        int rowsOfHearts = config.RowsOfHearts.value();
        int heartsSpacedBy_X = config.HeartsSpacedBy_X.value();
        int heartsSpacedBy_Y = config.HeartsSpacedBy_Y.value();

        // Set the width and height
        int width = 0, height = 0;

        // Draw the hearts and increase the width and height
        for (int i = 0; i < amount; i++) {
            boolean isHalf = (i + 1) == amount && isLastHalf;
            DrawableHelper.drawTexture(matrices, x + 1 + width, y + 1 + height, type.getU(isHalf), 1, 7, 7, 256, 256);
            width += heartsSpacedBy_X;

            if (width < (heartsSpacedBy_X * rowsOfHearts)) continue;
            width = 0;
            height -= heartsSpacedBy_Y;
        }
    }
}
