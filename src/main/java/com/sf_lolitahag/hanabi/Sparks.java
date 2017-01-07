package com.sf_lolitahag.hanabi;

import com.sf_lolitahag.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Sparks {

    private static final double RESIST = 0.05;
    private static final double GRAVITY = 1;
    private static final int SPARKS_TAIL = 20;
    private static final int INIT_FADE = 225;
    private static final int R = 255;
    private LinkedList<Integer> mXs = new LinkedList<>();
    private LinkedList<Integer> mYs = new LinkedList<>();
    private int mCount;
    private double mGapX;
    private double mGapY;
    private Color mColor;

    public Sparks() {
    }

    public void init(int x, int y) {
        mXs.clear();
        mYs.clear();
        for (int index = 0; index < SPARKS_TAIL; index++) {
            mXs.add(x);
            mYs.add(y);
        }

        mCount = 0;

        double roll = Math.random() * Math.PI * 2;
        double v = Utils.getRandBaseCoe(25, 10);
        mGapX = v * Math.cos(roll);
        mGapY = v * Math.sin(roll);

        updateColor();
    }

    public void next() {
        mGapY += GRAVITY;
        double coe = RESIST * Math.pow(Math.pow(mGapX, 2) + Math.pow(mGapY, 2), 0.5);
        mGapX -= coe * mGapX;
        mGapY -= coe * mGapY;
        mXs.addFirst(mXs.getFirst() + (int) mGapX);
        mYs.addFirst(mYs.getFirst() + (int) mGapY);

        mXs.removeLast();
        mYs.removeLast();
        updateColor();
    }

    public ArrayList<PaintObject> getArray() {
        ArrayList<PaintObject> ret = new ArrayList<>();
        for (int index = 0; index < SPARKS_TAIL; index++) {
            ret.add(new PaintObject(mXs.get(index), mYs.get(index), mColor));
        }
        return ret;
    }

    private void updateColor() {
        int fade = INIT_FADE - mCount;
        if (fade >= 0) {
            int g = Utils.getRandBaseCoe(100, 150);
            int b = Utils.getRandBaseCoe(100, 150);
            mColor = new Color(R, g, b, fade);
            mCount += 15;
        }
    }
}
