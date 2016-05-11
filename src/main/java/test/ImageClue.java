package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageClue {
	
	public static void main(String[] args) {
		ImageClue.clue();
  }
	
	public static void clue() {
		try {
		//首先读取两张图片
		// 对第一张图片做相同的处理
		File fileOne = new File("C:\\Users\\Sub\\Desktop\\chenmfa.jpg");
		BufferedImage ImageOne = ImageIO.read(fileOne);
		int width = ImageOne.getWidth();// 图片宽度
		int height = ImageOne.getHeight();// 图片高度
		
		// 读取第二张图片
		File fileTwo = new File("C:\\Users\\Sub\\Desktop\\backP.jpg");
		BufferedImage ImageTwo = ImageIO.read(fileTwo);
		int width2 = ImageTwo.getWidth();//图片宽度
		int height2 = ImageTwo.getHeight();//图片高度
		
		BufferedImage ImageBoard = new BufferedImage(width, height+height2,BufferedImage.TYPE_4BYTE_ABGR);
	
		Graphics g = ImageBoard.getGraphics();// 
		g.fillRect(0, 0, width, height);
		g.setColor(Color.blue);
		Font mFont = new Font("宋体", Font.PLAIN, 12);
		g.setFont(mFont);
		String text = "添加文字测试";
		g.drawString(text, 0, 10);
		
		
		// 从图片中读取RGB
		int[] ImageArrayOne = new int[width * height];
		ImageArrayOne = ImageOne.getRGB(0, 0, width, height, ImageArrayOne,
		0, width);
		int[] ImageArrayTwo = new int[width2 * height2];
		ImageArrayTwo = ImageTwo.getRGB(0, 0, width2, height2,
				ImageArrayTwo, 0, width);
		// 生成新图片
		BufferedImage ImageNew = new BufferedImage(width, height + height2,	BufferedImage.TYPE_INT_RGB);
		ImageNew.setRGB(0, 0, width, height, ImageArrayOne, 0, width);// 设置上半部分的RGB
		// ImageNew.setRGB(width,0,width,height,ImageArrayTwo,0,width);//设置右半部分的RGB
		ImageNew.setRGB(0, height2, width, height2, ImageArrayTwo, 0, width);// 设置下半部分的RGB
		File outFile = new File("C:\\Users\\Sub\\Desktop\\chenmfaout.jpg");
		ImageIO.write(ImageNew, "jpg", outFile);// 写图片
		} catch (Exception e) {
				e.printStackTrace();
		}
		}
}
