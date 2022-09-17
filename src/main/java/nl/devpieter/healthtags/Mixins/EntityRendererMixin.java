package nl.devpieter.healthtags.Mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import nl.devpieter.healthtags.HealthTags;
import nl.devpieter.healthtags.HeartType;
import nl.devpieter.healthtags.TargetManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {

    @Shadow
    @Final
    protected EntityRenderDispatcher dispatcher;

    @Shadow
    protected abstract boolean hasLabel(T entity);

    private final TargetManager targetManager = HealthTags.TARGET_MANAGER;
    private final Identifier icons = new Identifier("textures/gui/icons.png");
    private final int rowsOf = 10, spacedBy = 9;

    @Inject(at = @At("HEAD"), method = "renderLabelIfPresent")
    public void onRenderLabel(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo callbackInfo) {
        if (!(entity instanceof PlayerEntity player)) return;
        if (!this.targetManager.isValid(player)) return;

        int y = this.hasLabel(entity) ? -10 : 0;
        int width = -calculateWidth(player, this.spacedBy) / 2;
        //int height = calculateHeight(player, spacedBy);

        matrices.push();
        matrices.translate(0.0, entity.getHeight() + 0.5F, 0.0);
        matrices.multiply(this.dispatcher.getRotation());
        matrices.scale(-0.025F, -0.025F, 0.025F);
        this.renderHealthBar(matrices, player, width, y, this.rowsOf, this.spacedBy);
        matrices.pop();
    }

    public int calculateWidth(LivingEntity entity, int spacedBy) {
        return MathHelper.ceil(entity.getMaxHealth() / 2.0) * spacedBy;
    }

//    public int calculateHeight(LivingEntity entity, int spacedBy) {
//        return (MathHelper.ceil((entity.getMaxHealth() + entity.getAbsorptionAmount()) / 2.0) / spacedBy) * spacedBy;
//    }

    private void renderHealthBar(MatrixStack matrices, LivingEntity entity, int x, int y, int rowsOf, int spacedBy) {
        int hearts = MathHelper.ceil(entity.getHealth() / 2.0);
        int containers = MathHelper.ceil(entity.getMaxHealth() / 2.0);
        boolean hasHalfHeart = (MathHelper.ceil(entity.getHealth()) % 2) != 0;

        int absorptions = MathHelper.ceil(entity.getAbsorptionAmount() / 2.0);
        boolean hasHalfAbsorption = (MathHelper.ceil(entity.getAbsorptionAmount()) % 2) != 0;

        RenderSystem.setShaderTexture(0, this.icons);

        RenderSystem.enableDepthTest();
        RenderSystem.enablePolygonOffset();

        RenderSystem.polygonOffset(1, 1);
        int extraHeight = this.drawContainers(matrices, x, y, containers, rowsOf, spacedBy);
        this.drawContainers(matrices, x, y + extraHeight, absorptions, rowsOf, spacedBy);

        RenderSystem.polygonOffset(0, 0);
        this.drawHearts(matrices, x, y, HeartType.fromState(entity), hearts, hasHalfHeart, rowsOf, spacedBy);
        this.drawHearts(matrices, x, y + extraHeight, HeartType.ABSORPTION, absorptions, hasHalfAbsorption, rowsOf, spacedBy);

        RenderSystem.disablePolygonOffset();
        RenderSystem.disableDepthTest();
    }

    private int drawContainers(MatrixStack matrices, int x, int y, int amount, int rowsOf, int spacedBy) {
        int width = 0, height = 0;

        for (int i = 0; i < amount; i++) {
            DrawableHelper.drawTexture(matrices, x + width, y + height, 16, 0, 9, 9, 256, 256);
            width += spacedBy;

            if (width < (spacedBy * rowsOf)) continue;
            width = 0;
            height -= spacedBy;
        }
        return height;
    }

    private void drawHearts(MatrixStack matrices, int x, int y, HeartType type, int amount, boolean hasHalf, int rowsOf, int spacedBy) {
        int width = 0, height = 0;

        for (int i = 0; i < amount; i++) {
            DrawableHelper.drawTexture(matrices, x + 1 + width, y + 1 + height, type.getU((i + 1) == amount && hasHalf), 1, 7, 7, 256, 256);
            width += spacedBy;

            if (width < (spacedBy * rowsOf)) continue;
            width = 0;
            height -= spacedBy;
        }
    }
}
