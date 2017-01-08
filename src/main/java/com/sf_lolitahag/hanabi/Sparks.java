package com.sf_lolitahag.hanabi;

import com.sf_lolitahag.Utils;

import java.awt.*;

public class Sparks extends AbsPaintArray {

    private static final double RESIST = 0.05;
    private static final double GRAVITY = 1;
    private static final int SPARKS_TAIL = 20;
    private static final int INIT_FADE = 225;
    private static final int R = 255;
    private int mCount;
    private int mG;
    private int mB;
    private double mGapX;
    private double mGapY;

    public Sparks() {
    }

    @Override
    public void init(int x, int y) {
        super.init(x, y);

        mG = Utils.getRandBaseCoe(100, 150);
        mB = Utils.getRandBaseCoe(100, 150);
        Color color = new Color(R, mG, mB);
        for (int index = 0; index < SPARKS_TAIL; index++) {
            mPaintObjectList.add(new PaintObjectImpl(x, y, color));
        }

        mCount = 0;

        double roll = Math.random() * Math.PI * 2;
        double v = Utils.getRandBaseCoe(25, 10);
        mGapX = v * Math.cos(roll);
        mGapY = v * Math.sin(roll);
    }

    @Override
    public void next() {
        mGapY += GRAVITY;
        double coe = RESIST * Math.pow(Math.pow(mGapX, 2) + Math.pow(mGapY, 2), 0.5);
        mGapX -= coe * mGapX;
        mGapY -= coe * mGapY;

        PaintObject firstObject = mPaintObjectList.getFirst();
        PaintObject newObject = new PaintObjectImpl(firstObject.getX() + (int) mGapX,
                firstObject.getY() + (int) mGapY, firstObject.getColor());

        mPaintObjectList.addFirst(newObject);
        mPaintObjectList.removeLast();

        updateColor();
    }

    private void updateColor() {
        int fade = INIT_FADE - mCount;
        if (fade >= 0) {
            Color color = new Color(R, mG, mB, fade);
            for (PaintObject object : mPaintObjectList) {
                object.updateColor(color);
            }

            mCount += 15;
        }
    }
}
