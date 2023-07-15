package io.csum.segmented.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import io.csum.segmented.SegmentedMod;
import io.csum.segmented.config.SegmentedConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import java.awt.geom.Point2D;

import static io.csum.segmented.client.util.Constants.*;

@Environment(EnvType.CLIENT)
public class SegmentedHotbarRenderer extends DrawableHelper {
    private final Identifier WIDGETS_TEXTURE = new Identifier("segmented", "textures/gui/widgets.png");
    private final MinecraftClient client = MinecraftClient.getInstance();

    private final SegmentedConfig config = AutoConfig.getConfigHolder(SegmentedConfig.class).getConfig();

    public boolean renderSegmentedHotbar(MatrixStack matrices) {

        if (isHidden() || !config.enabled || config.onlyselector) { return false; }

        PlayerEntity player = (PlayerEntity)client.cameraEntity;
        if (player == null) { return false; }
        PlayerInventory inventory = player.getInventory();

        // integration with raised
        if (FabricLoader.getInstance().getObjectShare().get("raised:hud") instanceof Integer hud) {
            SegmentedMod.raiseAmount = hud - 2;
        }

        int scaledWidthHalved = client.getWindow().getScaledWidth() / 2;
        int scaledHeight = client.getWindow().getScaledHeight();

        Point2D.Float hotbar = new Point2D.Float(scaledWidthHalved + HOTBAR_OFFSET_FROM_BOTTOM_CENTER.x,
                scaledHeight + HOTBAR_OFFSET_FROM_BOTTOM_CENTER.y - SegmentedMod.raiseAmount);

        RenderSystem.setShaderTexture(0, WIDGETS_TEXTURE);

        // draw the hotbar itself
        DrawableHelper.drawTexture(matrices,
                (int) hotbar.x,
                (int) hotbar.y,
                (float) HOTBAR_BOUNDING_BOX.x,
                (float) HOTBAR_BOUNDING_BOX.y,
                HOTBAR_BOUNDING_BOX.width,
                HOTBAR_BOUNDING_BOX.height,
                TEXTURE_SIZE.width,
                TEXTURE_SIZE.height);

        if (SegmentedMod.selectedHotbarSegment == -1) {
            // draw slot selection box
            DrawableHelper.drawTexture(matrices,
                    (int) hotbar.x + (inventory.selectedSlot * SLOT_OFFSET) + (HOTBAR_GAP * (inventory.selectedSlot / 3)),
                    (int) hotbar.y,
                    (float) SLOT_SELECTOR.x + (inventory.selectedSlot * (SLOT_SELECTOR.width + 1)),
                    (float) SLOT_SELECTOR.y,
                    SLOT_SELECTOR.width,
                    SLOT_SELECTOR.height,
                    TEXTURE_SIZE.width,
                    TEXTURE_SIZE.height);

        } else {
            // draw segment selection box
            drawTexture(matrices,
                    (int) hotbar.x + (SegmentedMod.selectedHotbarSegment * SEGMENT_OFFSET),
                    (int) hotbar.y,
                    (float) SEGMENT_SELECTOR.x + (SegmentedMod.selectedHotbarSegment * (SEGMENT_SELECTOR.width + 1)),
                    (float) SEGMENT_SELECTOR.y,
                    SEGMENT_SELECTOR.width,
                    SEGMENT_SELECTOR.height,
                    TEXTURE_SIZE.width,
                    TEXTURE_SIZE.height);
        }

        // render offhand on the proper side
        if (!player.getOffHandStack().isEmpty()) {
            int offHandX = (int) hotbar.x + HOTBAR_BOUNDING_BOX.width - (HOTBAR_GAP - 2);
            if (player.getMainArm().getOpposite() == Arm.LEFT) {
                offHandX = (int) hotbar.x - OFFHAND_SLOT.width + (HOTBAR_GAP - 2);
            }

            drawTexture(matrices,
                    offHandX,
                    (int) hotbar.y,
                    (float) OFFHAND_SLOT.x,
                    (float) OFFHAND_SLOT.y,
                    OFFHAND_SLOT.width,
                    OFFHAND_SLOT.height,
                    TEXTURE_SIZE.width,
                    TEXTURE_SIZE.height);

            // render offhand item
            ItemStack itemStack = player.getOffHandStack();
            client.getItemRenderer().renderInGuiWithOverrides(itemStack, offHandX + ITEM_INSET, (int) hotbar.y + ITEM_INSET);
            client.getItemRenderer().renderGuiItemOverlay(client.textRenderer, itemStack, offHandX + ITEM_INSET, (int) hotbar.y + ITEM_INSET);
        }

        // render items
        for (int currSlot = 0; currSlot < SLOT_COUNT; currSlot++) {
            int x = (int) hotbar.x + ITEM_INSET + (currSlot * SLOT_OFFSET) + (HOTBAR_GAP * (currSlot / 3));
            int y = (int) hotbar.y + ITEM_INSET;
            ItemStack itemStack = inventory.getStack(currSlot);
            client.getItemRenderer().renderInGuiWithOverrides(itemStack, x, y);
            client.getItemRenderer().renderGuiItemOverlay(client.textRenderer, itemStack, x, y);
        }


        // render attack indicator, this part is pretty much equivalent to the game's original code for this except x,y
        if (client.options.getAttackIndicator().getValue() == AttackIndicator.HOTBAR) {
            float cooldownProgress = player.getAttackCooldownProgress(0.0F);
            if (cooldownProgress < 1.0F) {
                int y = (int) hotbar.y + (HOTBAR_BOUNDING_BOX.height - 18)/2;
                int x = (int) hotbar.x + HOTBAR_BOUNDING_BOX.width + 2;
                if (player.getMainArm() == Arm.LEFT) {
                    x = (int) hotbar.x - 2 - 18;
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

    public boolean isHidden() {
        if (client.interactionManager == null || client.options.hudHidden) { return true; }

        PlayerEntity player = client.player;
        return (player == null || !player.isAlive() || player.playerScreenHandler == null);
    }

}