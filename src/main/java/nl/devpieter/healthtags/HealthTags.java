package nl.devpieter.healthtags;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import nl.devpieter.healthtags.Config.Config;
import nl.devpieter.healthtags.Screens.ConfigScreen;

public class HealthTags implements ModInitializer {

    private final Config config = Config.getInstance();

    @Override
    public void onInitialize() {
        this.config.save();
        Keybindings.load();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (Keybindings.TOGGLE.wasPressed()) this.config.Enabled.toggle();
            if (Keybindings.OPEN_CONFIG_SCREEN.wasPressed()) client.setScreen(new ConfigScreen());
        });
    }
}
