/*
Copyright (C) 2016 Koji Wakamiya
Copyright (C) 2016 Masanori Kojima
Released under the MIT license
http://opensource.org/licenses/mit-license.php
*/
package com.sf_lolitahag.motion;

import com.sf_lolitahag.Utils;
import java.awt.Image;
import javax.swing.Timer;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class Furin extends AbstractMotion {

  private static final int AXIS_X = 200;
  private static final int AXIS_Y = 155;
  private static final int PAINT_INTERVAL = 500;
  private static final int PAINT_INTERVAL_MAX = 1500;
  private static final String[] IMAGE_LIST = {"furin01", "furin02", "furin03", "furin03"};
  private Timer timer = new Timer(PAINT_INTERVAL, (e) -> updateFileName());

  public Furin() {
    axisX = AXIS_X;
    axisY = AXIS_Y;

    Class tmpClass = getClass();
    cache = CACHE_MANAGER.createCache(Furin.class.getSimpleName(),
        CacheConfigurationBuilder
            .newCacheConfigurationBuilder(String.class, Image.class, ResourcePoolsBuilder.heap(100))
            .build());
    for (String fileName : IMAGE_LIST) {
      cache.put(fileName, Utils.getImageFromResources(tmpClass, fileName));
    }

    updateFileName();
    timer.start();
  }

  private void updateFileName() {
    fileName = IMAGE_LIST[Utils.getRandInt(IMAGE_LIST.length)];
    timer.setDelay(Utils.getRandRange(PAINT_INTERVAL, PAINT_INTERVAL_MAX));
  }
}
