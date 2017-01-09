package com.sf_lolitahag.hanabi;

import com.sf_lolitahag.Utils;

import java.awt.*;

public class Spark extends AbsPaintArray {

    private static final double RESIST = 0.05;
    private static final double GRAVITY = 0.7;
    private static final int TAIL_RANGE_START = 5;
    private static final int TAIL_RANGE_END = 30;
    private static final int INIT_ALPHA = 225;
    private static final int COLOR_R_BASE = 200;
    private static final int COLOR_R_COEFFICIENT = 50;
    private static final int COLOR_GB_BASE = 50;
    private static final int COLOR_GB_COEFFICIENT = 200;
    private static final int EXPLOSION_BASE = 15;
    private static final int EXPLOSION_COEFFICIENT = 20;
    private static final int ALPHA_RANGE_START = 10;
    private static final int ALPHA_RANGE_END = 30;
    private double mGapX;
    private double mGapY;

    public Spark() {
    }

    @Override
    public void init(int x, int y) {
        super.init(x, y);

        int r = Utils.getRandBaseCoe(COLOR_R_BASE, COLOR_R_COEFFICIENT);
        int g = Utils.getRandBaseCoe(COLOR_GB_BASE, COLOR_GB_COEFFICIENT);
        int b = Utils.getRandBaseCoe(COLOR_GB_BASE, COLOR_GB_COEFFICIENT);
        Color color = new Color(r, g, b, INIT_ALPHA);

        int tail = Utils.getRandRange(TAIL_RANGE_START, TAIL_RANGE_END);
        for (int index = 0; index < tail; index++) {
            mPaintObjectList.add(new PaintObjectImpl(x, y, color));
        }

        double roll = Math.random() * Math.PI * 2;
        double coefficient = Utils.getRandBaseCoe(EXPLOSION_BASE, EXPLOSION_COEFFICIENT);
        mGapX = coefficient * Math.cos(roll);
        mGapY = coefficient * Math.sin(roll);
    }

    @Override
    public void next() {
        mGapY += GRAVITY;
        double coefficient = RESIST * Math.pow(Math.pow(mGapX, 2) + Math.pow(mGapY, 2), 0.5);
        mGapX -= coefficient * mGapX;
        mGapY -= coefficient * mGapY;

        if (Math.floor(mGapX) == 0) {
            mGapX = 0;
        }
        PaintObject firstObject = mPaintObjectList.getFirst();
        PaintObject newObject = new PaintObjectImpl(firstObject.getX() + (int) mGapX, firstObject.getY() + (int) mGapY);

        mPaintObjectList.addFirst(newObject);
        mPaintObjectList.removeLast();

        Color color = firstObject.getColor();
        int alpha = color.getAlpha() - Utils.getRandRange(ALPHA_RANGE_START, ALPHA_RANGE_END);
        if (alpha < 0) {
            alpha = 0;
        }
        Color newColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
        for (PaintObject object : mPaintObjectList) {
            object.updateColor(newColor);
        }
    }
}
