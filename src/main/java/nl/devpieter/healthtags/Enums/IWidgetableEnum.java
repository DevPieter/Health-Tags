package nl.devpieter.healthtags.Enums;

import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.text.Text;

public interface IWidgetableEnum {

    /***
     * Gets the name of this enum value.
     * @return The name of this enum value.
     */
    Text getName();

    /***
     * Gets the tooltip of this enum value.
     * @return The tooltip of this enum value.
     */
    Tooltip getTooltip();
}
