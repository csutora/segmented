package io.csum.segmented.mixin.client;

import io.csum.segmented.client.control.SegmentedKeyHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = MinecraftClient.class, priority = 9999)
@Environment(EnvType.CLIENT)
public abstract class MinecraftClientMixin {

    @Shadow public ClientPlayerEntity player;
    @Shadow @Final public GameOptions options;
    @Shadow @Nullable
    public Screen currentScreen;

    @Redirect(method = "handleInputEvents",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/option/KeyBinding;wasPressed()Z",
            ordinal = 2))
    private boolean segmentedHandleHotbarSlotSelection(KeyBinding keyBinding) {

        if (!keyBinding.wasPressed()) { return false; }
        if (player.isSpectator()) { return false; }

        if (!player.isCreative() || currentScreen != null || (!options.saveToolbarActivatorKey.isPressed() && !options.loadToolbarActivatorKey.isPressed())) {
            SegmentedKeyHandler segmentedKeyHandler = new SegmentedKeyHandler();
            for (int i = 0; i < 9; i++) {
                if (keyBinding == options.hotbarKeys[i])
                    return !segmentedKeyHandler.handleSegmentedHotbarSlotSelection(player.getInventory(), i);
            }
        }
        return true;
    }
}
