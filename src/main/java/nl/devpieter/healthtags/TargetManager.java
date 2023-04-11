package nl.devpieter.healthtags;

import net.minecraft.entity.player.PlayerEntity;
import nl.devpieter.healthtags.Config.Config;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;

public class TargetManager {

    /* INSTANCE */
    private static final @NotNull TargetManager INSTANCE = new TargetManager();

    /***
     * Gets the instance of the TargetManager.
     * @return The instance of the TargetManager.
     */
    public static @NotNull TargetManager getInstance() {
        return INSTANCE;
    }

    private final LinkedHashMap<PlayerEntity, Long> targets = new LinkedHashMap<>();
    private final Config config = Config.getInstance();

    /***
     * Adds a target to the list of targets.
     * @param player The player to add as a target.
     */
    public void addTarget(@NotNull PlayerEntity player) {
        if (this.targets.containsKey(player)) this.targets.replace(player, System.currentTimeMillis());
        else this.targets.put(player, System.currentTimeMillis());
    }

//    /***
//     * Removes a target from the list of targets.
//     * @param player The player to remove as a target.
//     */
//    public void removeTarget(PlayerEntity player) {
//        this.targets.remove(player);
//    }

//    /***
//     * Gets a list of all targets.
//     * @return A list of all targets.
//     */
//    public List<PlayerEntity> getTargets() {
//        return new ArrayList<>(this.targets.keySet());
//    }

//    /***
//     * Gets the latest target.
//     * @return The latest target.
//     */
//    public PlayerEntity getLatestTarget() {
//        return this.targets.keySet().stream().findFirst().orElse(null);
//    }

    /***
     * Checks if a player is a target.
     * @param player The player to check.
     * @return True if the player is a target, false otherwise.
     */
    public boolean isTarget(@NotNull PlayerEntity player) {
        return this.targets.containsKey(player);
    }

    /***
     * Updates the target manager.
     * Remove targets that have been held for too long.
     */
    public void update() {
        int targetHoldTime = this.config.TargetHoldTime.get() * 1000;
        this.targets.entrySet().removeIf(entry -> System.currentTimeMillis() - entry.getValue() > targetHoldTime);
    }
}
