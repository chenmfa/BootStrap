package learn.qrcode;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import learn.BaseLearn;

/**
 * @author Sub
 * @description 生成带logo的二维码
 */
public class ZXingLogoQrcode extends BaseLearn{
	
	static Logger log = LoggerFactory.getLogger(ZXingLogoQrcode.class);
	
	public BufferedImage encodeLogoToImage(File sourceQrcodeImage, File logo){
		BufferedImage qrcodeImage = null;
		
		if(!sourceQrcodeImage.isFile() && !logo.isFile()){
			log.error("入参有非图片文件，不能转码 ");
			return qrcodeImage;
		}
		
		try {
	    qrcodeImage = ImageIO.read(sourceQrcodeImage);
	    Graphics2D g2d = qrcodeImage.createGraphics();
	    BufferedImage bi = ImageIO.read(logo);
	    int logoWidth = (bi.getWidth()>(qrcodeImage.getWidth()/5))?qrcodeImage.getWidth()/5:bi.getWidth(null);
	    int logoHeight = (bi.getHeight()>(qrcodeImage.getHeight()/5))?qrcodeImage.getHeight()/5:bi.getHeight(null);
	    //二维码图片大小减去logo大小
	    int x = (qrcodeImage.getWidth()-logoWidth)/2;
	    int y = (qrcodeImage.getHeight()-logoHeight)/2;
	    
	    g2d.drawImage(bi, x, y, logoWidth, logoHeight, null);
	    g2d.drawRoundRect(x, y, logoWidth, logoHeight, 15,15);
	    
	    g2d.setStroke(new BasicStroke(2));
	    g2d.setColor(Color.white);
	    g2d.drawRect(x, y, logoWidth, logoHeight);
	    g2d.dispose();
	    
	    bi.flush();
	    qrcodeImage.flush();
    } catch (IOException e) {
	    e.printStackTrace();
    }
		
		return qrcodeImage;
	}
	
	public boolean writeImageToFile(BufferedImage image, String formatName, File output){
		boolean success =false;
		try {
	    success = ImageIO.write(image, formatName, output);
    } catch (IOException e) {
	    log.error("写入二维码到文件失败", e);
    }
		return success;
	}
	
	public boolean writeImageToStream(BufferedImage image, String formatName, OutputStream output){
		boolean success =false;
		try {
	    success = ImageIO.write(image, formatName, output);
    } catch (IOException e) {
	    log.error("写入二维码到文件失败", e);
    }
		return success;
	}
	
	public static void main(String[] args) {
		ZXingLogoQrcode logoCode = new ZXingLogoQrcode();
		String clasPath =ZXingQrcode.class.getResource("/").getPath();	
		File sourceQrcodeImage = new File(clasPath+"output/qrCode.jpg");
		File logo = new File(clasPath+"output/logo.png");
		BufferedImage bufferedImage = logoCode.encodeLogoToImage(sourceQrcodeImage, logo);
		File output = new File(clasPath+"output/logogqrCode.jpg");
		logoCode.writeImageToFile(bufferedImage, "jpg", output);
  }
}
