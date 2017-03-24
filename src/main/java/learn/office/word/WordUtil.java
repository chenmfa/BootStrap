package learn.office.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * @author chenmfa
 * @description word 操作工具类
 */
public class WordUtil {
  public static void insertImage(XWPFParagraph paragraph,String imagePath,String imageName)
      throws InvalidFormatException, IOException{
    XWPFRun r = paragraph.createRun();
    FileInputStream imageFis = new FileInputStream(new File(imagePath));
    r.addPicture(imageFis, XWPFDocument.PICTURE_TYPE_PNG, imageName, 138*9525, 138*9525);
  }
}
