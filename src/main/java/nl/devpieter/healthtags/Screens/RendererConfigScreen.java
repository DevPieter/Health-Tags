package nl.devpieter.healthtags.Screens;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import nl.devpieter.healthtags.Config.Setting.Setting;
import nl.devpieter.healthtags.Config.WidgetSetting.SliderWidgetSetting;
import nl.devpieter.healthtags.Enums.HealthTagRenderer;
import nl.devpieter.healthtags.Renderers.IHealthTagRenderer;

import java.text.DecimalFormat;
import java.util.List;

public class RendererConfigScreen extends BaseScreen {

    private final DecimalFormat wholeNumberFormat = new DecimalFormat("#");
    private final HealthTagRenderer renderer;

    public RendererConfigScreen(HealthTagRenderer renderer) {
        // TODO: Move to translation file
        super(Text.translatable("Configuring '%s'", renderer.getName()));
        this.renderer = renderer;
    }

    @Override
    protected void init() {
        super.init();

        // TODO: Translation
        this.addDrawableChild(ButtonWidget.builder(Text.of("Back"), button -> {
            if (this.client == null) return;
            this.client.setScreen(new ConfigScreen());
        }).dimensions(this.widgetLeft, this.bottom - 30, this.widgetWidth, 20).build());

        IHealthTagRenderer tagRenderer = this.renderer.getRenderer();
        if (tagRenderer == null) return;

        List<Setting<?>> settings = this.renderer.getRenderer().getSettings();
        for (int i = 0; i < settings.size(); i++) {
            Setting<?> setting = settings.get(i);

            if (!(setting instanceof SliderWidgetSetting sliderSetting)) continue;
            this.addDrawableChild(sliderSetting.getWidget(widgetLeft, 40 + (i * 30), widgetWidth, 20)).setFormat(this.wholeNumberFormat);
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);


    }

    @Override
    public void close() {
        if (this.client == null) return;
        this.client.setScreen(new ConfigScreen());
    }
}
