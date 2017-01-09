package com.sf_lolitahag.hanabi;

import com.sf_lolitahag.Utils;

import java.awt.*;
import java.util.ArrayList;

public class Fireball extends AbsPaintArray {

    private static final int GAP_Y = -1;
    private static final int TAIL_RANGE_START = 10;
    private static final int TAIL_RANGE_END = 25;
    private static final int R = 255;
    private static final int G = 200;
    private static final int B = 200;
    private static final int ALPHA_RANGE_START = 60;
    private static final int ALPHA_RANGE_END = 200;
    private ArrayList<Color> mColorList = new ArrayList<>();

    public Fireball() {
    }

    @Override
    public void init(int x, int y) {
        super.init(x, y);
        mColorList.clear();

        int tail = Utils.getRandRange(TAIL_RANGE_START, TAIL_RANGE_END);
        int alpha = Utils.getRandRange(ALPHA_RANGE_START, ALPHA_RANGE_END);
        for (int index = 0; index < tail; index++) {
            Color color = new Color(R, G, B, alpha * (tail - index) / tail);
            mPaintObjectList.add(new PaintObjectImpl(x, y, color));
            mColorList.add(color);
        }
    }

    @Override
    public void next() {
        PaintObject firstObject = mPaintObjectList.getFirst();
        PaintObject newObject = new PaintObjectImpl(firstObject.getX(), firstObject.getY() + GAP_Y);

        mPaintObjectList.addFirst(newObject);
        mPaintObjectList.removeLast();

        int size = mColorList.size();
        for (int index = 0; index < size; index++) {
            mPaintObjectList.get(index).updateColor(mColorList.get(index));
        }
    }
}
