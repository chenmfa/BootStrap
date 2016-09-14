package test;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenmfa
 * @date 创建时间: 2016年9月9日 下午2:09:42
 * @description
 */
public class AtomicTest {
	public static void main(String[] args) throws IOException {
	  AtomicInteger aint = new AtomicInteger();
	  System.out.println(aint.get());
	  System.out.println(aint.getAndIncrement());
	  System.out.println(aint.getAndDecrement());
	  System.out.println(aint.compareAndSet(1, 3));
	  BufferedWriter bfr = new BufferedWriter(new FileWriter("r"));
	  bfr.write("sss");
  }

}
