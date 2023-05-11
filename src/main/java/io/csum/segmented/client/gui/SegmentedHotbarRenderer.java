package io.csum.segmented.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import io.csum.segmented.SegmentedMod;
import io.csum.segmented.config.SegmentedConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameMode;

import java.awt.geom.Point2D;

import static io.csum.segmented.client.util.Constants.*;

@Environment(EnvType.CLIENT)
public class SegmentedHotbarRenderer extends DrawableHelper {
    private final Identifier WIDGETS_TEXTURE = new Identifier("segmented", "textures/gui/widgets.png");
    private final MinecraftClient client = MinecraftClient.getInstance();

    private final SegmentedConfig config = AutoConfig.getConfigHolder(SegmentedConfig.class).getConfig();

    public boolean renderSegmentedHotbar(MatrixStack matrices) {

        if (isHidden() || !config.enabled) { return false; }

        PlayerEntity player = (PlayerEntity)client.cameraEntity;
        if (player == null) { return false; }
        PlayerInventory inventory = player.getInventory();

        int scaledWidthHalved = client.getWindow().getScaledWidth() / 2;
        int scaledHeight = client.getWindow().getScaledHeight();
        Point2D.Float hotbar = new Point2D.Float(scaledWidthHalved + HOTBAR.x, scaledHeight + HOTBAR.y);

        RenderSystem.setShaderTexture(0, WIDGETS_TEXTURE);

        // draw the hotbar itself
        DrawableHelper.drawTexture(matrices,
                (int) hotbar.x,
                (int) hotbar.y,
                HOTBAR_POS.x,
                HOTBAR_POS.y,
                HOTBAR.width,
                HOTBAR.height,
                (int) CANVAS_SIZE.x,
                (int) CANVAS_SIZE.y);

        if (SegmentedMod.selectedHotbarSegment == -1) {
            // draw vanilla selection box
            DrawableHelper.drawTexture(matrices,
                    (int) hotbar.x - 1 + (inventory.selectedSlot * SLOT.width) + (HOTBAR_GAP * (inventory.selectedSlot / 3)),
                    (int) hotbar.y - 1,
                    VANILLA_SELECTOR_POS.x,
                    VANILLA_SELECTOR_POS.y,
                    VANILLA_SELECTOR.width,
                    VANILLA_SELECTOR.height,
                    (int) CANVAS_SIZE.x,
                    (int) CANVAS_SIZE.y);

        } else {
            // draw segment-wide selection box
            drawTexture(matrices,
                    (int) hotbar.x - 1 + (SEGMENT_SELECTOR.width * SegmentedMod.selectedHotbarSegment),
                    (int) hotbar.y - 1,
                    SEGMENT_SELECTOR_POS.x,
                    SEGMENT_SELECTOR_POS.y,
                    SEGMENT_SELECTOR.width,
                    SEGMENT_SELECTOR.height,
                    (int) CANVAS_SIZE.x,
                    (int) CANVAS_SIZE.y);
        }

        // render offhand on the proper side
        if (!player.getOffHandStack().isEmpty()) {
            int offHandX = (int) hotbar.x + HOTBAR.width + (HOTBAR_GAP-2);
            if (player.getMainArm().getOpposite() == Arm.LEFT) {
                offHandX = (int) hotbar.x - OFFHAND_SLOT.width - (HOTBAR_GAP - 2);
            }

            drawTexture(matrices,
                    offHandX,
                    (int) hotbar.y,
                    OFFHAND_SLOT_POS.x,
                    OFFHAND_SLOT_POS.y,
                    OFFHAND_SLOT.width,
                    OFFHAND_SLOT.height,
                    (int) CANVAS_SIZE.x,
                    (int) CANVAS_SIZE.y);

            // render offhand item
            ItemStack itemStack = player.getOffHandStack();
            client.getItemRenderer().renderInGuiWithOverrides(matrices, itemStack, offHandX + ITEM_INSET, (int) hotbar.y + ITEM_INSET);
            client.getItemRenderer().renderGuiItemOverlay(matrices, client.textRenderer, itemStack, offHandX + ITEM_INSET, (int) hotbar.y + ITEM_INSET);
        }

        // render items
        for (int currSlot = 0; currSlot < SLOTS; currSlot++) {
            int x = (int) hotbar.x + ITEM_INSET + (currSlot * SLOT.width) + (HOTBAR_GAP * (currSlot / 3));
            int y = (int) hotbar.y + ITEM_INSET;
            ItemStack itemStack = inventory.getStack(currSlot);
            client.getItemRenderer().renderInGuiWithOverrides(matrices, itemStack, x, y);
            client.getItemRenderer().renderGuiItemOverlay(matrices, client.textRenderer, itemStack, x, y);
        }


        // render attack indicator, this part is pretty much equivalent to the game's original code for this
        if (client.options.getAttackIndicator().getValue() == AttackIndicator.HOTBAR) {
            float cooldownProgress = player.getAttackCooldownProgress(0.0F);
            if (cooldownProgress < 1.0F) {
                int y = scaledHeight - 20;
                int x = scaledWidthHalved + 91 + 6; // these values come from the original code
                if (player.getMainArm() == Arm.LEFT) {
                    x = scaledWidthHalved - 91 - 22;
                }

                RenderSystem.setShaderTexture(0, DrawableHelper.GUI_ICONS_TEXTURE);
                int dy = (int)(cooldownProgress * 19.0F);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                drawTexture(matrices, x, y, 0, 94, 18, 18);
                drawTexture(matrices, x, y + 18 - dy, 18, 112 - dy, 18, dy);
            }
        }

        return true;
    }

    private boolean isHidden() {
        if (client.interactionManager == null
        || client.interactionManager.getCurrentGameMode() == GameMode.SPECTATOR
        || client.options.hudHidden)
            return true;

        PlayerEntity player = client.player;
        return player == null || !player.isAlive() || player.playerScreenHandler == null;
    }

}