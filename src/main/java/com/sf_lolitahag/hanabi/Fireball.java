package com.sf_lolitahag.hanabi;

import com.sf_lolitahag.Utils;

import java.awt.*;
import java.util.ArrayList;

public class Fireball extends AbsPaintArray {

    private static final int R = 255;
    private static final int G = 200;
    private static final int B = 200;
    private static final int ALPHA_MIN = 60;
    private static final int ALPHA_MAX = 200;
    private static final int TAIL_COUNT_MIN = 10;
    private static final int TAIL_COUNT_MAX = 25;
    private static final int UPDATE_AXIS_Y = -1;
    private ArrayList<Color> mColorList = new ArrayList<>();

    public Fireball() {
    }

    @Override
    public void init(int x, int y) {
        super.init(x, y);
        mColorList.clear();

        int tail = Utils.getRandRange(TAIL_COUNT_MIN, TAIL_COUNT_MAX);
        int alpha = Utils.getRandRange(ALPHA_MIN, ALPHA_MAX);
        for (int index = 0; index < tail; index++) {
            Color color = new Color(R, G, B, alpha * (tail - index) / tail);
            mPaintObjectList.add(new PaintObjectImpl(x, y, color));
            mColorList.add(color);
        }
    }

    @Override
    public void next() {
        PaintObject firstObject = mPaintObjectList.getFirst();
        PaintObject newObject = new PaintObjectImpl(firstObject.getX(), firstObject.getY() + UPDATE_AXIS_Y);

        mPaintObjectList.addFirst(newObject);
        mPaintObjectList.removeLast();

        int size = mColorList.size();
        for (int index = 0; index < size; index++) {
            mPaintObjectList.get(index).updateColor(mColorList.get(index));
        }
    }
}
