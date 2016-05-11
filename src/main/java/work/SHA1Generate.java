package work;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class SHA1Generate {
  
  public static void main(String[] args) 
      throws NoSuchAlgorithmException, IOException, InvalidKeyException,
      NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException,
      InvalidKeySpecException {
    SHA1Generate sha1 = new SHA1Generate();
    //System.out.println(sha1.generateSha1("E:\\迅雷下载\\cn_windows_7_ultimate_with_sp1_x64_dvd_u_677408.iso"));
    List<String> encodeStr = sha1.encryptAES("woshi2","xbjMFC@hundsun.com");
    System.out.println(encodeStr);
    System.out.println(sha1.decryptAES(encodeStr,"xbjMFC@hundsun.com"));
  }
  
  /**
   * @description 获取sha1值
   * @param path
   * @return String
   * @throws
   */
  public String generateSha1(String path) 
      throws NoSuchAlgorithmException{
    File file = new File(path);
    if(!file.exists()){
      return "文件不存在";
    }
    StringBuffer sb = new StringBuffer();
    BufferedInputStream bis = null;
    try {
      bis =new BufferedInputStream(new FileInputStream(file));
      MessageDigest digest;
      //MD5,SHA, 如果是String的hua,仅仅只要digest.update(String.getBytes())就好了;
      digest = MessageDigest.getInstance("SHA-1");
      
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
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
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
  
  public List<String> encryptAES(String content,String password) 
      throws UnsupportedEncodingException, NoSuchAlgorithmException, 
      NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
      BadPaddingException, InvalidKeySpecException{
    //这里有两种方法，所以把这个放在了前面
    byte[] byteContent = content.getBytes("UTF-8");
    
    //(AES加密)里是通过密钥生成器生成128位密钥,也可以采用默认的方法
    KeyGenerator kgen = KeyGenerator.getInstance("AES");
    kgen.init(128, new SecureRandom(password.getBytes()));
    SecretKey key = kgen.generateKey();
    byte[] encodeFormat = key.getEncoded();
    SecretKeySpec keySpec =new SecretKeySpec(encodeFormat,"AES");
    Cipher cipher = Cipher.getInstance("AES");   
    cipher.init(Cipher.ENCRYPT_MODE, keySpec);
    byte[] result = cipher.doFinal(byteContent);
    
    //(DES加密)采用DESedeKeySpec 生成自定义原始密码的密钥
    DESKeySpec desKeySpec = new DESKeySpec((password.getBytes()));
    SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
    SecretKey generateSecret = factory.generateSecret(desKeySpec);
    Cipher cipher2 = Cipher.getInstance("DES");    
    cipher2.init(Cipher.ENCRYPT_MODE, generateSecret, new SecureRandom());
    byte[] result2 = cipher2.doFinal(byteContent);
    
    String AES_String = Base64.encodeBase64String(result);
    String DES_String = Base64.encodeBase64String(result2);
    List<String> encodeStr = new ArrayList<String>();
    encodeStr.add(AES_String);
    encodeStr.add(DES_String);
    return encodeStr;//也有默认的不用byteContent的
  }
  
  public List<String> decryptAES(List<String> encodeStr, String password) 
      throws UnsupportedEncodingException, NoSuchAlgorithmException, 
      NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
      BadPaddingException, InvalidKeySpecException{
    byte[] AES_String = Base64.decodeBase64(encodeStr.get(0));
    byte[] DES_String = Base64.decodeBase64(encodeStr.get(1));
    //(AES解密)里是通过密钥生成器生成128位密钥,也可以采用默认的方法
    KeyGenerator kgen = KeyGenerator.getInstance("AES");
    kgen.init(128, new SecureRandom(password.getBytes()));
    SecretKey key = kgen.generateKey();
    byte[] encodeFormat = key.getEncoded();
    SecretKeySpec keySpec =new SecretKeySpec(encodeFormat,"AES");
    Cipher cipher = Cipher.getInstance("AES");   
    cipher.init(Cipher.DECRYPT_MODE, keySpec);
    byte[] result = cipher.doFinal(AES_String);
    
    //(DES解密)采用DESedeKeySpec 生成自定义原始密码的密钥
    DESKeySpec desKeySpec = new DESKeySpec((password.getBytes()));
    SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
    SecretKey generateSecret = factory.generateSecret(desKeySpec);
    Cipher cipher2 = Cipher.getInstance("DES");    
    cipher2.init(Cipher.DECRYPT_MODE, generateSecret, new SecureRandom());
    byte[] result2 = cipher2.doFinal(DES_String);
    
    String AES_Origin = new String(result);
    String DES_Origin = new String(result2);
    List<String> decodeStr = new ArrayList<String>();
    decodeStr.add(AES_Origin);
    decodeStr.add(DES_Origin);
    return decodeStr;//也有默认的不用byteContent的
  }
}
