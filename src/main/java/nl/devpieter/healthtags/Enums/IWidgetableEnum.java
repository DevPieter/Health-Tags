package nl.devpieter.healthtags.Enums;

import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.text.Text;

public interface IWidgetableEnum {
    Text getName();

    Tooltip getTooltip();
}
