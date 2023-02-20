package nl.devpieter.healthtags.Mixins;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import nl.devpieter.healthtags.HealthTags;
import nl.devpieter.healthtags.Renderers.IHealthTagRenderer;
import nl.devpieter.healthtags.TargetManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixins extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    private final TargetManager targetManager = TargetManager.getInstance();

    public PlayerEntityRendererMixins(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(at = @At("TAIL"), method = "render(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    public void render(AbstractClientPlayerEntity player, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider consumerProvider, int light, CallbackInfo ci) {
        if (!this.targetManager.isTarget(player)) return;

        IHealthTagRenderer renderer = HealthTags.selectedRenderer.getRenderer();
        if (renderer == null) return;

        matrices.push();
        matrices.translate(0.0, player.getHeight() + 0.5F, 0.0);
        matrices.multiply(this.dispatcher.getRotation());
        matrices.scale(-0.025F, -0.025F, 0.025F);
        renderer.renderHealthTag(matrices, player, this.hasLabel(player), tickDelta, light);
        matrices.pop();
    }
}
