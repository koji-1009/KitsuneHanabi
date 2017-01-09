package com.sf_lolitahag.panel;

import com.sf_lolitahag.Utils;
import com.sf_lolitahag.hanabi.Firework;
import com.sf_lolitahag.hanabi.FireworkManager;
import com.sf_lolitahag.hanabi.PaintObject;
import com.sf_lolitahag.motion.AbstractMotion;
import com.sf_lolitahag.motion.Furin;
import com.sf_lolitahag.motion.Human;
import com.sf_lolitahag.motion.Kitsune;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends AbstractPanel {

    private static final int PAINT_INTERVAL = 30;
    private static final String BACK = "back";
    private static final String ROOM = "room";
    private static final String SKY = "sky";
    private final Image mBack;
    private final Image mRoom;
    private final Image mSky;
    private ArrayList<AbstractMotion> mMotions = new ArrayList<>();
    private Human mHuman;
    private Kitsune mKitsune;
    private FireworkManager mFireworks;

    public GamePanel() {
        Class tmpClass = getClass();
        mBack = Utils.getImageFromResources(tmpClass, BACK);
        mRoom = Utils.getImageFromResources(tmpClass, ROOM);
        mSky = Utils.getImageFromResources(tmpClass, SKY);
        mFireworks = new FireworkManager();
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

        mFireworks.getFireworks().forEach(fireworks -> drawHinotama(g, fireworks));
    }

    private void initMotions() {
        Furin furin = new Furin();
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

        mMotions.add(furin);
        mMotions.add(mHuman);
        mMotions.add(mKitsune);
    }

    private void startRepaintTimer() {
        new Timer(PAINT_INTERVAL, (e) -> repaint()).start();
    }

    private void drawHinotama(final Graphics g, final Firework fireworks) {
        if (fireworks.isFireballShow()) {
            fireworks.getFireball().getArray().forEach(fireball -> drawFireball(g, fireball));
        } else if (fireworks.isSparksShow()) {
            fireworks.getSparkList().forEach(sparks -> sparks.getArray().forEach(spark -> drawSpark(g, spark)));
        }
    }

    private void drawFireball(Graphics g, PaintObject fireball) {
        g.setColor(fireball.getColor());
        g.fillOval(fireball.getX(), fireball.getY(), 4, 4);
    }

    private void drawSpark(Graphics g, PaintObject spark) {
        g.setColor(spark.getColor());
        g.fillOval(spark.getX(), spark.getY(), 2, 2);
    }
}
