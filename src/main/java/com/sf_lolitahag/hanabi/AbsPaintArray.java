package com.sf_lolitahag.hanabi;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class AbsPaintArray {

    protected LinkedList<Integer> mXs = new LinkedList<>();
    protected LinkedList<Integer> mYs = new LinkedList<>();

    public void init(int x, int y) {
        mXs.clear();
        mYs.clear();
    }

    public int getTopX() {
        return mXs.getFirst();
    }

    public int getTopY() {
        return mYs.getFirst();
    }

    public abstract void next();
    public abstract ArrayList<PaintObject> getArray();
}
