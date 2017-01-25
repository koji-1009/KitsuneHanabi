/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.hanabi;

import java.util.ArrayList;
import javax.swing.Timer;

public class FireworkManager {

  private static final int HINOTAMA_MAX = 8;
  private static final int LAUNCH_INTERVAL = 1250;
  private ArrayList<Firework> fireworks = new ArrayList<>();

  public FireworkManager() {
    for (int i = 0; i < HINOTAMA_MAX; i++) {
      fireworks.add(new Firework());
    }
    new Timer(LAUNCH_INTERVAL, (e) -> startLaunch()).start();
  }

  private void startLaunch() {
    fireworks.forEach(Firework::startFireball);
  }

  public ArrayList<Firework> getFireworks() {
    return fireworks;
  }
}
