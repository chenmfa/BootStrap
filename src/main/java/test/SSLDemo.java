package test;


import java.security.KeyStore;


import javax.net.ssl.SSLSocketFactory;


import org.apache.http.HttpResponse;

import org.apache.http.HttpStatus;

import org.apache.http.HttpVersion;

import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpPost;

import org.apache.http.conn.scheme.PlainSocketFactory;

import org.apache.http.conn.scheme.Scheme;

import org.apache.http.conn.scheme.SchemeRegistry;

import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;

import org.apache.http.params.BasicHttpParams;

import org.apache.http.params.HttpParams;

import org.apache.http.params.HttpProtocolParams;

import org.apache.http.protocol.HTTP;


public class SSLDemo {

 

  private static final String SERVER_IP = "10.0.2.2";  //本地IP

  private static final int SERVER_PORT = 443;    //端口号  

  private static final String CLIENT_KET_PASSWORD = "***"; //私钥密码        

  private static final String CLIENT_TRUST_PASSWORD = "***";//信任证书密码        

  private static final String CLIENT_AGREEMENT = "TLS";  //使用协议        

  private static final String CLIENT_KEY_MANAGER = "X509"; //密钥管理器        

  private static final String CLIENT_TRUST_MANAGER = "X509"; //信任证书管理器        

  private static final String CLIENT_KEY_KEYSTORE = "BKS"; //"JKS";//密库，这里用的是BouncyCastle密库        

  private static final String CLIENT_TRUST_KEYSTORE = "BKS"; //"JKS";//      

  private static final String URL = "https://10.0.2.2:443/payment";     

/*

//取得SSL的SSLContext实例

SSLContext sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);

 

//取得keyManageFactory和TrustManagerFactory的x509密钥管理实例

KeyManagerFactory keyManager = KeyManagerFactory.getInstance(CLIENT_KEY_MANAGER);

TrustManagerFactory trustManager = TrustManagerFactory.getInstance(CLIENT_TRUST_MANAGER);

 

//取得BKS密库实例

KeyStore kks = KeyStore.getInstance(CLIENT_KEY_KEYSTORE);

KeyStore tks = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);

 

//加载客户端证书和私钥，通过读取资源文件的方式读取密钥和信任证书

kks.load(context.getAssets().open("cacerts.bks"), CLIENT_KET_PASSWORD.toCharArray());

tks.load(context.getAssets().open(""), CLIENT_TRUST_PASSWORD.toCharArray());


 

//初始化密钥管理器

keyManager.init(kks, CLIENT_KET_PASSWORD.toCharArray());

 

//初始化SSLContext

sslContext.init(keyManager.getKeyManagers(), null, null);

 

//生成SSLSocket

//SSLSocket Client_sslSocket = (SSLSocket) sslContext.getSocketFactory().createSocket(SERVER_IP, SERVER_PORT);

*/

private String httpPost(String xmlStr) throws CommException {

  try {

 

  KeyStore kks = KeyStore.getInstance(KeyStore.getDefaultType());

  kks.load(context.getAssets().open("cacerts.bks"), CLIENT_KET_PASSWORD.toCharArray());

   

  SSLSocketFactory sslSocketFactory = new org.apache.http.conn.ssl.SSLSocketFactory(kks);

  sslSocketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

   

  HttpParams params = new BasicHttpParams();

  HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

  HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

   

  SchemeRegistry schReg = new SchemeRegistry();

  schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));

  schReg.register(new Scheme("https", sslSocketFactory, SERVER_PORT));

  ThreadSafeClientConnManager conMgr = new ThreadSafeClientConnManager(params, schReg);

   

  HttpClient httpClient = new DefaultHttpClient(conMgr, params);

   

  HttpPost httpRequest = new HttpPost(URL);

  StringEntity reqEntity = new StringEntity(xmlStr, HTTP.UTF_8);

  reqEntity.setContentType("text/xml;charset=UTF-8");

  reqEntity.setChunked(true);

  httpRequest.setEntity(reqEntity);

   

  httpRequest.getRequestLine().toString();

   

   

  HttpResponse httpResponse = httpClient.execute(httpRequest);

  if( httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK ){

    Log.v(CLASSTAG, "----------ok");

  }else{

    Log.v(CLASSTAG, "----------no ok");

  }

   

  } catch (Exception e) {

  Log.e(CLASSTAG, e.toString());

  }

  return xmlStr;

 

}

}