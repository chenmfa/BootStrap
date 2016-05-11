package toolmanage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.extractor.ExcelExtractor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * 
 * 在以前开发相关luence的搜索时候需要将各种文件转换为文本类型，
 *  获取内容，将内容创建相关的索引，在检索时候，查询相关的索引。
 *  起到高效快捷的方式，本文讲解excel2003和excel2007内容信息的抽取方式。
 *  
 * 
 * @Title: 
 * @Description: 实现针对excel2003和excel 2007 的内容抽取方式 简单快捷
 * @Copyright:Copyright (c) 2011
 * 
 * @Date:2015-04-13
 * @author  chenmfa
 * @version 1.0
 */
public class ExcelTextExtractor {
     @SuppressWarnings("resource")
    public static void main(String[] args) {
        String filename="c:\\Users\\chenmf13021\\Desktop\\指数证券导入模板.xlsx";
       InputStream inp;
       boolean isExcel2003=false;
      try {
        //创建相关的文件流对象
        inp = new FileInputStream(filename);
          //声明相关的工作薄对象
        Workbook wb =null;
          //声明相关的excel抽取对象
          ExcelExtractor extractor=null;
          if(isExcel2003)//针对2003版本
          {
            //创建excel2003的文件文本抽取对象
            wb=new HSSFWorkbook(new POIFSFileSystem(inp));
            extractor =new org.apache.poi.hssf.extractor.ExcelExtractor((HSSFWorkbook)wb);
          }else{ //针对2007版本
            wb = new  XSSFWorkbook(inp);
            //创建excel2007的文件文本抽取对象
            extractor =new XSSFExcelExtractor((XSSFWorkbook)wb);
          }
          
          extractor.setFormulasNotResults(false);
          //是否抽象sheet页的名称
          extractor.setIncludeSheetNames(true);
          //是否抽取cell的注释内容
          extractor.setIncludeCellComments(true);
          //获取相关的抽取文本信息
          String text = extractor.getText();
          //
          System.out.println("抽取文本的内容如下 ="+text);
          
      } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      
    }
}
