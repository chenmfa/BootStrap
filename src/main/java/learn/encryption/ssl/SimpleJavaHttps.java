package learn.encryption.ssl;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * @author chenmfa
 * @date 创建时间: 2016-6-21 下午4:11:18
 * @description java端获取ssl双向验证
 */
public class SimpleJavaHttps {
  public static void main(String[] args) {
    try {
	    System.setProperty("javax.net.ssl.trustStore", "D:\\SubFile\\JOB_BACKUP\\DevelopmentDocuments\\HTTPS证书\\https_dsmzg_2017\\https-dsmclient.ks");
	    System.setProperty("javax.net.ssl.trustStorePassword","clientkey@dsm2017");
	    
	    System.setProperty("javax.net.ssl.keyStore", "D:\\SubFile\\JOB_BACKUP\\DevelopmentDocuments\\HTTPS证书\\https_dsmzg_2017\\https-dsmserver.ks");
      System.setProperty("javax.net.ssl.keyStorePassword","serverkey@dsm2017");
      //System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
	    // 主机名称验证策略
	    // 主机名称验证策略
      HttpsURLConnection.setDefaultHostnameVerifier(new TrustAnyVerifier());

	    String address = "https://192.168.1.186:4437/xiaodi/user/login";
	    URL url = new URL(address);
	    URLConnection conn = url.openConnection();
	    InputStream input = conn.getInputStream();
	    byte[] by = new byte[1024];
	    int read=0;
	    while((read=input.read(by)) != -1){
	    	String str = new String(by);
	    	System.out.println(str);
	    }
    } catch (Exception e){
	    e.printStackTrace();
    }
  }  
}

class TrustAnyVerifier implements HostnameVerifier {
	 
  public boolean verify(String hostname, SSLSession session) {
      System.out.println(">>> " + hostname + " " + session);
      return true;
  }
}