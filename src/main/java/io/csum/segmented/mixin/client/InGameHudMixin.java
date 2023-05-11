package io.csum.segmented.mixin.client;

import io.csum.segmented.client.gui.SegmentedHotbarRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
@Environment(EnvType.CLIENT)
public abstract class InGameHudMixin extends DrawableHelper {

    @Inject(method = "renderHotbar", at = @At(value = "HEAD"), cancellable = true, require = 0)
    private void renderSegmentedHotbar(float tickDelta, MatrixStack matrices, CallbackInfo ci) {
        SegmentedHotbarRenderer segmentedHotbarRenderer = new SegmentedHotbarRenderer();
        if (segmentedHotbarRenderer.renderSegmentedHotbar(matrices)) {
            ci.cancel();
        }
    }

}


