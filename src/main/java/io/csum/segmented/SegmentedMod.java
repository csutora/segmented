package io.csum.segmented;

import io.csum.segmented.config.SegmentedConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class SegmentedMod implements ModInitializer {
	public static int selectedHotbarSegment = -1;
	public static float cancelTimer = 0;

	@Override
	public void onInitialize() {
		AutoConfig.register(SegmentedConfig.class, JanksonConfigSerializer::new);
	}
}
