package com;

import java.io.File;

import com.heavenlake.wordapi.Document;

public class java2Word {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		java2Word java2Word = new java2Word();
	}
	public java2Word(){
		Document doc = null;
		try {
			doc = new Document();
			doc.open("E:/test.doc");//打开E盘中的word文档
			doc.find("柱状图");//把图片插到word文档“柱状图”文字位置中
			File imageFile = new File("E:/test.jpg");
			doc.insert(imageFile);
			doc.close(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
