package io.csum.segmented.client.control;

import io.csum.segmented.SegmentedMod;
import io.csum.segmented.config.SegmentedConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.entity.player.PlayerInventory;

public class SegmentedKeyHandler {

    private final SegmentedConfig config = AutoConfig.getConfigHolder(SegmentedConfig.class).getConfig();
    public boolean handleSegmentedHotbarSlotSelection(PlayerInventory inventory, int slot) {

        if (!config.enabled) { return false; }

        if (slot > 2) { return true; }
        if (SegmentedMod.selectedHotbarSegment == -1) {
            SegmentedMod.selectedHotbarSegment = slot;
            SegmentedMod.cancelTimer = config.timerreset * 20;
        } else {
            inventory.selectedSlot = slot + 3 * SegmentedMod.selectedHotbarSegment;
            SegmentedMod.selectedHotbarSegment = -1;
            SegmentedMod.cancelTimer = 0;
        }
        return true;
    }
}
