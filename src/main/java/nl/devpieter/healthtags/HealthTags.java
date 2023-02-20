package nl.devpieter.healthtags;

import net.fabricmc.api.ModInitializer;
import nl.devpieter.healthtags.Enums.HealthTagRenderer;

public class HealthTags implements ModInitializer {

    public static HealthTagRenderer selectedRenderer = HealthTagRenderer.HEART;
    public static int rowsOf = 10, spacedBy = 9, extraHeight = 30, holdTime = 5000;


    @Override
    public void onInitialize() {
    }
}
