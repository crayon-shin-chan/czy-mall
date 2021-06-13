package czy.spring.boot.starter.captcha.util;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

/**
 * 随机验证码工具类
 */
@Slf4j
public class RandImageUtil {

    /* 生成base64字符串 */
    public static String base64(String code, int width,int height,int lineCount,int lineWidth,String format,String prefix){

        try{
            BufferedImage image = getImageBuffer(code, width,height,lineCount,lineWidth);

            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            //写入流中
            ImageIO.write(image, format, byteStream);
            //转换成字节
            byte[] bytes = byteStream.toByteArray();
            //转换成base64串
            String base64 = Base64.getEncoder().encodeToString(bytes).trim();
            base64 = base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
            return prefix+base64;
        }catch (IOException ex){
            log.error("生成验证码图片失败",ex);
            return null;
        }

    }

    private static BufferedImage getImageBuffer(String code, int width,int height,int lineCount,int lineWidth){
        // 在内存中创建图象
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        final Graphics2D graphics = (Graphics2D) image.getGraphics();
        // 设定背景颜色
        graphics.setColor(Color.WHITE); // ---1
        graphics.fillRect(0, 0, width, height);
        // 设定边框颜色
//		graphics.setColor(getRandColor(100, 200)); // ---2
        graphics.drawRect(0, 0, width - 1, height - 1);

        final Random random = new Random();
        // 随机产生干扰线，使图象中的认证码不易被其它程序探测到
        for (int i = 0; i < lineCount; i++) {
            graphics.setColor(getRandColor(150, 200)); // ---3

            final int x = random.nextInt(width - lineWidth - 1) + 1; // 保证画在边框之内
            final int y = random.nextInt(height - lineWidth - 1) + 1;
            final int xl = random.nextInt(lineWidth);
            final int yl = random.nextInt(lineWidth);
            graphics.drawLine(x, y, x + xl, y + yl);
        }
        // 取随机产生的认证码
        for (int i = 0; i < code.length(); i++) {
            // 将认证码显示到图象中,调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            // graphics.setColor(new Color(20 + random.nextInt(130), 20 + random
            // .nextInt(130), 20 + random.nextInt(130)));
            // 设置字体颜色
            graphics.setColor(Color.BLACK);
            // 设置字体样式
//			graphics.setFont(new Font("Arial Black", Font.ITALIC, 18));
            graphics.setFont(new Font("Times New Roman", Font.BOLD, 24));
            // 设置字符，字符间距，上边距
            graphics.drawString(String.valueOf(code.charAt(i)), (23 * i) + 8, 26);
        }
        // 图象生效
        graphics.dispose();
        return image;
    }

    private static Color getRandColor(int fc, int bc) { // 取得给定范围随机颜色
        final Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }

        final int r = fc + random.nextInt(bc - fc);
        final int g = fc + random.nextInt(bc - fc);
        final int b = fc + random.nextInt(bc - fc);

        return new Color(r, g, b);
    }
}

