package com.sf_lolitahag.panel;

import com.sf_lolitahag.Utils;
import com.sf_lolitahag.motion.AbstractMotion;
import com.sf_lolitahag.motion.Furin;
import com.sf_lolitahag.motion.Human;
import com.sf_lolitahag.motion.Kitsune;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends AbstractPanel {

    private static final int PAINT_INTERVAL = 50;
    private static final String BACK = "back";
    private static final String ROOM = "room";
    private static final String SKY = "sky";
    private final Image mBack;
    private final Image mRoom;
    private final Image mSky;
    private ArrayList<AbstractMotion> mMotions = new ArrayList<>();
    private Furin mFurin;
    private Human mHuman;
    private Kitsune mKitsune;

    public GamePanel() {
        Class tmpClass = getClass();
        mBack = Utils.getImageFromResources(tmpClass, BACK);
        mRoom = Utils.getImageFromResources(tmpClass, ROOM);
        mSky = Utils.getImageFromResources(tmpClass, SKY);
        initMotions();
        startRepaintTimer();
    }

    @Override
    public void onSpaceKeyPress() {
        mHuman.onSpaceKeyPress();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(mSky, 0, 0, null);
        g.drawImage(mBack, 0, 0, null);
        g.drawImage(mRoom, 0, 0, null);

        mMotions.forEach(motion -> {
            if (motion.isShow()) {
                g.drawImage(motion.getBodyImage(), motion.getAxisX(), motion.getAxisY(), null);
            }
        });
    }

    private void initMotions() {
        mFurin = new Furin();
        mHuman = new Human(new Human.Callback() {
            @Override
            public void onStartOkosuMotion() {
                mKitsune.setVisibility(false);
            }

            @Override
            public void onFinishOkosuMotion() {
                mKitsune.startManzoku();
                mKitsune.setVisibility(true);
            }
        });
        mKitsune = new Kitsune(new Kitsune.Callback() {
            @Override
            public boolean isCheckHumanSleep() {
                return mHuman.isSleepNow();
            }

            @Override
            public void onStartOkosu() {
                mHuman.startOkosu();
            }
        });

        mMotions.add(mFurin);
        mMotions.add(mHuman);
        mMotions.add(mKitsune);
    }

    private void startRepaintTimer() {
        new Timer(PAINT_INTERVAL, (e) -> repaint()).start();
    }
}
