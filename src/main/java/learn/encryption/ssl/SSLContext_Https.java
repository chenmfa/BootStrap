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

/*import android.annotation.SuppressLint;

import com.dsm.nohttpdemo.App;*/

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created in Jan 31, 2016 8:03:59 PM.
 *
 * @author Yan Zhenjie.
 * @author chenmfa
 * @description SSLContext 设置https双向验证
 */
public class SSLContext_Https {
  private static SSLContext sslContext = null;	
	
		public static void main(String[] args) {
			getSSLContext2();
    }
		
		 public static SSLContext getSSLContext2() {
       if (sslContext != null) {
           return sslContext;
       }
       try {
           // 加载证书流, 这里证书需要放在assets下
           //InputStream inputStream = App.getInstance().getAssets().open("serverkey.cer");
      	 	 InputStream inputStream =  new FileInputStream(new File("D:\\tomcatcert\\serverkey.cer"));
           // 生成证书
           CertificateFactory cerFactory = CertificateFactory.getInstance("X.509");
           Certificate cer = cerFactory.generateCertificate(inputStream);
           // 加载证书到KeyStore
           KeyStore keyStore = KeyStore.getInstance("jks");//在eclipse这边改成了jks，android用PKCS12不知道为什么没问题，待研究
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
           clientKeyStore.load(new FileInputStream(new File("D:\\tomcatcert\\client.ks")), "123456".toCharArray());

           // 把初始化clientKeyStore放入keyManagerFactory
           KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
           keyManagerFactory.init(clientKeyStore, "123456".toCharArray());

           // 初始化SSLContext
           sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
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
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    };

    /**
     * 域名验证
     */
    public static final HostnameVerifier HOSTNAME_VERIFIER = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };
}
