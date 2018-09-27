package learn.encryption.filehash;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* @author chenmfa
* @version 创建时间：2017年11月30日 上午8:55:52 
* @description
*/
public class FileHashUtil {
  @SuppressWarnings("unused")
  private static final Logger logger = LoggerFactory.getLogger(FileHashUtil.class);
  
  public static String sha1(String filepath) throws NoSuchAlgorithmException, IOException{
    return generateHash(filepath, "SHA-1");
  }
  public static String sha1(File file) throws NoSuchAlgorithmException, IOException{
    return generateHash(file, "SHA-1");
  }
  public static String MD5(String filepath) throws NoSuchAlgorithmException, IOException{
    return generateHash(filepath, "MD5");
  }
  public static String MD5(File file) throws NoSuchAlgorithmException, IOException{
    return generateHash(file, "MD5");
  }
  /**
   * @throws IOException 
   * @description 获取sha1值
   * @param path
   * @return String
   * @throws
   */
  public static String generateHash(String path, String algorithm) 
      throws NoSuchAlgorithmException, IOException{
    File file = new File(path);
    return generateHash(file, algorithm);
  }
  
  public static String generateHash(File file,String algorithm)
      throws NoSuchAlgorithmException, IOException{
    StringBuilder sb = new StringBuilder();
    BufferedInputStream bis = null;
    try {
      bis =new BufferedInputStream(new FileInputStream(file));
      MessageDigest digest;
      //MD5,SHA, 如果是String的话,仅仅只要digest.update(String.getBytes())就好了;
      digest = MessageDigest.getInstance(algorithm);
      
      byte[] buffer = new byte[1024*1024*10];
      int len=0;
      double total= 0;
      while((len=bis.read(buffer))!=-1){      
        digest.update(buffer,0,len);
        total +=len;
        System.out.println(total/(1024*1024*1024));
      }
      byte[] result = digest.digest();
      
      for(int i=0;i<result.length;i++){
        byte b = result[i];
        String hexString = Integer.toHexString(b & 0xFF);
        if(hexString.length()<-1){
          System.out.println("hexString is "+hexString);
          sb.append(0);
        }
        sb.append(hexString);     
      }      
    }finally{
      if(null !=bis){        
        try {
          bis.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return sb.toString().toUpperCase();
  }
}
