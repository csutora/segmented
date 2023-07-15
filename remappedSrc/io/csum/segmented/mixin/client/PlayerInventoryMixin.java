package io.csum.segmented.mixin.client;

import io.csum.segmented.SegmentedMod;
import io.csum.segmented.config.SegmentedConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerInventory.class, priority = 9999)
@Environment(EnvType.CLIENT)
public abstract class PlayerInventoryMixin {

    @Inject(method = "scrollInHotbar",
            at = @At(value = "HEAD"))
    private void segmentedScrollInHotbar(double scrollAmount, CallbackInfo ci) {
        SegmentedConfig config = AutoConfig.getConfigHolder(SegmentedConfig.class).getConfig();
        if (config.enabled && config.scrollreset) { SegmentedMod.selectedHotbarSegment = -1; }
    }
}
