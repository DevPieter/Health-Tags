package nl.devpieter.healthtags.Enums;

import net.minecraft.entity.Entity;

public enum HeartType {
    NORMAL(53), ABSORPTION(161), FROZEN(179);

    private final int u;

    HeartType(int u) {
        this.u = u;
    }

    public int getU(boolean halfHeart) {
        return this.u + (halfHeart ? 9 : 0);
    }

    public static HeartType fromState(Entity entity) {
        return entity.isFrozen() ? FROZEN : NORMAL;
    }
}
