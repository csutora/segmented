package io.csum.segmented;

import io.csum.segmented.config.SegmentedConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class SegmentedMod implements ModInitializer {
	//public static final String MOD_ID = "segmented";
	//public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static int selectedHotbarSegment = -1;

	@Override
	public void onInitialize() {
		AutoConfig.register(SegmentedConfig.class, JanksonConfigSerializer::new);
	}
}
