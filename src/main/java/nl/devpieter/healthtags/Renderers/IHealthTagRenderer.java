package nl.devpieter.healthtags.Renderers;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import nl.devpieter.healthtags.Config.Setting.Setting;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IHealthTagRenderer {

    default @NotNull List<Setting<?>> getSettings() {
        return List.of();
    }

    void renderHealthTag(MatrixStack matrices, PlayerEntity player, boolean hasLabel, float tickDelta, int light);
}
