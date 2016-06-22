package test;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;

/**
 */
 class TrustAnyVerifiers implements HostnameVerifier {
	public boolean verify(String hostname, SSLSession session) {
		System.out.println(">>> " + hostname + " " + session);
		return true;
	}
 }
 

public class AccessHttpsResource {
	public static void main(String[] args) throws Exception {
		System.setProperty("javax.net.ssl.trustStore", "c:/client.jks");
		System.setProperty("javax.net.ssl.trustStorePassword","12345678");
		HttpsURLConnection.setDefaultHostnameVerifier(new TrustAnyVerifier());
		String address = "https://127.0.0.1:8443/";
		URL url = new URL(address);
		URLConnection conn = url.openConnection();
		InputStream input = conn.getInputStream();
		int result = input.read();
		System.out.println(result);
	}
}