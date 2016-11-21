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

    public static int getRandNotEql(int max) {
        if (max == 0) {
            throw new IllegalArgumentException("This method doesn\'t support 0.");
        }
        int ret = (int)(Math.random() * max);
        if (ret == max) {
            ret = getRandNotEql(max);
        }
        return ret;
    }

    public static int getRandRange(int start, int end) {
        if (start > end) {
            System.err.println("start = [" + start + "], end = [" + end + "]");
            throw new IllegalArgumentException("Please end value is more large as start value.");
        }
        int ret = (int) (Math.random() * end);
        if (ret < start) {
            ret = getRandRange(start, end);
        }
        return ret;
    }
}
