package nl.devpieter.healthtags.Enums;

import net.minecraft.text.Text;
import nl.devpieter.healthtags.Renderers.HeartTagRenderer;
import nl.devpieter.healthtags.Renderers.IHealthTagRenderer;
import nl.devpieter.healthtags.Renderers.PercentageTagRenderer;
import org.jetbrains.annotations.Nullable;

public enum HealthTagRenderer {
    HEART("heart", new HeartTagRenderer()),
    PERCENTAGE("percentage", new PercentageTagRenderer()),
    NONE("none", null);

    private final String translationKey;
    @Nullable
    private final IHealthTagRenderer renderer;

    HealthTagRenderer(String translationKey, @Nullable IHealthTagRenderer renderer) {
        this.translationKey = translationKey;
        this.renderer = renderer;
    }

    public Text getName() {
        //TODO: Add translations
        return Text.translatable("text.healthtags.renderer." + this.translationKey);
    }

    @Nullable
    public IHealthTagRenderer getRenderer() {
        return this.renderer;
    }
}
