package learn.util;

import java.io.IOException;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * @author chenmf13021
 * @date 2015年12月25日
 * @description 由于xml不能生成standalone属性，采用重构XMLWriter的writeDeclaration来解决
 */
public class XMLWriterUtil extends XMLWriter{
  
  @Override
  protected void writeDeclaration() throws IOException {
    //XMLWriter 还支持文件路径保存的方式，最后使用XMLWriter.write(xml), XMLWriter.close()就好了
    //XMLWriter output = new XMLWriter(new FileOutputStream(new File("path")));  
    //output.setEscapeText(false);
    OutputFormat format = getOutputFormat();
    String encoding = format.getEncoding();

    if (!format.isSuppressDeclaration()) {
        if (encoding.equals("UTF8")) {
            writer.write("<?xml version=\"1.0\"");

            if (!format.isOmitEncoding()) {
                writer.write(" encoding=\"UTF-8\"");
            }

            writer.write("?>");
        } else {
            writer.write("<?xml version=\"1.0\"");

            if (!format.isOmitEncoding()) {
                writer.write(" encoding=\"" + encoding + "\"");
            }

            writer.write("standalone=\"standalone\" ?>");
        }

        if (format.isNewLineAfterDeclaration()) {
            println();
        }
    }
    
  }
}
