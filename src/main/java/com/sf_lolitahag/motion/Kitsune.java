package com.sf_lolitahag.motion;

import com.sf_lolitahag.Utils;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import javax.swing.*;
import java.awt.*;

public class Kitsune extends AbstractMotion {

    private static final int AXIS_X = 140;
    private static final int AXIS_Y = 330;
    private static final int PAINT_INTERVAL_STAY = 750;
    private static final int PAINT_INTERVAL_MANZOKU = 750;
    private static final int CHECK_INTERVAL = 1250;
    private static final double CHECK_SUCCEED = 0.6;
    private static final String[] STAY_LIST = {"k_stay01", "k_stay02"};
    private static final String[] KIZUKU_LIST = {"kiduku01"};
    private static final String[] MANZOKU_LIST = {"manzoku01", "manzoku02", "manzoku03"};
    private static final String[] IMAGE_LIST = {"k_stay01", "k_stay02", "kiduku01", "manzoku01", "manzoku02", "manzoku03"};
    private int mIndex = 0;
    private Callback mCallback;
    private Mode mMode;
    private Timer mTimer = new Timer(PAINT_INTERVAL_STAY, (e) -> updateFileName());
    private Timer mSleepChecker = new Timer(CHECK_INTERVAL, (e) -> checkHumanSleep());

    public Kitsune(Callback callback) {
        mCallback = callback;

        mAxisX = AXIS_X;
        mAxisY = AXIS_Y;

        Class tmpClass = getClass();
        mCache = CACHE_MANAGER.createCache(Kitsune.class.getSimpleName(),
                CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Image.class, ResourcePoolsBuilder.heap(100)).build());
        for (String fileName : IMAGE_LIST) {
            mCache.put(fileName, Utils.getImageFromResources(tmpClass, fileName));
        }

        mMode = Mode.STAY;
        updateFileName();
        mTimer.start();
        mSleepChecker.start();
    }

    public void setVisibility(boolean isShow) {
        mIsShow = isShow;
    }

    private void checkHumanSleep() {
        if (mCallback.isCheckHumanSleep() && Math.random() >= CHECK_SUCCEED) {
            mSleepChecker.stop();
            startKizuku();
        }
    }

    private void startKizuku() {
        setNextMode();
        String[] list = getImageList();
        mFileName = list[mIndex];
        mTimer.restart();
    }

    public void startManzoku() {
        String[] list = getImageList();
        mFileName = list[mIndex];
        mTimer.setDelay(PAINT_INTERVAL_MANZOKU);
        mTimer.start();
    }

    private void updateFileName() {
        switch (mMode) {
            case STAY: {
                String[] list = getImageList();
                mFileName = list[Utils.getRandNotEql(list.length)];
            }
            break;
            case KIZUKU: {
                String[] list = getImageList();
                mIndex += 1;
                if (mIndex >= list.length) {
                    setNextMode();
                    mTimer.stop();
                    mCallback.onStartOkosu();
                } else {
                    mFileName = list[mIndex];
                }
            }
            break;
            case MANZOKU: {
                String[] list = getImageList();
                mIndex += 1;
                if (mIndex >= list.length) {
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
            case KIZUKU:
                return KIZUKU_LIST;
            case MANZOKU:
                return MANZOKU_LIST;
            default:
                return STAY_LIST;
        }
    }

    private void setNextMode() {
        switch (mMode) {
            case STAY:
                mMode = Mode.KIZUKU;
                break;
            case KIZUKU:
                mMode = Mode.MANZOKU;
                break;
            case MANZOKU:
                mSleepChecker.start();
                mMode = Mode.STAY;
                break;
        }
        mIndex = 0;
    }

    private enum Mode {
        STAY,
        KIZUKU,
        MANZOKU
    }

    public interface Callback {
        boolean isCheckHumanSleep();
        void onStartOkosu();
    }
}
