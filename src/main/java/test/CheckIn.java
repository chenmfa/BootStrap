package test;

import java.util.Scanner;


/**
 * @author chenmfa
 * @date 创建时间: 2016年8月26日 上午8:36:16
 * @description
 */
public class CheckIn {
	public static void main(String[] args) {
	  String[][] 人员数组= new String[][]{{"陈苗发","1团","1连","1组","23岁","浙江"},
	  		{"陈苗发2","1团","1连","2组","24岁","浙江2"}};
	  while(true){
	  	boolean exist=false;
	  	System.out.println("请输入人员姓名");
	  	Scanner can =new Scanner(System.in);
	  	String name =can.nextLine();
	  	for(int i=0;i<人员数组.length;i++){
	  		String[] infomation = 人员数组[i];
	  		String currentName =infomation[0];
	  		if(name.equals(currentName)){
	  			System.out.println("首长好，我是"+infomation[1]+infomation[2]+"的"+infomation[3]+"的"+name+"，今年"+infomation[4]+"，来自"+infomation[5]+"，报告完毕");
	  			exist=true;
	  			break;
	  		}
	  	}
	  	if(!exist){
	  		System.out.println(name+"不在本系统内");
	  	}
	  }
  }
	
}
