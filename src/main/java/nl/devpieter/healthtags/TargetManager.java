package nl.devpieter.healthtags;

import net.minecraft.entity.player.PlayerEntity;
import nl.devpieter.healthtags.Config.Config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class TargetManager {

    /* INSTANCE */
    private static final TargetManager INSTANCE = new TargetManager();

    public static TargetManager getInstance() {
        return INSTANCE;
    }

    private final LinkedHashMap<PlayerEntity, Long> targets = new LinkedHashMap<>();
    private final Config config = Config.getInstance();

    public void addTarget(PlayerEntity player) {
        if (this.targets.containsKey(player)) this.targets.replace(player, System.currentTimeMillis());
        else this.targets.put(player, System.currentTimeMillis());
    }

    public void removeTarget(PlayerEntity player) {
        this.targets.remove(player);
    }

    public List<PlayerEntity> getTargets() {
        return new ArrayList<>(this.targets.keySet());
    }

    public PlayerEntity getLatestTarget() {
        return this.targets.keySet().stream().findFirst().orElse(null);
    }

    public boolean isTarget(PlayerEntity player) {
        return this.targets.containsKey(player);
    }

    public void update() {
        int targetHoldTime = this.config.TargetHoldTime.get();
        this.targets.entrySet().removeIf(entry -> System.currentTimeMillis() - entry.getValue() > targetHoldTime);
    }
}
