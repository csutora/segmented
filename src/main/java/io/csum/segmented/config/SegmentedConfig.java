package io.csum.segmented.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "segmented")
public class SegmentedConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    @Comment("Enables or disables the segmented hotbar")
    public boolean enabled = true;

//    this will be used for a mode i want to add later on,
//    when it's turned on the textures will be stitched together from the minecraft widgets texture,
//    allowing for easier resource pack compatibility. might not be possible to implement this, we'll see.
//    public boolean getTextureFromNormalHotbar = false;

}
