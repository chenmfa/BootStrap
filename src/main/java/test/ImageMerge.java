package test;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;

public class ImageMerge {
	
	public static void main(String[] args) throws IOException {
	  File[] files = new File[2];
	  files[0] = new File("C:\\Users\\Sub\\Desktop\\chenmfa.jpg");
	  files[1]= new File("C:\\Users\\Sub\\Desktop\\backP.jpg");
	  ImageMerge.imageClue(files,"C:\\Users\\Sub\\Desktop\\result.jpg",1);
  }
	
	public ImageMerge(){
		
	}
	
	public ImageMerge(File[] files, String destFileName, int scanSizePosition){

	}
	
	
	/**
	 * @param files destFileName
	 * @throws IOException
	 * @description 这个方法只能拼接宽度一样的图片，否则有可能照成数组越界
	 */
	public static void imageClue(File[] files, String destFileName, int scanSizePosition) throws IOException{
		if(null == files || files.length == 0){
			throw new IllegalArgumentException("需要拼接的文件数量为空");
		}else if(StringUtils.isBlank(destFileName)){
			throw new IllegalArgumentException("目标文件为空");
		}else if(scanSizePosition > files.length || scanSizePosition <0){
			scanSizePosition = 0;
		}
		// 根据file数组 生成一个新的Image
    BufferedImage[] imgs = new BufferedImage[files.length];
    imgs[scanSizePosition] = ImageIO.read(files[scanSizePosition]);
    int totalHeight=0;    
    int scanSizePicWidth =0;
    Map<Integer,Map<String,Integer>> picParamMap = new HashMap<Integer,Map<String,Integer>>();
    for (int i = 0; i < files.length; i++){
    		//将对应图片放入
    	if(scanSizePosition !=i){    		
    		imgs[i] = ImageIO.read(files[i]);
    		if(scanSizePosition != i){
    			imgs[i] = scaledInstance(imgs[i], imgs[scanSizePosition].getWidth(), 0);
    		}
    	}
  		Map<String,Integer> picParam = new HashMap<String,Integer>();
  		int picWidth= imgs[i].getWidth();
  		int picHeight = imgs[i].getHeight();
  		picParam.put("xcoordinate", 0);
  		picParam.put("ycoordinate", totalHeight);
  		picParam.put("width", picWidth);
  		picParam.put("height", picHeight);
  		picParamMap.put(i, picParam);        
      totalHeight += picHeight;
      
      System.out.println("图片宽度："+picWidth+",图片高度："+picHeight);
    }
    scanSizePicWidth = imgs[scanSizePosition].getWidth();    
    BufferedImage imgNew = new BufferedImage(scanSizePicWidth, totalHeight, BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < imgs.length; i++) {
  		int height = imgs[i].getHeight();
  		int width = imgs[i].getWidth();
  		Map<String, Integer> map = picParamMap.get(i);
  		System.out.println("MaxInteger:"+scanSizePicWidth+", ArrayLength:"+scanSizePicWidth * height);
  		int[] imgArray = new int[scanSizePicWidth * height];
      imgs[i].getRGB(0, 0, width, imgs[i].getHeight(), imgArray, 0, scanSizePicWidth);
      imgNew.setRGB(0, map.get("ycoordinate"), width, height, imgArray, 0, scanSizePicWidth);
    }
    File outFile = new File(destFileName);
    if(!outFile.exists()){
    	outFile.mkdirs();
    	outFile.createNewFile();
    }
    ImageIO.write(imgNew, "jpg", outFile);// 写图片
  }
	
	public static BufferedImage scaledInstance(BufferedImage input, int scaledWidth, int scaledHeight){
		int imageHeight = scaledHeight!=0?scaledHeight:input.getHeight();
		int imageWidth = scaledWidth!=0?scaledWidth:input.getWidth();

		Image scaledImage = input.getScaledInstance(imageWidth, imageHeight,Image.SCALE_DEFAULT);
		BufferedImage scaledBufferImage = new BufferedImage(imageWidth, imageHeight,BufferedImage.TYPE_INT_BGR);
		scaledBufferImage.getGraphics().drawImage(input, 0, 0, imageWidth, imageHeight, null); //画图
		return scaledBufferImage;
	}
}
