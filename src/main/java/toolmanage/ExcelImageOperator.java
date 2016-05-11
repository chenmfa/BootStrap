package toolmanage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelImageOperator {
  
  public static void main(String[] args) throws Exception{
    saveImageFile("src\\note\\testImage.xls");
    WritePicToExcel07.importImageToExcel();
  }
  
  /**
   *@param filePath excel文件的地址 
   * @throws FileNotFoundException 
   */
  public static boolean saveImageFile(String filePath) throws FileNotFoundException{
    File f =new File(filePath);
    if(!f.exists()){
      f.mkdirs();
      return false;
    }
    System.out.println(f.getAbsolutePath());
    InputStream is =new BufferedInputStream(new FileInputStream(filePath));
    Workbook wb;
    try {
      //wb = new HSSFWorkbook(is);
      //wb = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(filePath)));
      String fileName = f.getName();
      if(fileName.endsWith(".xlsx")){
        wb = new XSSFWorkbook(is);
      }else if(fileName.endsWith(".xls")){
        wb =new HSSFWorkbook(is);
      }else{
        System.out.println("文件格式不正确");
        is.close();
        return false;
      }
      
      List<? extends PictureData> pictureData = wb.getAllPictures();
      Iterator<? extends PictureData> iterator = pictureData.iterator();
      Sheet sheet =wb.getSheetAt(0);
      if(wb instanceof HSSFWorkbook){
        //这里这么写的原因是测试另一种写法
        saveWord2003(sheet,pictureData);
      }else{
        while(iterator.hasNext()){
          PictureData next = iterator.next();
          savePicture(next.hashCode(), next);
        }
      }
      Sheet sheet0 = wb.getSheetAt(0);
      Row row = sheet0.getRow(0);
      Cell cell0 =row.getCell(0);    
      int cellType = cell0.getCellType();
      CellStyle cellStyle = cell0.getCellStyle();
      System.out.println(cellType);
      System.out.println(cellStyle.getDataFormatString());
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return true;
  }
  
  public static void savePicture(int index, PictureData picData){
    String ext = picData.suggestFileExtension();
    byte[] by = picData.getData();
    try {
      OutputStream out =new BufferedOutputStream(new FileOutputStream(".\\src\\note\\"+(index+1)+"."+ext));
      out.write(by);
      out.flush();
      out.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public static void  saveWord2003(Sheet sheet,List<? extends PictureData> pictureData){
    HSSFPatriarch patriarch = ((HSSFSheet)sheet).getDrawingPatriarch();
    List<HSSFShape> children = patriarch.getChildren();
    Iterator<HSSFShape> itShape = children.iterator();
    int i=0;
    while(itShape.hasNext()){
       HSSFShape shape = itShape.next();
       //HSSFAnchor anchor = shape.getAnchor();
       HSSFClientAnchor anchor = (HSSFClientAnchor)shape.getAnchor();
       if(shape instanceof HSSFPicture){
         HSSFPicture pic = (HSSFPicture)shape;
         //int row =anchor.getRow1();
         System.out.println(i+"--->"+anchor.getRow1()+" :"+anchor.getCol1());
         int picIndex = pic.getPictureIndex();
         HSSFPictureData picData = (HSSFPictureData)pictureData.get(picIndex-1);
         System.out.println(i + "---->"+ picData.getMimeType()+":"+ picData.getPictureType()+"---->"+picData.getFormat());
         savePicture(picIndex,picData);
         i++;
       }
    }
  }

}

class WritePicToExcel07 {
  
  public static void importImageToExcel() throws Exception {
   /* 写EXCEL，将目标excel中图片写入到新的excel中 */
   String basePath = ".\\src\\note\\";

  Workbook wb = new XSSFWorkbook();
   
   FileInputStream fis = new FileInputStream(basePath + "2.png");
   byte[] bytes = IOUtils.toByteArray(fis);
   int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
   fis.close();
   
   Sheet sheet = wb.createSheet("sheet1");
   //创建一个顶级容器
   Drawing drawing = sheet.createDrawingPatriarch();
   CreationHelper helper = wb.getCreationHelper();
   ClientAnchor anchor = helper.createClientAnchor();
   anchor.setCol1(3);
      anchor.setRow1(2);
      Picture pict = drawing.createPicture(anchor, pictureIdx);
      //auto-size picture relative to its top-left corner
      pict.resize();//该方法只支持JPEG 和 PNG后缀文件
      String file = "生成的EXCEL.xls";
      if(wb instanceof XSSFWorkbook) file += "x";
      FileOutputStream fos = new FileOutputStream(basePath+file);
   
//   Row row = sheet.createRow(0);//生成第一行
//   row.createCell(0).setCellValue("A");
//   row.createCell(1).setCellValue("B");
   wb.write(fos);
   fos.close();
  }
 }


