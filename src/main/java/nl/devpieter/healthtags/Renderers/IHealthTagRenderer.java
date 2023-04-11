package nl.devpieter.healthtags.Renderers;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import nl.devpieter.healthtags.Config.Setting.Setting;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IHealthTagRenderer {

    /***
     * Gets the settings of this renderer.
     * @return The settings of this renderer.
     */
    default @NotNull List<Setting<?>> getSettings() {
        return List.of();
    }

    /***
     * Renders the health tag for the given player.
     * @param matrices The matrix stack to render with.
     * @param player The player to render the health tag for.
     * @param hasLabel Whether the player has a label.
     * @param tickDelta The tick delta.
     * @param light The light level.
     */
    void renderHealthTag(@NotNull MatrixStack matrices, @NotNull PlayerEntity player, boolean hasLabel, float tickDelta, int light);
}
