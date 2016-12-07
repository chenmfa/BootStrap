package learn.encryption.ssl;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

/**
 * @author chenmfa
 * @date 创建时间: 2016年6月23日 上午10:18:06
 * @description
 */
public class SSlPrivate {
	
	private static X509Certificate[] mX509Certificates;
	private static PrivateKey mPrivateKey;	
	public static void main(String[] args) throws Exception {
		initPrivateKeyAndX509Certificate();
  }
	
	private static void initPrivateKeyAndX509Certificate()
		throws Exception {
	 // 创建一个证书库，并将证书导入证书库
	KeyStore keyStore = KeyStore.getInstance("jks");
	keyStore.load(new FileInputStream(new File("D:\\tomcatcert_20161205_186\\client.ks")),
	"123456".toCharArray());
		Enumeration<?> localEnumeration;
		localEnumeration = keyStore.aliases();
		while (localEnumeration.hasMoreElements()) {
				String str3 = (String) localEnumeration.nextElement();
				mPrivateKey = (PrivateKey) keyStore.getKey(str3,
						"123456".toCharArray());
				if (mPrivateKey == null) {
					continue;
				} else {
					Certificate[] arrayOfCertificate = keyStore
							.getCertificateChain(str3);
					mX509Certificates = new X509Certificate[arrayOfCertificate.length];
					for (int j = 0; j < mX509Certificates.length; j++) {
						mX509Certificates[j] = ((X509Certificate) arrayOfCertificate[j]);
					}
				}
			}
		}
}
