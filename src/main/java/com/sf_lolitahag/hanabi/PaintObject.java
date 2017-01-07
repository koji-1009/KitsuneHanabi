package com.sf_lolitahag.hanabi;

import java.awt.*;

public class PaintObject {

    private int mX;
    private int mY;
    private Color mColor;

    public PaintObject(int x, int y, Color color) {
        mX = x;
        mY = y;
        mColor = color;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    public Color getColor() {
        return mColor;
    }
}
