package com.sf_lolitahag.hanabi;

import com.sf_lolitahag.Utils;

import javax.swing.*;
import java.util.ArrayList;

public class Firework {

    private static final int INIT_POSITION = 400;
    private static final int POSITION_GAP_X = 300;
    private static final int UPDATE_FIREBALL = 25;
    private static final int UPDATE_SPARK = 150;
    private static final int SPARKS_NUM = 100;
    private static final double LAUNCH_PERCENT = 0.8;
    private boolean mIsRun;
    private boolean mFireballShow;
    private boolean mSparksShow;
    private Fireball mFireball;
    private ArrayList<Spark> mSparkList = new ArrayList<>();
    private Timer mUpdateTimer = new Timer(UPDATE_FIREBALL, (e) -> updatePosition());
    private Timer mFireballTimer;
    private Timer mSparkTimer;

    public Firework() {
        mFireball = new Fireball();
        for (int index = 0; index < SPARKS_NUM; index++) {
            mSparkList.add(new Spark());
        }
        mIsRun = false;
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

    public boolean isSparksShow() {
        return mSparksShow;
    }

    public void startFireball() {
        if (!mIsRun && Math.random() > LAUNCH_PERCENT) {
            mIsRun = true;

            initFireball(Utils.getRandBaseCoe(INIT_POSITION, POSITION_GAP_X), INIT_POSITION);
            mFireballShow = true;
            mFireballTimer = new Timer(Utils.getRandBaseCoe(3000, 3000), (e) -> onFinishFireball());
            mFireballTimer.start();

            mUpdateTimer.setDelay(UPDATE_FIREBALL);
            mUpdateTimer.start();
        }
    }

    private void startSparks() {
        initSparks(mFireball.getTopX(), mFireball.getTopY());
        mSparksShow = true;
        mSparkTimer = new Timer(Utils.getRandBaseCoe(1500, 750), (e) -> onFinishSpark());
        mSparkTimer.start();

        mUpdateTimer.setDelay(UPDATE_SPARK);
        mUpdateTimer.restart();
    }

    private void initFireball(int x, int y) {
        mFireball.init(x, y);
    }

    private void initSparks(int x, int y) {
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

        startSparks();
    }

    private void onFinishSpark() {
        mSparksShow = false;
        mSparkTimer.stop();

        mUpdateTimer.stop();
        mIsRun = false;
    }
}
