# segmented


A mod that splits your hotbar into 3 segments, and removes the need to move your hand across the atlantic ocean every time you want to hotkey to later slots. <br>
The way it works is instead of using keys 1-9 to select a slot, you first select a segment using 1-3, and then select a slot in that segment using 1-3 again. This makes it possible to quickly navigate the hotbar without ever moving your hand.

Supports custom, and if you'd like, even different textures for each segment, slot and selector. Finally! Your custom 3D hotbar can have a selector that works well with it! 

Segmented is available for Fabric only. No Forge support is planned, and please don't ask for it or else I'll have to [steal your kneecaps](https://www.youtube.com/watch?v=dQw4w9WgXcQ). <br>

*segmented is heavily inspired by a feature of [Inventorio](https://curseforge.com/minecraft/mc-mods/inventorio), go check it out! (yes, now)*

### Dependencies
- [Fabric API](https://github.com/FabricMC/fabric) - required
- [Cloth Config](https://github.com/shedaniel/ClothConfig/) - required
- [ModMenu](https://github.com/TerraformersMC/ModMenu) - optional, lets you configure the mod at runtime.

### Mod compat
 - [Auto HUD](https://github.com/Crendgrim/AutoHUD) - reveals the hotbar on segment change, and the default selection cancel timer is just shorter than Auto HUD's hide, so it's really satisfying.
 - [Raised](https://github.com/yurisuika/Raised) - works perfectly through their ObjectShare feature.
