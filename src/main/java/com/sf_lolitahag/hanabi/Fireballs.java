package com.sf_lolitahag.hanabi;

import java.awt.*;
import java.util.ArrayList;

public class Fireballs extends AbsPaintArray {

    private static final int FIREBALL_TAIL = 20;
    private static final int GAP_Y = -1;
    private static final int INIT_ALPHA = 200;
    private static final int R = 255;
    private static final int G = 200;
    private static final int B = 200;
    private double mGapX = 0;
    private ArrayList<Color> mColorList = new ArrayList<>();

    public Fireballs() {
    }

    @Override
    public void init(int x, int y) {
        super.init(x, y);
        mColorList.clear();

        for (int index = 0; index < FIREBALL_TAIL; index++) {
            Color color = new Color(R, G, B, INIT_ALPHA * (FIREBALL_TAIL - index) / FIREBALL_TAIL);
            mPaintObjectList.add(new PaintObjectImpl(x, y, color));
            mColorList.add(color);
        }
    }

    @Override
    public void next() {
        mGapX = -0.3 * mGapX + Math.random();
        PaintObject firstObject = mPaintObjectList.getFirst();
        PaintObject newObject = new PaintObjectImpl(firstObject.getX() + (int) mGapX, firstObject.getY() + GAP_Y);

        mPaintObjectList.addFirst(newObject);
        mPaintObjectList.removeLast();

        for (int index = 0; index < FIREBALL_TAIL; index++) {
            mPaintObjectList.get(index).updateColor(mColorList.get(index));
        }
    }
}
