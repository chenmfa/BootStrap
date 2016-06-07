package learn.encoding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ResolveFileEncoding {

		public static void main(String[] args) throws IOException {
			File file = new File("D:\\workspace\\BootStrap\\src\\main\\webapp\\test.jsp");  
			InputStream in= new java.io.FileInputStream(file);  
			byte[] b = new byte[3];  
			in.read(b);  
			in.close();  
			if (b[0] == -17 && b[1] == -69 && b[2] == -65)  
			    System.out.println(file.getName() + "：编码为UTF-8");  
			else  
			    System.out.println(file.getName() + "：可能是GBK，也可能是其他编码");  
    }
}
