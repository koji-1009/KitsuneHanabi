/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.panel;

import com.sf_lolitahag.Utils;
import com.sf_lolitahag.hanabi.FireworkManager;
import com.sf_lolitahag.motion.AbstractMotion;
import com.sf_lolitahag.motion.Furin;
import com.sf_lolitahag.motion.Human;
import com.sf_lolitahag.motion.Kitsune;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class GamePanel extends AbstractPanel {

  private static final int PAINT_INTERVAL = 33;
  private static final String BACK = "back";
  private static final String ROOM = "room";
  private static final String SKY = "sky";
  private final Image back;
  private final Image room;
  private final Image sky;
  private final List<AbstractMotion> motions = new ArrayList<>();
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
  protected void paintComponent(final Graphics g) {
    g.drawImage(sky, 0, 0, null);
    g.drawImage(back, 0, 0, null);
    g.drawImage(room, 0, 0, null);

    motions.stream().filter(AbstractMotion::isShow)
        .forEach(m -> g.drawImage(m.getBodyImage(), m.getAxisX(), m.getAxisY(), null));

    fireworkManager.draw(g);
  }

  private void initMotions() {
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

    motions.add(new Furin());
    motions.add(human);
    motions.add(kitsune);
  }

  private void startRepaintTimer() {
    new Timer(PAINT_INTERVAL, (e) -> repaint()).start();
  }
}
