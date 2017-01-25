/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.panel;

import com.sf_lolitahag.Utils;
import com.sf_lolitahag.hanabi.Firework;
import com.sf_lolitahag.hanabi.FireworkManager;
import com.sf_lolitahag.hanabi.PaintObject;
import com.sf_lolitahag.motion.AbstractMotion;
import com.sf_lolitahag.motion.Furin;
import com.sf_lolitahag.motion.Human;
import com.sf_lolitahag.motion.Kitsune;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.Timer;

public class GamePanel extends AbstractPanel {

  private static final int PAINT_INTERVAL = 30;
  private static final String BACK = "back";
  private static final String ROOM = "room";
  private static final String SKY = "sky";
  private final Image back;
  private final Image room;
  private final Image sky;
  private ArrayList<AbstractMotion> motions = new ArrayList<>();
  private Human human;
  private Kitsune kitsune;
  private FireworkManager fireworkManager;

  public GamePanel() {
    Class tmpClass = getClass();
    back = Utils.getImageFromResources(tmpClass, BACK);
    room = Utils.getImageFromResources(tmpClass, ROOM);
    sky = Utils.getImageFromResources(tmpClass, SKY);
    fireworkManager = new FireworkManager();
    initMotions();
    startRepaintTimer();
  }

  @Override
  public void onSpaceKeyPress() {
    human.onSpaceKeyPress();
  }

  @Override
  protected void paintComponent(Graphics g) {
    g.drawImage(sky, 0, 0, null);
    g.drawImage(back, 0, 0, null);
    g.drawImage(room, 0, 0, null);

    motions.forEach(motion -> {
      if (motion.isShow()) {
        g.drawImage(motion.getBodyImage(), motion.getAxisX(), motion.getAxisY(), null);
      }
    });

    fireworkManager.getFireworks().forEach(fireworks -> drawHinotama(g, fireworks));
  }

  private void initMotions() {
    Furin furin = new Furin();
    human = new Human(new Human.Callback() {
      @Override
      public void onStartOkosuMotion() {
        kitsune.setVisibility(false);
      }

      @Override
      public void onFinishOkosuMotion() {
        kitsune.startManzoku();
        kitsune.setVisibility(true);
      }
    });
    kitsune = new Kitsune(new Kitsune.Callback() {
      @Override
      public boolean isCheckHumanSleep() {
        return human.isSleepNow();
      }

      @Override
      public void onStartOkosu() {
        human.startOkosu();
      }
    });

    motions.add(furin);
    motions.add(human);
    motions.add(kitsune);
  }

  private void startRepaintTimer() {
    new Timer(PAINT_INTERVAL, (e) -> repaint()).start();
  }

  private void drawHinotama(final Graphics g, final Firework fireworks) {
    if (fireworks.isFireballShow()) {
      fireworks.getFireball().getArray().forEach(fireball -> drawFireball(g, fireball));
    } else if (fireworks.isSparkShow()) {
      fireworks.getSparkList()
          .forEach(sparks -> sparks.getArray().forEach(spark -> drawSpark(g, spark)));
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
