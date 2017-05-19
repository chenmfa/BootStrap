package learn.encryption.ssl;

import java.security.KeyStore;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/** 
* @author chenmfa
* @version 创建时间：2017年5月19日 下午1:14:51 
* @description
*/
public class HttpsPost {
  public static void main(String[] args) {
    //HttpClientBuilder  CloseableHttpClient
    DefaultHttpClient httpclient = new DefaultHttpClient();

    KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType()); 
    FileInputStream instream = new FileInputStream(new File("D:/server.jks"));
    try {
    trustStore.load(instream, "password".toCharArray());
    } finally {
    instream.close();
    }

    SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore,"password",trustStore);
    Scheme sch = new Scheme("https", socketFactory, 443);
    httpclient.getConnectionManager().getSchemeRegistry().register(sch);

    HttpGet httpget = new HttpGet("https://localhost:8443/");

    System.out.println("executing request" + httpget.getRequestLine());

    HttpResponse response = httpclient.execute(httpget);
    HttpEntity entity = response.getEntity();

    System.out.println("----------------------------------------");
    System.out.println(response.getStatusLine());
    if (entity != null) {
    System.out.println("Response content length: " + entity.getContentLength());
    }
    if (entity != null) {
    entity.consumeContent();
    }
    httpclient.getConnectionManager().shutdown();
  }
}
