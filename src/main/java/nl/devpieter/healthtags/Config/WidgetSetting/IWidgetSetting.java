package nl.devpieter.healthtags.Config.WidgetSetting;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import org.jetbrains.annotations.NotNull;

public interface IWidgetSetting<W extends Drawable & Element & Selectable> {
    @NotNull W getWidget(int x, int y, int width, int height);
}
