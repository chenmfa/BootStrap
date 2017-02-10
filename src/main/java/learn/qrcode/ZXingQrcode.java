package learn.qrcode;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EnumMap;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * @author Sub
 * @description 生成二维码
 */
public class ZXingQrcode {
	
	//二维码颜色
	private static int qrcodeColor = 0xFF000000;//0xFF000000的颜色为红色
	//二维码背景色（指的是不填充时的色）
	private static int backgroundColor = 0xFFFFFFFF; //0xFFFF0000
	
	//默认的高度和宽度
	private int width = 320;
	
	private int height = 320;
	
	private EnumMap<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
	
	/**	二维码的纠错级别(排错率),4个级别： 
	 *	L (7%)、 
	 *	M (15%)、 
	 *	Q (25%)、 
	 *	H (30%)(最高H) 
	 *	纠错信息同样存储在二维码中，纠错级别越高，纠错信息占用的空间越多，
	 *	那么能存储的有用讯息就越少；共有四级； 
	 *	选择M，扫描速度快。 
	 */
	public ZXingQrcode(){
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		//二维码边框大小：1,2,3,4(最大)
		hints.put(EncodeHintType.MARGIN, 1);
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.DATA_MATRIX_SHAPE, SymbolShapeHint.FORCE_RECTANGLE);
		//设置最大和最小值，现已失效
		//hints.put(EncodeHintType.MAX_SIZE, 500);
		//hints.put(EncodeHintType.MIN_SIZE, 150);
	}
	
	public ZXingQrcode(int width, int height){		
		this();
		this.width = width;
		this.height = height;
	}
	
	public ZXingQrcode(int width, int height, int qrcodeColor, int backgroundColor){
		this(width, height);
		ZXingQrcode.qrcodeColor = qrcodeColor;
		ZXingQrcode.backgroundColor = backgroundColor;
	}
	
	
	public BufferedImage encodeImage(String contents) throws WriterException{
		BufferedImage image = null;
		BitMatrix matrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();	 
		// 画图	 
		g2d.setColor(new Color(backgroundColor));//new Color(255,0,0)
		g2d.setStroke(new BasicStroke(1)); 
		g2d.drawRect(0, 0, width, height);
		//释放对象	 
		g2d.dispose();
		int matrixWidth = matrix.getWidth();
		int matrixHeight = matrix.getHeight();
		for(int x=0;x< matrixWidth; x++){
			for(int y =0; y< matrixHeight; y++){
				image.setRGB(x, y, matrix.get(x, y)?qrcodeColor:0xFF0000);
			}
		}
		
		return image;
	}
	
	/**
	 * @param contents 二维码内容
	 * @param file 写到文件名
	 * @param formatName 格式
	 * @return 
	 */
	public boolean writeToFile(String contents,File file, String formatName){
		try {
	    BufferedImage image = encodeImage(contents);
	    ImageIO.write(image, formatName, file);
    } catch (WriterException | IOException e) {	   
	    e.printStackTrace();
	    return false;
    }
		return true;
	}
	
	/**
	 * @param contents 二维码内容
	 * @param file 写到流
	 * @param formatName 格式
	 * @return 
	 */
	public boolean writeToStream(String contents, OutputStream stream,	String formatName){
		try {
	    BufferedImage image = encodeImage(contents);
	    ImageIO.write(image, formatName, stream);
    } catch (WriterException | IOException e) {	   
	    e.printStackTrace();
	    return false;
    }
		return true;
	}
	
	public static void main(String[] args) {
		ZXingQrcode zq = new ZXingQrcode();
		//用.或者不加参数获取类路径，用/获取classPath 路径
		String clasPath =ZXingQrcode.class.getResource("/").getPath();		
		String contents ="欢迎使用充电设备";
		File f = new File(clasPath+"output/qrCode.png");
		if(!f.getParentFile().exists()){
			f.mkdirs();
		}
		zq.writeToFile(contents, f, "png");
  }
}
