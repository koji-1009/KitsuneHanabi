package com.sf_lolitahag.hanabi;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Fireballs {

    private static final int FIREBALL_TAIL = 20;
    private static final int GAP_Y = -1;
    private static final int INIT_ALPHA = 100;
    private static final int R = 255;
    private static final int G = 200;
    private static final int B = 200;
    private LinkedList<Integer> mXs = new LinkedList<>();
    private LinkedList<Integer> mYs = new LinkedList<>();
    private ArrayList<Color> mColors = new ArrayList<>();
    private double mGapX;

    public Fireballs() {
    }

    public void init(int x, int y) {
        mXs.clear();
        mYs.clear();
        for (int index = 0; index < FIREBALL_TAIL; index++) {
            mXs.add(x);
            mYs.add(y);
            mColors.add(new Color(R, G, B, INIT_ALPHA * (FIREBALL_TAIL - index) / FIREBALL_TAIL));
        }
    }

    public int getTopX() {
        return mXs.getFirst();
    }

    public int getTopY() {
        return mYs.getFirst();
    }

    public ArrayList<Fireball> getArray() {
        ArrayList<Fireball> ret = new ArrayList<>();
        for (int index = 0; index < FIREBALL_TAIL; index++) {
            ret.add(new Fireball(mXs.get(index), mYs.get(index), mColors.get(index)));
        }
        return ret;
    }

    public void next() {
        mGapX = -0.3 * mGapX + Math.random();
        mXs.addFirst(mXs.getFirst() + (int) mGapX);
        mYs.addFirst(mYs.getFirst() + GAP_Y);

        mXs.removeLast();
        mYs.removeLast();
    }
}
