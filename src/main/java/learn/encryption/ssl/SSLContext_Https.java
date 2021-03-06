/*
 * Copyright 2015 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package learn.encryption.ssl;

import java.io.BufferedReader;
import java.io.DataOutputStream;

/*import android.annotation.SuppressLint;

import com.dsm.nohttpdemo.App;*/

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created in Jan 31, 2016 8:03:59 PM.
 *
 * @author Yan Zhenjie.
 * @author chenmfa
 * @description SSLContext 设置https双向验证
 */
@SuppressWarnings("deprecation")
public class SSLContext_Https extends DefaultHttpClient{
  private static SSLContext sslContext = null;	
	
		public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException {
//			getSSLContext2("D:\\https_dsmzg_2018\\dsm-server-2018.cer","D:\\SubFile\\JOB_BACKUP\\DevelopmentDocuments\\HTTPS证书\\https_dsmzg_2017\\https-dsmclient.p12","clientkey@dsm2017");
			getSSLContext2("D:\\SubFile\\JOB_BACKUP\\开发文档\\HTTPS证书\\https_dsmzg_2017\\https-dsmserver.cer",
					"D:\\SubFile\\JOB_BACKUP\\开发文档\\HTTPS证书\\https_dsmzg_2017\\https-dsmclient.p12","clientkey@dsm2017");
      //SSLSession session = sslContext.createSSLEngine("192.168.1.186", 443).getSession();
      
      
      // 创建URL对象 
			String url = "https://192.168.1.186:4437/xiaodi/ads/getLockAdverList?account=18668165280";
			Map<String,String> params = new HashMap<String,String>();
			params.put("account", "18668165280");
			post(url, 10, params);
//			String result = post(url, params);
//			System.out.println(result);
//      URL myURL = new URL("https://192.168.1.186:4437/xiaodi/server/reloadPartnerInfo");
//      URL myURL = new URL("https://192.168.1.115:4437/xiaodi/ads/getLockAdverList?account=18668165280&token=A04BCQULBDgEFB1BQk5cU1NRU19cQU1WWkBPCg0FAwIkCVpZWl1UVExTXFRDSFZSSVlPSkADGhw7HAoQEQMDRFhAWUJYV0hBVE4JAxQLCQlPQ1oiNig/KSsmSEBPGBsAFxkDEkBYSF1eTk1USVldU1FQSEBPFh0OMQhPXEBQSBE=");
      
    }
		
		 public static SSLContext getSSLContext2(String servercerfile,String clientkeyStore,String clientPass) {
       if (sslContext != null) {
           return sslContext;
       }
       try {
           // 加载证书流, 这里证书需要放在assets下
           //InputStream inputStream = App.getInstance().getAssets().open("serverkey.cer");
      	 	 InputStream inputStream =  new FileInputStream(new File(servercerfile));
           // 生成证书
           CertificateFactory cerFactory = CertificateFactory.getInstance("X.509");
           Certificate cer = cerFactory.generateCertificate(inputStream);
           // 加载证书到KeyStore
           KeyStore keyStore = KeyStore.getInstance("PKCS12");//在eclipse这边改成了jks，android用PKCS12不知道为什么没问题，待研究
           keyStore.load(null, null);
           keyStore.setCertificateEntry("trust", cer);

           // KeyStore加入TrustManagerFactory
           TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
           trustManagerFactory.init(keyStore);
           
           sslContext = SSLContext.getInstance("TLS");

           //初始化clientKeyStore(android端只支持bks)
           //KeyStore clientKeyStore = KeyStore.getInstance("BKS");
           KeyStore clientKeyStore = KeyStore.getInstance("jks");
           //clientKeyStore.load(App.getInstance().getAssets().open("clientkey.bks"), "123456".toCharArray());
           clientKeyStore.load(new FileInputStream(new File(clientkeyStore)), clientPass.toCharArray());

           // 把初始化clientKeyStore放入keyManagerFactory
           KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
           keyManagerFactory.init(clientKeyStore, clientPass.toCharArray());

           // 初始化SSLContext  trustManagerFactory.getTrustManagers()
           sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());//new TrustManager[]{trustManagers}、、
       } catch (Exception e) {
           e.printStackTrace();
       }

       return sslContext;
   }

    /**
     * @description java端的SSLContext
     * @description 拿到https证书, SSLContext (NoHttp已经修补了系统的SecureRandom的bug)。
     * @description 这里由于在client.ks里面导入了server端的公钥，加上客户端自己的密钥，
     * @description 所以可以用一个文件，实际上呢最好分开便于理解
     * @description 安卓端参见上面的getSSLContext2()
     */
    //@SuppressLint("TrulyRandom")
    public static SSLContext getSSLContext() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            // 加载证书流, 这里证书需要放在assets下
            InputStream inputStream = new FileInputStream(new File("D:\\tomcatcert\\server.ks"));
            		//App.getInstance().getAssets().open("srca.cer");

            // 生成证书
            CertificateFactory cerFactory = CertificateFactory.getInstance("X.509");
            

            // 加载证书到KeyStore
            KeyStore keyStore = KeyStore.getInstance("jks");
            keyStore.load(inputStream,"123456".toCharArray());
            //Certificate cer = cerFactory.generateCertificate(inputStream);
            Certificate cer = keyStore.getCertificate("clientKey");
            keyStore.setCertificateEntry("trust", cer);

            // 把KeyStore放入keyManagerFactory
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, "123456".toCharArray());

            // KeyStore加入TrustManagerFactory
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            // 初始化SSLContext
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sslContext;
    }

    /**
     * 如果不需要https证书.(NoHttp已经修补了系统的SecureRandom的bug)
     */
    public static SSLContext getDefaultSLLContext() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManagers}, new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslContext;
    }

    /**
     * 信任管理器
     */
    private static TrustManager trustManagers = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
          for(Certificate cer:chain){
            System.out.println(cer.toString());
          }
          
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    };
    
    public static void post(String url, int repeat, Map<String,String> params) 
        throws IllegalArgumentException, IllegalAccessException, IOException{
      for(int i = 0; i < repeat; i++){        
        String result = post(url, params);
        System.out.println(result);
      }
    }
    
    /**
     * 以Post方式向web接口请求数据,编码为utf-8
     * 
     * 超时时间5s,编码UTF8
     * 
     * @param actionUrl
     * @param params
     * @return
     * @throws InvalidParameterException
     * @throws NetWorkException
     * @throws IOException
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * @throws Exception
     */
    public static String post(String url, Map<String,String> params)
        throws IOException, IllegalArgumentException, IllegalAccessException {
      return post(url, params,5 * 1000);
    }
    
    /**
     * 以Post方式向web接口请求数据,编码为utf-8
     * 
     * 编码UTF8
     * 
     * @param actionUrl
     * @param params
     * @param timeout 超时时间  毫秒
     * @return
     * @throws InvalidParameterException
     * @throws NetWorkException
     * @throws IOException
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * @throws Exception
     */
    public static String post(String url, Map<String,String> params,int timeout)
        throws IOException, IllegalArgumentException, IllegalAccessException {
      if (url == null || url.equals(""))
        throw new IllegalArgumentException("url 为空");
      if (params == null)
        throw new IllegalArgumentException("params 参数为空");
      if(url.indexOf('?')>0){
        url = url + "&token=A04BCQULBDgEFB1BQk5cU1NRU19cQU1WWkBPCg0FAwIkCVpZWl1UVExTXFRDSFZSSVlPSkADGhw7HAoQEQMDRFhAWUJYV0hBVE4JAxQLCQlPQ1oiNig/KSsmSEBPGBsAFxkDEkBYSF1eTk1USVldU1FQSEBPFh0OMQhPXEBQSBE=";
      }else{
        url = url + "?token=A04BCQULBDgEFB1BQk5cU1NRU19cQU1WWkBPCg0FAwIkCVpZWl1UVExTXFRDSFZSSVlPSkADGhw7HAoQEQMDRFhAWUJYV0hBVE4JAxQLCQlPQ1oiNig/KSsmSEBPGBsAFxkDEkBYSF1eTk1USVldU1FQSEBPFh0OMQhPXEBQSBE=";
      }
      
      String sb2 = "";
      String BOUNDARY = java.util.UUID.randomUUID().toString();
      String PREFIX = "--", LINEND = "\r\n";
      String MULTIPART_FROM_DATA = "multipart/form-data";
      String CHARSET = "UTF-8";
      // try {
      URL uri = new URL(url);
      HttpsURLConnection conn = (HttpsURLConnection) uri.openConnection();
      // 设置超时
      conn.setConnectTimeout(timeout);
      conn.setReadTimeout(timeout);
      conn.setDoInput(true);
      conn.setDoOutput(true);
      conn.setUseCaches(false);
      conn.setRequestMethod("POST");
      conn.setRequestProperty("connection", "keep-alive");
      conn.setRequestProperty("Charsert", "UTF-8");
      conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
          + ";boundary=" + BOUNDARY);
      SSLSocketFactory ssf = sslContext.getSocketFactory(); 
      conn.setSSLSocketFactory(ssf);
      conn.setHostnameVerifier(HOSTNAME_VERIFIER);
      
      StringBuilder sb = new StringBuilder();
      for (Map.Entry<String, String> entry : params.entrySet()) {
        sb.append(PREFIX);
        sb.append(BOUNDARY);
        sb.append(LINEND);
        sb.append("Content-Disposition: form-data; name=\""
            + entry.getKey() + "\"" + LINEND);
        sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
        sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
        sb.append(LINEND);
        sb.append(entry.getValue());
        sb.append(LINEND);
      }

      DataOutputStream outStream = new DataOutputStream(
          conn.getOutputStream());
      outStream.write(sb.toString().getBytes("UTF-8"));
      InputStream in = null;

      byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
      outStream.write(end_data);
      outStream.flush();

      int res = conn.getResponseCode();
      if (res == 200) {
        in = conn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in,
            "UTF-8"));
        String line = "";
        for (line = br.readLine(); line != null; line = br.readLine()) {
          sb2 = sb2 + line;
        }
      }
      outStream.close();
      conn.disconnect();
      return sb2;
    }

    /**
     * 域名验证
     */
    public static final HostnameVerifier HOSTNAME_VERIFIER = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };
}
