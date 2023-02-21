package nl.devpieter.healthtags;

import net.fabricmc.api.ModInitializer;
import nl.devpieter.healthtags.Config.Config;

public class HealthTags implements ModInitializer {

    @Override
    public void onInitialize() {
        Config.getInstance().save();
    }
}
