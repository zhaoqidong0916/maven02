package com.haitai.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 生成固定二维码的工具类   可以再二维码上嵌套图片
 */
public class ZXingCode {
    private static final int QRCOLOR = 0xFF000000; // 默认是黑色
    private static final int BGWHITE = 0xFFFFFFFF; // 背景颜色

    private static final int WIDTH = 200; // 二维码宽
    private static final int HEIGHT = 200; // 二维码高

    // 用于设置QR二维码参数
    private static Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>() {
        private static final long serialVersionUID = 1L;

        {
            put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
            put(EncodeHintType.CHARACTER_SET, "utf-8");// 设置编码方式
            put(EncodeHintType.MARGIN, 0);
        }
    };


    /**
     * 生成带logo的二维码图片
     * @param logoFile logo的图片路径
     * @param codeFile 生成的二维码存放路径
     * @param qrUrl 需要生成的二维码信息
     */
    public static void drawLogoQRCode(File logoFile, File codeFile, String qrUrl) {
        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            BitMatrix bm = multiFormatWriter.encode(qrUrl, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
            BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

            // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
                }
            }

            int width = image.getWidth();
            int height = image.getHeight();
            if (Objects.nonNull(logoFile) && logoFile.exists()) {
                // 构建绘图对象
                Graphics2D g = image.createGraphics();
                // 读取Logo图片
                BufferedImage logo = ImageIO.read(logoFile);
                // 开始绘制logo图片
                g.drawImage(logo, width * 2 / 5, height * 2 / 5, width * 2 / 10, height * 2 / 10, null);
                g.dispose();
                logo.flush();
            }

            image.flush();

            ImageIO.write(image, "png", codeFile); // TODO
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 这里是调用上面生成二维码方法的
     * @param args
     * @throws WriterException
     */
    public static void main(String[] args) throws WriterException {
        File logoFile = new File("C:/Users/asus/Desktop/123/2.jpg");
        File QrCodeFile = new File("C:/Users/asus/Desktop/123/123456.jpg");
        String url = "https://blog.csdn.net/weixin_38407595/article/details/87254224?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522160552862219724838525620%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fall.%2522%257D&request_id=160552862219724838525620&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~first_rank_v2~rank_v28-22-87254224.pc_first_rank_v2_rank_v28&utm_term=java%E5%AE%9E%E7%8E%B0%E4%BA%8C%E7%BB%B4%E7%A0%81%E5%A5%97%E5%8D%B0&spm=1018.2118.3001.4449";
        String note = "访问连接";
        drawLogoQRCode(null, QrCodeFile, url);

    }
}
