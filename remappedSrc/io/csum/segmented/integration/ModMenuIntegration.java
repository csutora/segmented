package io.csum.segmented.integration;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.csum.segmented.config.SegmentedConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(SegmentedConfig.class, parent).get();
    }
}
