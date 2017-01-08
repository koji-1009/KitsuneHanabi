package com.sf_lolitahag.hanabi;

import java.awt.*;

public class PaintObjectImpl implements PaintObject {

    private int mX;
    private int mY;
    private Color mColor;

    public PaintObjectImpl(int x, int y) {
        this(x, y, new Color(255, 255, 255));
    }

    public PaintObjectImpl(int x, int y, Color color) {
        mX = x;
        mY = y;
        mColor = color;
    }

    @Override
    public int getX() {
        return mX;
    }

    @Override
    public int getY() {
        return mY;
    }

    @Override
    public Color getColor() {
        return mColor;
    }

    @Override
    public void updateColor(Color color) {
        mColor = color;
    }
}
