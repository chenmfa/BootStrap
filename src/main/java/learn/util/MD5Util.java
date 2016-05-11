package learn.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	public final static String MD5(String s) throws NoSuchAlgorithmException {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        byte[] btInput = s.getBytes();
        // ??��??MD5???�?�?�???? MessageDigest 对象
        MessageDigest mdInst = MessageDigest.getInstance("MD5");
        // 使�?��??�????�??????��?��??�?
        mdInst.update(btInput);
        // ??��??�????
        byte[] md = mdInst.digest();
        // ???�????�???��????????�???��??�?�?串形�?
        int j = md.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(MD5Util.MD5("20121221"));
        System.out.println(MD5Util.MD5("???�?"));
    }
}
