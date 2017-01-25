/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.hanabi;

import com.sf_lolitahag.Utils;
import java.util.ArrayList;
import javax.swing.Timer;

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
  private boolean isRun;
  private boolean fireballShow;
  private boolean sparksShow;
  private Fireball fireball;
  private ArrayList<Spark> sparks = new ArrayList<>();
  private Timer updateTimer;
  private Timer fireballTimer;
  private Timer sparkTimer;

  public Firework() {
    isRun = false;
    fireballShow = false;
    sparksShow = false;

    fireball = new Fireball();
    updateTimer = new Timer(TIMER_POSITION_UPDATE_FIREBALL, (e) -> updatePosition());
  }

  public Fireball getFireball() {
    return fireball;
  }

  public ArrayList<Spark> getSparkList() {
    return sparks;
  }

  public boolean isFireballShow() {
    return fireballShow;
  }

  public boolean isSparkShow() {
    return sparksShow;
  }

  public void startFireball() {
    if (!isRun && Math.random() > LAUNCH_PERCENT) {
      isRun = true;

      initFireball(Utils.getRandBaseCoe(POSITION_BASE, POSITION_GAP), POSITION_BASE);
      fireballShow = true;
      fireballTimer = new Timer(
          Utils.getRandBaseCoe(FIREBALL_LIFETIME_BASE, FIREBALL_LIFETIME_COEFFICIENT),
          (e) -> onFinishFireball());
      fireballTimer.start();

      updateTimer.setDelay(TIMER_POSITION_UPDATE_FIREBALL);
      updateTimer.start();
    }
  }

  private void startSpark() {
    initSpark(fireball.getTopX(), fireball.getTopY());
    sparksShow = true;
    sparkTimer = new Timer(Utils.getRandBaseCoe(SPARK_LIFETIME_BASE, SPARK_LIFETIME_COEFFICIENT),
        (e) -> onFinishSpark());
    sparkTimer.start();

    updateTimer.setDelay(TIMER_POSITION_UPDATE_SPARK);
    updateTimer.restart();
  }

  private void initFireball(int x, int y) {
    fireball.init(x, y);
  }

  private void initSpark(int x, int y) {
    sparks.clear();
    int sparkCount = Utils.getRandRange(SPARK_COUNT_MIN, SPARK_COUNT_MAX);
    for (int index = 0; index < sparkCount; index++) {
      sparks.add(new Spark());
    }
    sparks.forEach(spark -> spark.init(x, y));
  }

  private void updatePosition() {
    if (fireballShow) {
      fireball.next();
    } else if (sparksShow) {
      sparks.forEach(Spark::next);
    }
  }

  private void onFinishFireball() {
    updateTimer.stop();

    fireballShow = false;
    fireballTimer.stop();

    startSpark();
  }

  private void onFinishSpark() {
    sparksShow = false;
    sparkTimer.stop();

    updateTimer.stop();
    isRun = false;
  }
}
