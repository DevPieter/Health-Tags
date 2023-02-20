package nl.devpieter.healthtags.Mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import nl.devpieter.healthtags.TargetManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Shadow
    @Final
    protected MinecraftClient client;
    private final TargetManager targetManager = TargetManager.getInstance();

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci) {
        this.targetManager.update();

        if (!(this.client.targetedEntity instanceof PlayerEntity player)) return;
        this.targetManager.addTarget(player);
    }
}
