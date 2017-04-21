package test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/** 
* @author chenmfa
* @version 创建时间：2017年3月30日 下午3:27:44 
* @description
*/
public class GenerateSongs {
  public static void main(String[] args) throws IOException {
    BufferedReader bis = new BufferedReader(new InputStreamReader(new FileInputStream(new File("D:\\豆瓣FM红心.txt"))));
    String str = null;
    StringBuilder sb = new StringBuilder();
    BufferedWriter writer = new BufferedWriter(new FileWriter(new File("D:\\豆瓣FM红心re.txt")));
    while((str = bis.readLine()) != null){
      String[] strArr = str.split("-");
      String author =strArr[0].trim();
      String album = strArr[1].trim();
      sb.append("<File>\r\n");
      sb.append("<MediaFileType>0</MediaFileType>\r\n");
      sb.append("<FileName>"+str+"</FileName>\r\n");
      sb.append("<FilePath>.</FilePath>\r\n");
      sb.append("<FileSize>3606056</FileSize>\r\n");
      sb.append("<Duration>225000</Duration>\r\n");
      sb.append("<Hash>24e52622272714e9b39b0e223bbe832b</Hash>\r\n");
      sb.append("<Lyric></Lyric>\r\n");
      sb.append("<File>\r\n");
      sb.append("<Bitrate>128000</Bitrate>\r\n");
      sb.append("<MandatoryBitrate>128000</MandatoryBitrate>\r\n");
      sb.append("</File>\r\n");
    }
    writer.write(sb.toString());
    writer.flush();
    writer.close();
  }
}
