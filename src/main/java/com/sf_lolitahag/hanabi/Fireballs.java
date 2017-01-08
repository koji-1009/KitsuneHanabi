package com.sf_lolitahag.hanabi;

import java.awt.*;
import java.util.ArrayList;

public class Fireballs extends AbsPaintArray {

    private static final int FIREBALL_TAIL = 20;
    private static final int GAP_Y = -1;
    private static final int INIT_ALPHA = 100;
    private static final int R = 255;
    private static final int G = 200;
    private static final int B = 200;
    private ArrayList<Color> mColors = new ArrayList<>();
    private double mGapX;

    public Fireballs() {
    }

    @Override
    public void init(int x, int y) {
        mXs.clear();
        mYs.clear();
        for (int index = 0; index < FIREBALL_TAIL; index++) {
            mXs.add(x);
            mYs.add(y);
            mColors.add(new Color(R, G, B, INIT_ALPHA * (FIREBALL_TAIL - index) / FIREBALL_TAIL));
        }
    }

    @Override
    public ArrayList<PaintObject> getArray() {
        ArrayList<PaintObject> ret = new ArrayList<>();
        for (int index = 0; index < FIREBALL_TAIL; index++) {
            ret.add(new PaintObject(mXs.get(index), mYs.get(index), mColors.get(index)));
        }
        return ret;
    }

    @Override
    public void next() {
        mGapX = -0.3 * mGapX + Math.random();
        mXs.addFirst(mXs.getFirst() + (int) mGapX);
        mYs.addFirst(mYs.getFirst() + GAP_Y);

        mXs.removeLast();
        mYs.removeLast();
    }
}
