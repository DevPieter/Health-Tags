package nl.devpieter.healthtags;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Keybindings {

    public static final KeyBinding TOGGLE = register("toggle", GLFW.GLFW_KEY_H);
    public static final KeyBinding OPEN_CONFIG_SCREEN = register("open_config_screen", GLFW.GLFW_KEY_C);

    private static KeyBinding register(String name, int code) {
        KeyBinding keyBinding = new KeyBinding("key.healthtags." + name, InputUtil.Type.KEYSYM, code, "category.healthtags");
        return KeyBindingHelper.registerKeyBinding(keyBinding);
    }

    public static void load() {
        // Some Java magic!
    }
}
