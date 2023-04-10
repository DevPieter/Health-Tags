package nl.devpieter.healthtags.Config.WidgetSetting;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import org.jetbrains.annotations.NotNull;

public interface IWidgetSetting<W extends Drawable & Element & Selectable> {

    /***
     * Gets the widget of this setting.
     * @param x The x position of the widget.
     * @param y The y position of the widget.
     * @param width The width of the widget.
     * @param height The height of the widget.
     * @return The widget of this setting.
     */
    @NotNull W getWidget(int x, int y, int width, int height);
}
