package nl.devpieter.healthtags;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;

public class TargetManager {

    private final MinecraftClient client = MinecraftClient.getInstance();

    private final HashMap<PlayerEntity, Long> targetedPlayers = new HashMap<>();
    private final long isValidFor = 5 * 1000;

    public void tick() {
        this.targetedPlayers.entrySet().removeIf(entry -> entry.getValue() < System.currentTimeMillis());

        if (!(client.targetedEntity instanceof PlayerEntity targetedPlayer)) return;
        if (this.targetedPlayers.containsKey(targetedPlayer)) this.targetedPlayers.replace(targetedPlayer, System.currentTimeMillis() + this.isValidFor);
        else this.targetedPlayers.put(targetedPlayer, System.currentTimeMillis() + this.isValidFor);
    }

    public boolean isValid(PlayerEntity player) {
        if (!this.targetedPlayers.containsKey(player)) return false;
        return this.targetedPlayers.get(player) > System.currentTimeMillis();
    }
}
