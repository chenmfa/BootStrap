package test;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * @author chenmfa
 * @date 创建时间: 2016-6-21 下午4:11:18
 * @description
 */
public class SSLTest {
  public static void main(String[] args) {
    try {
	    System.setProperty("javax.net.ssl.trustStore", "D:\\tomcatcert\\client.ks");
	    System.setProperty("javax.net.ssl.trustStorePassword","123456");
	    
	    System.setProperty("javax.net.ssl.keyStore", "D:\\tomcatcert\\client.p12");
      System.setProperty("javax.net.ssl.keyStorePassword","123456");
      System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
	    // 主机名称验证策略
	    // 主机名称验证策略
      HttpsURLConnection.setDefaultHostnameVerifier(new TrustAnyVerifier());

	    String address = "https://192.168.1.115/lock/index.jsp";
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