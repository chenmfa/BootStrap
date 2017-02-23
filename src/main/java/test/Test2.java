package test;

public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
    /* int a = 9/0;
     System.out.println("结果是"+a);*/
     System.out.println("select ta.md_work as worker, IFNULL(ta.md_item3,'') as content, IFNULL(ta.md_endtime,'') as install_time, IFNULL(ta.md_mendtime,'') as promise_time, IFNULL(ta.md_starttime,'') as realize_time, IFNULL(TIMESTAMPDIFF(MINUTE,ta.md_starttime,ta.md_endtime),0) as occupied_time, tb.dv_brand as brand,tb.dv_brandname as brandname,tb.dv_norms as norms, IFNULL(tb.dv_factoryCode,'') as factoryCode, md_clientaddress as address, md_clientphone as userphone, md_clientname as username, md_item4 as price, md_userremark as remark from re_mend ta LEFT JOIN re_deviceInfo tb on ta.md_qrcode = tb.dv_qrcode;".length());
		 isEquals("");
	}
	
	public static void  isEquals(String str){
		String msg = "abc";
		//if(msg.equals(str)){
		if(str.equals(msg)){
			//System.out.println("相等");
		}else{
			//System.out.println("不相等");
		}
		
	}

}
