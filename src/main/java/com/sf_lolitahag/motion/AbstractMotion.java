package com.sf_lolitahag.motion;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;

import java.awt.*;

/**
 * パネル上で表示するオブジェクトのBaseクラス
 * 必要に応じてオブジェクトのResources名とX軸、Y軸、影の有無をOverrideする
 */
public abstract class AbstractMotion {

    private static final String EMPTY = "";
    protected static final CacheManager CACHE_MANAGER = CacheManagerBuilder.newCacheManagerBuilder().build(true);
    protected int mAxisX = 0;
    protected int mAxisY = 0;
    protected boolean mIsShow = true;
    protected String mFileName = EMPTY;
    protected Cache<String, Image> mCache;

    public final int getAxisX() {
        return mAxisX;
    }

    public final int getAxisY() {
        return mAxisY;
    }

    public final Image getBodyImage() {
        if (mFileName.equals(EMPTY)) {
            return null;
        }
        return mCache.get(mFileName);
    }

    public final boolean isShow() {
        return mIsShow;
    }
}
