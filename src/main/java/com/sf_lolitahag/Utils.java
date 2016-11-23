package com.sf_lolitahag;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Utils {

    public static BufferedImage getImageFromResources(final Class Class, final String fileName) {
        if (fileName == null || fileName.equals("")) {
            return null;
        }

        BufferedImage image = null;
        try {
            image = ImageIO.read(Class.getResource("/" + fileName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (image == null) {
            System.err.println(fileName + " is not found. return null.");
        }
        return image;
    }

    public static int getRandInt(int max) {
        if (max == 0) {
            throw new IllegalArgumentException("This method doesn\'t support 0.");
        }
        int ret = (int)(Math.random() * max);
        if (ret == max) {
            ret = getRandInt(max);
        }
        return ret;
    }

    public static int getRandRange(int start, int end) {
        if (start > end) {
            System.err.println("startFireball = [" + start + "], end = [" + end + "]");
            throw new IllegalArgumentException("Please end value is more large as startFireball value.");
        }
        int ret = getRandInt(end + 1);
        if (ret < start) {
            ret = getRandRange(start, end);
        }
        return ret;
    }

    public static int getRandBaseCoe(int base, int coe) {
        return base + getRandInt(coe + 1);
    }
}
