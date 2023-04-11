package nl.devpieter.healthtags;

import com.mojang.logging.LogUtils;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

public class Keybindings {

    private static final Logger LOGGER = LogUtils.getLogger();

    public static final KeyBinding TOGGLE = register("healthtags.toggle.key", GLFW.GLFW_KEY_H);
    public static final KeyBinding OPEN_CONFIG_SCREEN = register("healthtags.config.screen.key", GLFW.GLFW_KEY_C);
    public static final KeyBinding OPEN_RENDERER_CONFIG_SCREEN = register("healthtags.config.renderer_screen.key", GLFW.GLFW_KEY_R);

    /***
     * Registers a keybinding.
     * @param translationKey The translation key of the keybinding.
     * @param code The key code of the keybinding.
     * @return The registered keybinding.
     */
    private static @NotNull KeyBinding register(@NotNull String translationKey, int code) {
        LOGGER.debug("Registering a keybinding with the translation key '{}' and the key code '{}'", translationKey, code);

        KeyBinding keyBinding = new KeyBinding(translationKey, InputUtil.Type.KEYSYM, code, "healthtags.category");
        return KeyBindingHelper.registerKeyBinding(keyBinding);
    }

    /***
     * Loads the keybindings with some Java magic.
     */
    public static void load() {
        // Some Java magic!
        LOGGER.debug("Loading the keybindings with some Java magic!");
    }
}
