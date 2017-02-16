package learn.excel.Util;

import java.awt.Color;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFColor;

import bean.SysGridField;
import learn.util.ClassUtil;

/**
 * @author chenmfa
 * @date 2017-2-10 09:50:35
 * @description POI工具类
 */
public class PoiUtil {
  
  /**
   * @TODO 导出基础参数
   */
  public static final Short DEFOULT_FONT_ZIZE = 10;//前端12像素,10号字体
  public static final String DEFOULT_FONT_NAME = "微软雅黑";
  public static final String GRID_ALIGIN_RIGHT_FIELD_TYPE[] = {"F","A","N"}; //需要右对齐的字段类型
  public static final String GRID_DEFAULT_TITLE = "默认标题";//默认的标题名称
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
  
  public Workbook importDataTOExcel(List<?> dataList, List<SysGridField> fieldList) 
      throws IllegalAccessException, IllegalArgumentException, 
      InvocationTargetException, IntrospectionException{
    Workbook workbook = new HSSFWorkbook();
    Sheet sheet = workbook.createSheet("导出报表");
    

    if(null != fieldList && fieldList.size()>0){
      CellStyle globalStyle = getCellStyleLeft(workbook);
      for(int i=0;i<fieldList.size();i++){
        SysGridField field = fieldList.get(i);
        //标题行
        Row titleRow = sheet.createRow(0);      
        Cell titlecell = titleRow.createCell(i);
        titlecell.setCellStyle(globalStyle);
        String alias = field.getFieldAlias();
        String fieldName = field.getFieldName();
        if(StringUtils.isNotBlank(alias)){
          titlecell.setCellValue(alias);
        }else{
          alias = GRID_DEFAULT_TITLE;
          titlecell.setCellValue(alias);
        }
        Integer fieldWidth = field.getFieldWidth();
        if(null != fieldWidth && fieldWidth.intValue()>0){
          sheet.setColumnWidth(i, getCellWidth(fieldWidth));
        }else{
          sheet.setColumnWidth(i, getCellWidth(alias.length()*3));
        }
        //标题行设置结束
        
        //开始填写数据内容
        if(null != dataList && dataList.size()>0){
          Row contentRow; 
          for(int m=1;m<dataList.size()+1;m++){
            Object object = dataList.get(m-1);
            contentRow = sheet.createRow(m);
            Cell contentcell = contentRow.createCell(i);
            contentcell.setCellStyle(globalStyle);
            contentcell.setCellValue(ClassUtil.getFieldValueFromObject(object, fieldName));
          }                
        }
      }
    }
    return workbook;
  }
  
  /**
   * @TODO 左对齐样式
   * @param wb
   */
  protected CellStyle getCellStyleLeft(Workbook workbook){
    CellStyle titleStyle = workbook.createCellStyle();
    titleStyle.setFont(this.getFontStyle(workbook));
    titleStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
    return titleStyle;
  }
  
  /**
   * @TODO 右对齐样式
   * @param wb
   */
  protected CellStyle getCellStyleRight(Workbook workbook){
    CellStyle tableStyle = workbook.createCellStyle();
    tableStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);//默认左对齐
    tableStyle.setFont(this.getFontStyle(workbook));
    return tableStyle;
  }
  
  /**
   * @TODO 获取默认字体样式
   * @param wb
   */
  protected Font getFontStyle(Workbook workbook){
    Font fontStyle  = workbook.createFont();
    fontStyle.setFontHeightInPoints(DEFOULT_FONT_ZIZE);
    fontStyle.setFontName(DEFOULT_FONT_NAME);
    return fontStyle;
  }
  /**
   * 像素转宽度(12号字体)
   */
  protected int getCellWidth(int pixel) {  
    return pixel/6 * 256;  
  }
}
