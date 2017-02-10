package learn.excel.Util;

import java.awt.Color;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFColor;

/**
 * @author chenmfa
 * @date 2017-2-10 09:50:35
 * @description POI工具类
 */
public class PoiUtil {
  
  /**
   * @param org.apache.poi.ss.usermodel.color poi 颜色对象
   * @return Color 正常color对象
   */
  public static Color poiColor2awtColor(org.apache.poi.ss.usermodel.Color color){     
    java.awt.Color awtColor=null;
    if(color instanceof XSSFColor){     //.xlsx格式,word 2007及以上
        XSSFColor xc=(XSSFColor) color;
        String rgbHex=xc.getARGBHex();
        if(rgbHex!=null){
            awtColor=new Color(Integer.parseInt(rgbHex.substring(2),  16));
        }
    }else if(color instanceof HSSFColor){   //.xls格式,word 2003
        HSSFColor hc=(HSSFColor) color;
        short[] s=hc.getTriplet();
        if(s!=null){
            awtColor=new Color(s[0],s[1],s[2]);
        }
    }
    return awtColor;
}
}
