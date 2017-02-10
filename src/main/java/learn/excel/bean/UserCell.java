package learn.excel.bean;

import org.apache.poi.ss.usermodel.Cell;

/**
 * @author chenmfa
 * @date 2017-2-10 09:50:35
 * @description 用户自定义单元格对象
 */
public class UserCell {
  private Cell cell; //excel表格对象
  private int row;  //列位置
  private int col; //行位置
  private boolean show;//该单元格是否显示
  public Cell getCell() {
    return cell;
  }
  public void setCell(Cell cell) {
    this.cell = cell;
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
  public boolean isShow() {
    return show;
  }
  public void setShow(boolean show) {
    this.show = show;
  }
}
