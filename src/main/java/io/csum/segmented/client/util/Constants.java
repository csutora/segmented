package io.csum.segmented.client.util;


import java.awt.*;
import java.awt.geom.Point2D;

public class Constants {

    public static final Point2D.Float   CANVAS_SIZE = new Point2D.Float(256f, 64f);

    // x and y mean the position of these in the texture canvas
    public static final Point2D.Float   HOTBAR_POS = new Point2D.Float(0f, 0f);
    public static final Point2D.Float   SEGMENT_SELECTOR_POS = new Point2D.Float(0f, 22f);
    public static final Point2D.Float   VANILLA_SELECTOR_POS = new Point2D.Float(64f, 22f);
    public static final Point2D.Float   OFFHAND_SLOT_POS = new Point2D.Float(192f, 0f);

    /*
     * x and y are the offsets needed to get from the middle and bottom of the screen to the top left of the hotbar
     * the actual y value is offset by -1 so the hotbar is not cut off as it would be in vanilla
     */
    public static final Rectangle       HOTBAR = new Rectangle(-95, -23, 190, 22);
    public static final Rectangle       SEGMENT_SELECTOR = new Rectangle(64, 24);
    public static final Rectangle       VANILLA_SELECTOR = new Rectangle(24, 24);
    public static final Rectangle       OFFHAND_SLOT = new Rectangle(22, 22);

    // the size of a single slot
    public static final Rectangle       SLOT = new Rectangle(20, 20);

    // the x and y inset of an item sprite in a slot
    public static final int             ITEM_INSET = 3;

    // the gap in the hotbar
    public static final int             HOTBAR_GAP = 4;

    public static final int             SLOTS = 9;

}