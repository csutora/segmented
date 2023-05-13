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

    @ConfigEntry.Gui.Tooltip
    @Comment("If true, disables the custom hotbar but keeps the selection mechanics")
    public boolean norender = false;

    @ConfigEntry.Gui.Tooltip
    @Comment("Enables or disables the mechanic where scrolling clears the segment selection")
    public boolean scrollreset = true;

    @ConfigEntry.Gui.Tooltip
    @Comment("Enables or disables the mechanic where using an item clears the segment selection")
    public boolean usereset = true;

    @ConfigEntry.Gui.Tooltip
    @Comment("Sets the time in seconds after which the segment selection gets cleared, 0 disables this feature")
    public float timerreset = 5;

//    this will be used for a mode i want to add later on,
//    when it's turned on the textures will be stitched together from the minecraft widgets texture,
//    allowing for easier resource pack compatibility. might not be possible to implement this, we'll see.
//    public boolean getTextureFromNormalHotbar = false;

}
