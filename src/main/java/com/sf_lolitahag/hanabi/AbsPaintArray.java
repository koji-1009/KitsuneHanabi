package com.sf_lolitahag.hanabi;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class AbsPaintArray {

    protected LinkedList<PaintObject> mPaintObjectList = new LinkedList<>();

    public void init(int x, int y) {
        mPaintObjectList.clear();
    }

    public int getTopX() {
        return mPaintObjectList.getFirst().getX();
    }

    public int getTopY() {
        return mPaintObjectList.getFirst().getY();
    }

    public ArrayList<PaintObject> getArray() {
        ArrayList<PaintObject> ret = new ArrayList<>();
        for (PaintObject object : mPaintObjectList) {
            ret.add(object);
        }

        return ret;
    }

    public abstract void next();
}
