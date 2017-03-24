package learn.office.word.image;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.sun.tools.doclets.formats.html.markup.DocType;

import learn.office.word.WordUtil;

public class ImageInsertion {
  
  public static void main(String[] args) throws InvalidFormatException, IOException {
    XWPFDocument doc = new XWPFDocument();
    XWPFParagraph paragraph = doc.createParagraph();
    paragraph.setIndentationLeft(26);
    //doc.addPictureData(imageFis, XWPFDocument.PICTURE_TYPE_PNG);
    for(int i=0;i<4;i++){
      WordUtil.insertImage(paragraph, "E:\\AC2336105.png", "test"+i);
    }
    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("E:\\word.docx")));
    doc.write(bos);
    bos.close();
  }
}
