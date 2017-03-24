package learn.office.excel.bean;

import java.awt.Color;
import java.awt.Font;

import learn.office.excel.Util.PoiUtil;


/**
 * @author chenmfa
 * @date 2017-2-10 09:50:35
 * @description 用户自定义单元格对象
 */
public class Grid {
  private int x;
  private int y;
  private int width;
  private int height;
  private boolean show;
  private int row;
  private int col;
  private String text;
  private Color bgColor;
  private Font font = new Font("Consolas", Font.PLAIN, 12);
  private Color ftColor;
  public int getX() {
    return x;
  }
  public void setX(int x) {
    this.x = x;
  }
  public int getY() {
    return y;
  }
  public void setY(int y) {
    this.y = y;
  }
  public int getWidth() {
    return width;
  }
  public void setWidth(int width) {
    this.width = width;
  }
  public int getHeight() {
    return height;
  }
  public void setHeight(int height) {
    this.height = height;
  }
  public boolean isShow() {
    return show;
  }
  public void setShow(boolean show) {
    this.show = show;
  }
  public int getRow() {
    return row;
  }
  public void setRow(int row) {
    this.row = row;
  }
  public int getCol() {
    return col;
  }
  public void setCol(int col) {
    this.col = col;
  }
  public String getText() {
    return text;
  }
  public void setText(String text) {
    this.text = text;
  }
  public Color getBgColor() {
    return bgColor;
  }
  public void setBgColor(org.apache.poi.ss.usermodel.Color bgColor) {
    if(null != bgColor){
      this.bgColor = PoiUtil.poiColor2awtColor(bgColor);
    }
  }
  public Font getFont() {
    return font;
  }
  public void setFont(org.apache.poi.ss.usermodel.Font font) {
    if(null != font){      
      this.font = new Font(font.getFontName(),Font.PLAIN,font.getFontHeight()/20+2);
    }
  }
  public Color getFtColor() {
    return ftColor;
  }
  public void setFtColor(org.apache.poi.ss.usermodel.Color ftColor) {
    if(null != bgColor){
      this.ftColor = PoiUtil.poiColor2awtColor(ftColor);
    }
  }  
}
