package com.gw.das.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

/**
 * 截图片
 */
public class ImageUtil {

	private final static Logger logger = Logger.getLogger(ImageUtil.class);

	public static InputStream identifying(String strRands) {
		int width = 80;
		int height = 25;
		BufferedImage buffImg = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		
		// 创建字体，字体的大小应该根据图片的高度来定。
		Font font = new Font("Fixedsys", Font.PLAIN, height - 2);
		// 设置字体。
		g.setFont(font);

		// 画边框。
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width - 1, height - 1);
		
		//随机产生10条干扰线，使图象中的认证码不易被其它程序探测到。
		g.setColor(Color.RED);
		Random random = new Random();
		for(int i = 0; i < 10; i++){
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		int x = width / (strRands.length() + 2);
		for (int i = 0; i < strRands.length(); i++) {
			// 得到随机产生的验证码数字。
			String strRand = strRands.substring(i, i + 1);
			// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
			int red = random.nextInt(255);
			int green = random.nextInt(255);
			int blue = random.nextInt(255);

			// 用随机产生的颜色将验证码绘制到图像中。
			g.setColor(new Color(red, green, blue));
			g.drawString(strRand, (i + 1) * x, height - 4);
		}
		g.dispose();
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		try {
			ImageIO.write(buffImg, "jpeg", out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

}