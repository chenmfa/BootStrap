package learn.office.excel.Util;

import java.awt.Color;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;

import learn.office.excel.bean.POIParam;
import learn.office.excel.bean.SysGridField;
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
  public static final Short HEADER_FONT_ZIZE = 15;//前端12像素,15号字体
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
  
  public static Workbook importDataTOExcel(List<?> dataList, List<SysGridField> fieldList,POIParam param) 
      throws IllegalAccessException, IllegalArgumentException, 
      InvocationTargetException, IntrospectionException{
    Workbook workbook = new HSSFWorkbook();
    Sheet sheet = workbook.createSheet(param.getTitle());
    //设置全局样式(用于小标题和数据)
    CellStyle globalStyle = getCellStyleLeft(workbook);
    //设置表头数据样式
    CellStyle HeaderStyle = getCellStyleHeader(workbook);
    //创建Excel表头和查询时间行    
    Row headerRow = sheet.createRow(0);
    Row timerRow = sheet.createRow(1);
    //设置表头样式
    headerRow.setHeight(getRowHeight());    
    Cell headerCell = headerRow.createCell(0);
    headerCell.setCellStyle(HeaderStyle);
    headerCell.setCellValue(param.getTitle());
    
    //设置起止时间样式    
    Cell startCell = timerRow.createCell(0);
    startCell.setCellStyle(globalStyle);
    startCell.setCellValue(null != param.getStartDate()?"开始时间："+param.getStartDate():"开始时间：");
    Cell endCell = timerRow.createCell(3);
    endCell.setCellStyle(globalStyle);
    endCell.setCellValue(null != param.getStartDate()?"结束时间："+param.getStartDate():"结束时间：");
    
    //设置合并单元格样式
    CellRangeAddress headerCra=new CellRangeAddress(0, 0, 0, (null!=fieldList && fieldList.size()>0)?fieldList.size():10);
    CellRangeAddress starttimerCra=new CellRangeAddress(1, 1, 0, 2);
    CellRangeAddress stoptimerCra=new CellRangeAddress(1, 1, 3, 5);
    sheet.addMergedRegion(starttimerCra);
    sheet.addMergedRegion(stoptimerCra);
    sheet.addMergedRegion(headerCra);
    
    if(null != fieldList && fieldList.size()>0){
      //标题行
      Row titleRow = sheet.createRow(2);
      for(int i=0;i<fieldList.size();i++){
        SysGridField field = fieldList.get(i);    
        Cell titlecell = titleRow.createCell(i);
        titlecell.setCellStyle(globalStyle);
        String alias = field.getFieldAlias();
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
      }
      //开始填写数据内容
      if(null != dataList && dataList.size()>0){
        Row contentRow; 
        for(int m=3;m<dataList.size()+3;m++){
          Object object = dataList.get(m-3);
          contentRow = sheet.createRow(m);
          for(int n=0;n<fieldList.size();n++){
            SysGridField field = fieldList.get(n);
            String fieldName = field.getFieldName();
            Cell contentcell = contentRow.createCell(n);
            contentcell.setCellStyle(globalStyle);
            contentcell.setCellValue(ClassUtil.getFieldValueFromObject(object, fieldName));
          }
        }                
      }
    }
    return workbook;
  }
  
  /**
   * @TODO 头部标题样式选择(水平，垂直都居中)
   * @param wb
   */
  protected static CellStyle getCellStyleHeader(Workbook workbook){
    CellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    headerStyle.setFont(getHeaderFontStyle(workbook));
    return headerStyle;
  }
  /**
   * @TODO 左对齐样式
   * HSSFCellStyle cellStyle = wb.createCellStyle();
              一、设置背景色:
    cellStyle.setFillForegroundColor((short) 13);//设置背景色
    cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
              二、设置边框:
    cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
    cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
    cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
    cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
              三、设置居中:
    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中
              四、设置字体:
    HSSFFont font = wb.createFont();
    font.setFontName("黑体");
    font.setFontHeightInPoints((short) 16);//设置字体大小
    HSSFFont font2 = wb.createFont();
    font2.setFontName("仿宋_GB2312");
    font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
    font2.setFontHeightInPoints((short) 12);
    cellStyle.setFont(font);//选择需要用到的字体格式
              五、设置列宽:
    sheet.setColumnWidth(0, 3766);
    //第一个参数代表列id(从0开始),第2个参数代表宽度值  参考 ："2012-08-10"的宽度为2500
              六、设置自动换行:
    cellStyle.setWrapText(true);//设置自动换行
   * @param wb
   */
  protected static CellStyle getCellStyleLeft(Workbook workbook){
    CellStyle titleStyle = workbook.createCellStyle();
    titleStyle.setFont(getFontStyle(workbook));
    titleStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
    return titleStyle;
  }
  
  /**
   * @TODO 右对齐样式
   * @param wb
   */
  protected static CellStyle getCellStyleRight(Workbook workbook){
    CellStyle tableStyle = workbook.createCellStyle();
    tableStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);//默认左对齐
    tableStyle.setFont(getFontStyle(workbook));
    return tableStyle;
  }
  
  /**
   * @TODO 获取默认字体样式
   * @param wb
   */
  protected static Font getFontStyle(Workbook workbook){
    Font fontStyle  = workbook.createFont();
    fontStyle.setFontHeightInPoints(DEFOULT_FONT_ZIZE);
    fontStyle.setFontName(DEFOULT_FONT_NAME);
    return fontStyle;
  }
  
  /**
   * @TODO 获取头部字体样式(大小:HEADER_FONT_ZIZE 字体:DEFOULT_FONT_NAME)
   * @param wb
   */
  protected static Font getHeaderFontStyle(Workbook workbook){
    Font fontStyle  = workbook.createFont();
    fontStyle.setFontHeightInPoints(HEADER_FONT_ZIZE);
    fontStyle.setFontName(DEFOULT_FONT_NAME);
    return fontStyle;
  }
  /**
   * 像素转宽度(12号字体)
   */
  protected static int getCellWidth(int pixel) {  
    return pixel * 36;  
  }
  
  /**
   * 获取头部表格高度
   */
  protected static short getRowHeight() {  
    return (short)(150*6);  
  }
}
