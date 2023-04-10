package nl.devpieter.healthtags.Enums;

import net.minecraft.entity.Entity;

public enum HeartType {
    NORMAL(53), ABSORPTION(161), FROZEN(179);

    private final int u;

    HeartType(int u) {
        this.u = u;
    }

    /***
     * Gets the u position of the heart texture.
     * @param halfHeart Whether the heart is half a heart.
     * @return The u position of the heart texture.
     */
    public int getU(boolean halfHeart) {
        return this.u + (halfHeart ? 9 : 0);
    }

    /***
     * Gets the heart type of the given entity.
     * @param entity The entity to get the heart type of.
     * @return The heart type of the given entity.
     */
    public static HeartType fromState(Entity entity) {
        return entity.isFrozen() ? FROZEN : NORMAL;
    }
}
