package com.sf_lolitahag.hanabi;

import com.sf_lolitahag.Utils;

import javax.swing.*;
import java.util.ArrayList;

public class Firework {

    private static final int POSITION_BASE = 400;
    private static final int POSITION_GAP = 300;
    private static final int TIMER_POSITION_UPDATE_FIREBALL = 25;
    private static final int TIMER_POSITION_UPDATE_SPARK = 150;
    private static final int SPARK_COUNT_MIN = 50;
    private static final int SPARK_COUNT_MAX = 250;
    private static final int FIREBALL_LIFETIME_BASE = 3000;
    private static final int FIREBALL_LIFETIME_COEFFICIENT = 2500;
    private static final int SPARK_LIFETIME_BASE = 1250;
    private static final int SPARK_LIFETIME_COEFFICIENT = 1000;
    private static final double LAUNCH_PERCENT = 0.7;
    private boolean mIsRun;
    private boolean mFireballShow;
    private boolean mSparksShow;
    private Fireball mFireball;
    private ArrayList<Spark> mSparkList = new ArrayList<>();
    private Timer mUpdateTimer;
    private Timer mFireballTimer;
    private Timer mSparkTimer;

    public Firework() {
        mIsRun = false;
        mFireballShow = false;
        mSparksShow = false;

        mFireball = new Fireball();
        mUpdateTimer = new Timer(TIMER_POSITION_UPDATE_FIREBALL, (e) -> updatePosition());
    }

    public Fireball getFireball() {
        return mFireball;
    }

    public ArrayList<Spark> getSparkList() {
        return mSparkList;
    }

    public boolean isFireballShow() {
        return mFireballShow;
    }

    public boolean isSparkShow() {
        return mSparksShow;
    }

    public void startFireball() {
        if (!mIsRun && Math.random() > LAUNCH_PERCENT) {
            mIsRun = true;

            initFireball(Utils.getRandBaseCoe(POSITION_BASE, POSITION_GAP), POSITION_BASE);
            mFireballShow = true;
            mFireballTimer = new Timer(Utils.getRandBaseCoe(FIREBALL_LIFETIME_BASE, FIREBALL_LIFETIME_COEFFICIENT),
                    (e) -> onFinishFireball());
            mFireballTimer.start();

            mUpdateTimer.setDelay(TIMER_POSITION_UPDATE_FIREBALL);
            mUpdateTimer.start();
        }
    }

    private void startSpark() {
        initSpark(mFireball.getTopX(), mFireball.getTopY());
        mSparksShow = true;
        mSparkTimer = new Timer(Utils.getRandBaseCoe(SPARK_LIFETIME_BASE, SPARK_LIFETIME_COEFFICIENT),
                (e) -> onFinishSpark());
        mSparkTimer.start();

        mUpdateTimer.setDelay(TIMER_POSITION_UPDATE_SPARK);
        mUpdateTimer.restart();
    }

    private void initFireball(int x, int y) {
        mFireball.init(x, y);
    }

    private void initSpark(int x, int y) {
        mSparkList.clear();
        int sparkCount = Utils.getRandRange(SPARK_COUNT_MIN, SPARK_COUNT_MAX);
        for (int index = 0; index < sparkCount; index++) {
            mSparkList.add(new Spark());
        }
        mSparkList.forEach(spark -> spark.init(x, y));
    }

    private void updatePosition() {
        if (mFireballShow) {
            mFireball.next();
        } else if (mSparksShow) {
            mSparkList.forEach(Spark::next);
        }
    }

    private void onFinishFireball() {
        mUpdateTimer.stop();

        mFireballShow = false;
        mFireballTimer.stop();

        startSpark();
    }

    private void onFinishSpark() {
        mSparksShow = false;
        mSparkTimer.stop();

        mUpdateTimer.stop();
        mIsRun = false;
    }
}
