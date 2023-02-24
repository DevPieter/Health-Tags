package nl.devpieter.healthtags.Config.WidgetSetting;

import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.text.Text;
import nl.devpieter.healthtags.Config.Setting.Setting;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class EnumWidgetSetting<T extends Enum<T>> extends Setting<T> implements IWidgetSetting<CyclingButtonWidget<T>> {

    private Function<T, Text> valueToText;
    private T[] values;

    public EnumWidgetSetting(T value) {
        super(value);
    }

    @SafeVarargs
    public final void setValues(Function<T, Text> valueToText, T... values) {
        this.valueToText = valueToText;
        this.values = values;
    }

    @Override
    public @NotNull CyclingButtonWidget<T> getWidget(int x, int y, int width, int height) {
        return CyclingButtonWidget
                .builder(this.valueToText)
                .values(this.values)
                .initially(this.get())
                .omitKeyText()
                .build(x, y, width, height, Text.empty(), (button, value) -> this.set(value));
    }
}
