package learn.encryption.ssl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/** 
* @author chenmfa
* @version 创建时间：2017年5月19日 下午1:14:51 
* @description
*/
@SuppressWarnings("deprecation")
public class HttpsPost {
  public static void main(String[] args) throws 
  IOException, KeyStoreException, KeyManagementException, 
  UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException {
    //HttpClientBuilder  CloseableHttpClient
    DefaultHttpClient httpClient = new DefaultHttpClient();

    KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType()); 
    FileInputStream instream = new FileInputStream(new File("D:/server.jks"));
    try {
    trustStore.load(instream, "password".toCharArray());
    } finally {
    instream.close();
    }

    SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore,"password",trustStore);
    Scheme sch = new Scheme("https", socketFactory, 443);
    httpClient.getConnectionManager().getSchemeRegistry().register(sch);

    HttpGet httpget = new HttpGet("https://localhost:8443/");

    System.out.println("executing request" + httpget.getRequestLine());

    HttpResponse response = httpClient.execute(httpget);
    HttpEntity entity = response.getEntity();

    System.out.println("----------------------------------------");
    System.out.println(response.getStatusLine());
    if (entity != null) {
    System.out.println("Response content length: " + entity.getContentLength());
    }
    if (entity != null) {
    entity.consumeContent();
    }
    httpClient.getConnectionManager().shutdown();
  }
}
