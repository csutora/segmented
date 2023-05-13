package io.csum.segmented.mixin.client;

import io.csum.segmented.SegmentedMod;
import io.csum.segmented.config.SegmentedConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Mouse.class, priority = 9999)
@Environment(EnvType.CLIENT)
public abstract class MouseMixin {

    // clears segment selection on item use
    @Inject(method = "onMouseButton",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/option/KeyBinding;onKeyPressed(Lnet/minecraft/client/util/InputUtil$Key;)V"))
    private void segmentedOnKeyPressed(long window, int button, int action, int mods, CallbackInfo ci) {

        SegmentedConfig config = AutoConfig.getConfigHolder(SegmentedConfig.class).getConfig();
        if (config.enabled && config.usereset) { SegmentedMod.selectedHotbarSegment = -1; }

    }
}
