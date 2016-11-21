package com.sf_lolitahag.motion;

import com.sf_lolitahag.Utils;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import javax.swing.*;
import java.awt.*;

public class Furin extends AbstractMotion {

    private static final int AXIS_X = 200;
    private static final int AXIS_Y = 155;
    private static final int PAINT_INTERVAL = 500;
    private static final int PAINT_INTERVAL_MAX = 1500;
    private static final String[] IMAGE_LIST = {"furin01", "furin02", "furin03", "furin03"};
    private Timer mTimer = new Timer(PAINT_INTERVAL, (e) -> updateFileName());

    public Furin() {
        mAxisX = AXIS_X;
        mAxisY = AXIS_Y;

        Class tmpClass = getClass();
        mCache = CACHE_MANAGER.createCache(Furin.class.getSimpleName(),
                CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Image.class, ResourcePoolsBuilder.heap(100)).build());
        for (String fileName : IMAGE_LIST) {
            mCache.put(fileName, Utils.getImageFromResources(tmpClass, fileName));
        }

        updateFileName();
        mTimer.start();
    }

    private void updateFileName() {
        mFileName = IMAGE_LIST[Utils.getRandNotEql(IMAGE_LIST.length)];
        mTimer.setDelay(Utils.getRandRange(PAINT_INTERVAL, PAINT_INTERVAL_MAX));
    }
}
