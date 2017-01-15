package com.sf_lolitahag.motion;

import com.sf_lolitahag.Utils;
import java.awt.Image;
import javax.swing.Timer;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

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
  private static final String[] IMAGE_LIST = {"k_stay01", "k_stay02", "kiduku01", "manzoku01",
      "manzoku02", "manzoku03"};
  private int index = 0;
  private Callback callback;
  private Mode mode;
  private Timer sleepChecker = new Timer(CHECK_INTERVAL, (e) -> checkHumanSleep());
  private Timer timer = new Timer(PAINT_INTERVAL_STAY, (e) -> updateFileName());

  public Kitsune(Callback callback) {
    this.callback = callback;

    axisX = AXIS_X;
    axisY = AXIS_Y;

    Class tmpClass = getClass();
    cache = CACHE_MANAGER.createCache(Kitsune.class.getSimpleName(),
        CacheConfigurationBuilder
            .newCacheConfigurationBuilder(String.class, Image.class, ResourcePoolsBuilder.heap(100))
            .build());
    for (String fileName : IMAGE_LIST) {
      cache.put(fileName, Utils.getImageFromResources(tmpClass, fileName));
    }

    mode = Mode.STAY;
    updateFileName();
    timer.start();
    sleepChecker.start();
  }

  public void setVisibility(boolean isShow) {
    this.isShow = isShow;
  }

  private void checkHumanSleep() {
    if (callback.isCheckHumanSleep() && Math.random() >= CHECK_SUCCEED) {
      sleepChecker.stop();
      startKizuku();
    }
  }

  private void startKizuku() {
    setNextMode();
    String[] list = getImageList();
    fileName = list[index];
    timer.restart();
  }

  public void startManzoku() {
    String[] list = getImageList();
    fileName = list[index];
    timer.setDelay(PAINT_INTERVAL_MANZOKU);
    timer.start();
  }

  private void updateFileName() {
    switch (mode) {
      case STAY: {
        String[] list = getImageList();
        fileName = list[Utils.getRandInt(list.length)];
      }
      break;
      case KIZUKU: {
        String[] list = getImageList();
        index += 1;
        if (index >= list.length) {
          setNextMode();
          timer.stop();
          callback.onStartOkosu();
        } else {
          fileName = list[index];
        }
      }
      break;
      case MANZOKU: {
        String[] list = getImageList();
        index += 1;
        if (index >= list.length) {
          setNextMode();
          list = getImageList();
        }
        fileName = list[index];
      }
      break;
    }
  }

  private String[] getImageList() {
    switch (mode) {
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
    switch (mode) {
      case STAY:
        mode = Mode.KIZUKU;
        break;
      case KIZUKU:
        mode = Mode.MANZOKU;
        break;
      case MANZOKU:
        sleepChecker.start();
        mode = Mode.STAY;
        break;
    }
    index = 0;
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
