package learn.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Encrypt {

	/**
	 * 
	 */
	public MD5Encrypt() {
		super();
	}

	public static String md5(String arg0) {
		MessageDigest msgDigest = null;
		try {
			msgDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("System doesn't support MD5 algorithm.");
		}
		try {
			msgDigest.update(arg0.getBytes());
		} catch (NullPointerException e) {

		}
		byte[] bytes = msgDigest.digest();
		byte tb;
		char low;
		char high;
		char tmpChar;
		String md5Str = new String();
		for (int i = 0; i < bytes.length; i++) {
			tb = bytes[i];
			tmpChar = (char) ((tb >>> 4) & 0x000f);
			if (tmpChar >= 10) {
				high = (char) (('a' + tmpChar) - 10);
			} else {
				high = (char) ('0' + tmpChar);
			}
			md5Str += high;
			tmpChar = (char) (tb & 0x000f);
			if (tmpChar >= 10) {
				low = (char) (('a' + tmpChar) - 10);
			} else {
				low = (char) ('0' + tmpChar);
			}
			md5Str += low;
		}
		return md5Str;
	}

	public static byte[] getFileMD5(File file) throws DigestException {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return digest.digest();
	}

	public static String MD5Encode(String sourceString) {
		String resultString = null;
		try {
			resultString = new String(sourceString);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byte2hexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}

	public static final String byte2hexString(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if ((bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString(bytes[i] & 0xff, 16));
		}
		return buf.toString();
	}

	public static void CreateMD5File(File srcF) throws DigestException {
		// TODO Auto-generated method stub
		byte[] fileMD5Code = MD5Encrypt.getFileMD5(srcF);
		FileInputStream in = null;
		FileOutputStream fo = null;
		try {
			in = new FileInputStream(srcF);
			byte b[] = new byte[in.available()];
			int temp = 0;
			int i = 0;
			while ((temp = in.read()) != -1) {
				b[i] = (byte) temp;
				i++;
			}
			fo = new FileOutputStream(srcF);

			byte[] srcb = new byte[fileMD5Code.length + b.length];

			for (int k = 0; k < fileMD5Code.length; k++) {
				srcb[k] = fileMD5Code[k];
			}
			for (int k = 0; k < b.length; k++) {
				srcb[k + fileMD5Code.length] = b[k];
			}
			fo.write(srcb);
			fo.flush();
			fo.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fo != null) {
				try {
					fo.close();
				} catch (IOException e1) {
				}
			}

		}
	}

}
