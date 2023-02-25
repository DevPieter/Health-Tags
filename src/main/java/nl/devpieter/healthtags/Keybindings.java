package nl.devpieter.healthtags;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Keybindings {

    public static final KeyBinding TOGGLE = register("healthtags.toggle.key", GLFW.GLFW_KEY_H);
    public static final KeyBinding OPEN_CONFIG_SCREEN = register("healthtags.config.screen.key", GLFW.GLFW_KEY_C);
    public static final KeyBinding OPEN_RENDERER_CONFIG_SCREEN = register("healthtags.config.renderer_screen.key", GLFW.GLFW_KEY_R);

    private static KeyBinding register(String translationKey, int code) {
        KeyBinding keyBinding = new KeyBinding(translationKey, InputUtil.Type.KEYSYM, code, "healthtags.category");
        return KeyBindingHelper.registerKeyBinding(keyBinding);
    }

    public static void load() {
        // Some Java magic!
    }
}
