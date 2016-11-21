package com.sf_lolitahag.motion;

import com.sf_lolitahag.Utils;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import javax.swing.*;
import java.awt.*;

public class Human extends AbstractMotion {

    private static final int AXIS_X = 140;
    private static final int AXIS_Y = 330;
    private static final int PAINT_INTERVAL_STAY = 1000;
    private static final int PAINT_INTERVAL_NERU = 1750;
    private static final int PAINT_INTERVAL_SLEEP = 750;
    private static final int PAINT_INTERVAL_OKOSU = 750;
    private static final int PAINT_INTERVAL_OKIRU = 2000;
    private static final String[] STAY_LIST = {"o_stay01"};
    private static final String[] NERU_LIST = {"o_stay01", "neru01", "neru02", "neru03"};
    private static final String[] SLEEP_LIST = {"neru03", "neru04"};
    private static final String[] OKIRU_LIST = {"okiru01", "okiru02", "okiru03"};
    private static final String[] OKOSU_LIST = {"okosu01", "okosu02"};
    private static final String[] IMAGE_LIST = {"o_stay01", "neru01", "neru02", "neru03", "neru04", "okiru01", "okiru02", "okiru03", "okosu01", "okosu02"};
    private int mIndex = 0;
    private int mRepeatTime;
    private Callback mCallback;
    private Mode mMode;
    private Timer mTimer = new Timer(PAINT_INTERVAL_STAY, (e) -> updateFileName());

    public Human(Callback callback) {
        mCallback = callback;

        mAxisX = AXIS_X;
        mAxisY = AXIS_Y;

        Class tmpClass = getClass();
        mCache = CACHE_MANAGER.createCache(Human.class.getSimpleName(),
                CacheConfigurationBuilder.newCacheConfigurationBuilder
                        (String.class, Image.class, ResourcePoolsBuilder.heap(100)).build());
        for (String fileName : IMAGE_LIST) {
            mCache.put(fileName, Utils.getImageFromResources(tmpClass, fileName));
        }

        mMode = Mode.STAY;
        mFileName = STAY_LIST[0];
        mTimer.start();
    }

    public void onSpaceKeyPress() {
        if (mMode == Mode.STAY) {
            setNextMode();
        }
    }

    public boolean isSleepNow() {
        return mMode == Mode.SLEEP;
    }

    public void startOkosu() {
        mRepeatTime = Utils.getRandRange(0, 3);
        setNextMode();
    }

    private void updateFileName() {
        switch (mMode) {
            case STAY:
                // nop
                break;
            case SLEEP: {
                String[] list = getImageList();
                mFileName = list[Utils.getRandNotEql(list.length)];
            }
            break;
            case OKOSU: {
                if (mRepeatTime >= 0) {
                    String[] list = getImageList();
                    mFileName = list[mIndex];
                    if (mIndex == 0) {
                        mIndex = 1;
                    } else {
                        mIndex = 0;
                        mRepeatTime -= 1;
                    }
                } else {
                    setNextMode();
                }
            }
            break;
            default: {
                String[] list = getImageList();
                mIndex += 1;
                if (mIndex >= list.length - 1) {
                    setNextMode();
                    list = getImageList();
                }
                mFileName = list[mIndex];
            }
            break;
        }
    }

    private String[] getImageList() {
        switch (mMode) {
            case STAY:
                return STAY_LIST;
            case NERU:
                return NERU_LIST;
            case SLEEP:
                return SLEEP_LIST;
            case OKOSU:
                return OKOSU_LIST;
            case OKIRU:
                return OKIRU_LIST;
            default:
                return STAY_LIST;
        }
    }

    private int getInterval() {
        switch (mMode) {
            case STAY:
                return PAINT_INTERVAL_STAY;
            case NERU:
                return PAINT_INTERVAL_NERU;
            case SLEEP:
                return PAINT_INTERVAL_SLEEP;
            case OKOSU:
                return PAINT_INTERVAL_OKOSU;
            case OKIRU:
                return PAINT_INTERVAL_OKIRU;
            default:
                return PAINT_INTERVAL_STAY;
        }
    }

    private void setNextMode() {
        switch (mMode) {
            case STAY:
                mMode = Mode.NERU;
                break;
            case NERU:
                mMode = Mode.SLEEP;
                break;
            case SLEEP:
                mMode = Mode.OKOSU;
                mCallback.onStartOkosuMotion();
                updateFileName();
                break;
            case OKOSU:
                mMode = Mode.OKIRU;
                mCallback.onFinishOkosuMotion();
                updateFileName();
                break;
            case OKIRU:
                mMode = Mode.STAY;
                break;
        }
        mIndex = 0;
        mTimer.setDelay(getInterval());
    }

    private enum Mode {
        STAY,
        NERU,
        SLEEP,
        OKOSU,
        OKIRU
    }

    public interface Callback {
        void onStartOkosuMotion();
        void onFinishOkosuMotion();
    }
}
