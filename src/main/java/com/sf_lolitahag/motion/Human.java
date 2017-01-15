package com.sf_lolitahag.motion;

import com.sf_lolitahag.Utils;
import java.awt.Image;
import javax.swing.Timer;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class Human extends AbstractMotion {

  private static final int AXIS_X = 140;
  private static final int AXIS_Y = 330;
  private static final int PAINT_INTERVAL_STAY = 1000;
  private static final int PAINT_INTERVAL_NERU = 1750;
  private static final int PAINT_INTERVAL_SLEEP = 750;
  private static final int PAINT_INTERVAL_OKOSU = 750;
  private static final int PAINT_INTERVAL_OKIRU = 2000;
  private static final String[] STAY_LIST = {"o_stay01"};
  private static final String[] NERU_LIST = {"o_stay01", "neru01", "neru02", "neru03"};
  private static final String[] SLEEP_LIST = {"neru03", "neru04"};
  private static final String[] OKIRU_LIST = {"okiru01", "okiru02", "okiru03"};
  private static final String[] OKOSU_LIST = {"okosu01", "okosu02"};
  private static final String[] IMAGE_LIST = {"o_stay01", "neru01", "neru02", "neru03", "neru04",
      "okiru01", "okiru02", "okiru03", "okosu01", "okosu02"};
  private int index = 0;
  private int repeatTime;
  private Callback callback;
  private Mode mode;
  private Timer timer = new Timer(PAINT_INTERVAL_STAY, (e) -> updateFileName());

  public Human(Callback callback) {
    this.callback = callback;

    axisX = AXIS_X;
    axisY = AXIS_Y;

    Class tmpClass = getClass();
    cache = CACHE_MANAGER.createCache(Human.class.getSimpleName(),
        CacheConfigurationBuilder.newCacheConfigurationBuilder
            (String.class, Image.class, ResourcePoolsBuilder.heap(100)).build());
    for (String fileName : IMAGE_LIST) {
      cache.put(fileName, Utils.getImageFromResources(tmpClass, fileName));
    }

    mode = Mode.STAY;
    fileName = STAY_LIST[0];
    timer.start();
  }

  public void onSpaceKeyPress() {
    if (mode == Mode.STAY) {
      setNextMode();
    }
  }

  public boolean isSleepNow() {
    return mode == Mode.SLEEP;
  }

  public void startOkosu() {
    repeatTime = Utils.getRandRange(0, 3);
    setNextMode();
  }

  private void updateFileName() {
    switch (mode) {
      case STAY:
        // nop
        break;
      case SLEEP: {
        String[] list = getImageList();
        fileName = list[Utils.getRandInt(list.length)];
      }
      break;
      case OKOSU: {
        if (repeatTime >= 0) {
          String[] list = getImageList();
          fileName = list[index];
          if (index == 0) {
            index = 1;
          } else {
            index = 0;
            repeatTime -= 1;
          }
        } else {
          setNextMode();
        }
      }
      break;
      default: {
        String[] list = getImageList();
        index += 1;
        if (index >= list.length - 1) {
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
      case NERU:
        return NERU_LIST;
      case SLEEP:
        return SLEEP_LIST;
      case OKOSU:
        return OKOSU_LIST;
      case OKIRU:
        return OKIRU_LIST;
      default:
        return STAY_LIST;
    }
  }

  private int getInterval() {
    switch (mode) {
      case STAY:
        return PAINT_INTERVAL_STAY;
      case NERU:
        return PAINT_INTERVAL_NERU;
      case SLEEP:
        return PAINT_INTERVAL_SLEEP;
      case OKOSU:
        return PAINT_INTERVAL_OKOSU;
      case OKIRU:
        return PAINT_INTERVAL_OKIRU;
      default:
        return PAINT_INTERVAL_STAY;
    }
  }

  private void setNextMode() {
    switch (mode) {
      case STAY:
        mode = Mode.NERU;
        break;
      case NERU:
        mode = Mode.SLEEP;
        break;
      case SLEEP:
        mode = Mode.OKOSU;
        callback.onStartOkosuMotion();
        updateFileName();
        break;
      case OKOSU:
        mode = Mode.OKIRU;
        callback.onFinishOkosuMotion();
        updateFileName();
        break;
      case OKIRU:
        mode = Mode.STAY;
        break;
    }
    index = 0;
    timer.setDelay(getInterval());
  }

  private enum Mode {
    STAY,
    NERU,
    SLEEP,
    OKOSU,
    OKIRU
  }

  public interface Callback {

    void onStartOkosuMotion();

    void onFinishOkosuMotion();
  }
}
