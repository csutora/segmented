package io.csum.segmented.client.util;


import java.awt.*;
import java.awt.geom.Point2D;

public class Constants {

    public static final Rectangle   TEXTURE_SIZE = new Rectangle(256, 128);

    // coordinates of the top left corner of- and sizes of the bounding boxes in the texture
    public static final Rectangle   HOTBAR_BOUNDING_BOX = new Rectangle(3, 3, 194, 26);
    public static final Rectangle   SLOT_SELECTOR = new Rectangle(3, 67, 26, 26);
    public static final Rectangle   SEGMENT_SELECTOR = new Rectangle(3, 35, 66, 26);
    public static final Rectangle   OFFHAND_SLOT = new Rectangle(198, 3, 26, 26);

    public static final Point2D.Float   HOTBAR_OFFSET_FROM_BOTTOM_CENTER = new Point2D.Float(-97f, -26f);

    // the inset of an item sprite in a slot
    public static final int   ITEM_INSET = 5;

    // segment and slot offset, hotbar gap size on actual hud
    public static final int   SEGMENT_OFFSET = 64;
    public static final int   SLOT_OFFSET = 20;
    public static final int   HOTBAR_GAP = 4;

    public static final int   SLOT_COUNT = 9;

}