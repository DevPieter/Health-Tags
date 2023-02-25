package nl.devpieter.healthtags.Config.WidgetSetting;

import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.text.Text;
import nl.devpieter.healthtags.Config.Setting.Setting;
import nl.devpieter.healthtags.Enums.IWidgetableEnum;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class EnumWidgetSetting<T extends Enum<T> & IWidgetableEnum> extends Setting<T> implements IWidgetSetting<CyclingButtonWidget<T>> {

    public EnumWidgetSetting(T value) {
        super(value);
    }

    @Override
    public @NotNull CyclingButtonWidget<T> getWidget(int x, int y, int width, int height) {
        return CyclingButtonWidget
                .builder((Function<T, Text>) IWidgetableEnum::getName)
                .tooltip(IWidgetableEnum::getTooltip)
                .values(this.get().getDeclaringClass().getEnumConstants())
                .initially(this.get())
                .omitKeyText()
                .build(x, y, width, height, Text.empty(), (button, value) -> this.set(value));
    }
}
