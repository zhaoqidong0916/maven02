package com.haitai.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * 把二维码添加到指定图片上的工具类
 */
public class mergeImage {

    /**
     * 把指定的二维码图片添加到背景图片上（工具类）
     * @param bigPath  背景图片的存放地址
     * @param smallPath  镶嵌的二维码存放的地址
     * @param x 距离背景图片左上方的X距离
     * @param y  距离背景图片左上方的y距离  （注意：这里是以背景图片左上方为基准的，而且想要移动到指定位置记住要  X—（减去）二维码的X值，Y-（减去）二维码的Y值）
     * @throws IOException
     */
    public static void mergeImage(String bigPath, String smallPath, String x, String y) throws IOException {

        try {
            BufferedImage small;
            BufferedImage big = ImageIO.read(new File(bigPath));
            if (smallPath.contains("http")) {

                URL url = new URL(smallPath);
                small = ImageIO.read(url);
            } else {
                small = ImageIO.read(new File(smallPath));
            }

            Graphics2D g = big.createGraphics();

            float fx = Float.parseFloat(x);
            float fy = Float.parseFloat(y);
            int x_i = (int) fx;
            int y_i = (int) fy;
            g.drawImage(small, x_i, y_i, small.getWidth(), small.getHeight(), null);
            g.dispose();
            ImageIO.write(big, "png", new File(bigPath));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        try {
            mergeImage.mergeImage("C:/Users/asus/Desktop/123/2.jpg", "C:/Users/asus/Desktop/123/123456.jpg", "860", "500");
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}
