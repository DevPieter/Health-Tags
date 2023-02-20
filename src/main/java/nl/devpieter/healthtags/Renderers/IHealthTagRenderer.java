package nl.devpieter.healthtags.Renderers;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;

public interface IHealthTagRenderer {
    void renderHealthTag(MatrixStack matrices, PlayerEntity player, boolean hasLabel, float tickDelta, int light);
}
