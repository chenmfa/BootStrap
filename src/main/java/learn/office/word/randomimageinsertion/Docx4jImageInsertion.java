package learn.office.word.randomimageinsertion;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.docx4j.TraversalUtil;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.finders.RangeFinder;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.org.apache.poi.util.IOUtils;
import org.docx4j.wml.Body;
import org.docx4j.wml.CTBookmark;
import org.docx4j.wml.Document;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.R;

public class Docx4jImageInsertion {
  public static void main(String[] args) throws Exception {
    // 模板文件路径
    String templatePath = "template.docx";
    // 生成的文件路径
    String targetPath = "target.docx";
    // 书签名
    String bookmarkName = "bookmark";
    // 图片路径
    String imagePath = "image.jpg";

    // 载入模板文件
    WordprocessingMLPackage wPackage = WordprocessingMLPackage.load(new FileInputStream(templatePath));
    // 提取正文
    MainDocumentPart mainDocumentPart = wPackage.getMainDocumentPart();
    Document wmlDoc = (Document) mainDocumentPart.getJaxbElement();
    Body body = wmlDoc.getBody();
    // 提取正文中所有段落
    List<Object> paragraphs = body.getContent();
    // 提取书签并创建书签的游标
    RangeFinder rt = new RangeFinder("CTBookmark", "CTMarkupRange");
    new TraversalUtil(paragraphs, rt);

    // 遍历书签
    for (CTBookmark bm:rt.getStarts()) {
        // 这儿可以对单个书签进行操作，也可以用一个map对所有的书签进行处理
        if (bm.getName().equals(bookmarkName)){                
            // 读入图片并转化为字节数组，因为docx4j只能字节数组的方式插入图片
            InputStream is = new FileInputStream(imagePath);
            byte[] bytes = IOUtils.toByteArray(is);
            // 穿件一个行内图片
            BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wPackage, bytes);

             // createImageInline函数的前四个参数我都没有找到具体啥意思，，，，
             // 最有一个是限制图片的宽度，缩放的依据
            Inline inline = imagePart.createImageInline(null, null, 0,1, false, 800);
            // 获取该书签的父级段落
            P p = (P)(bm.getParent());

            ObjectFactory factory = new ObjectFactory();
            // R对象是匿名的复杂类型，然而我并不知道具体啥意思，估计这个要好好去看看ooxml才知道
            R run = factory.createR();
            // drawing理解为画布？
            Drawing drawing = factory.createDrawing();
            drawing.getAnchorOrInline().add(inline);
            run.getContent().add(drawing);
            p.getContent().add(run);
        }
    }
    wPackage.save(new FileOutputStream(targetPath));
  }
}
