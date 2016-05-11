package test;

import java.io.IOException;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class JSoupDemo {

	public static String getContentByJsoup(String url){  
	    String content="";  
	    try {  
	        System.out.println("time=====start");  
	        Date startdate=new Date();  
	        Document doc=Jsoup.connect(url)  
	        .data("jquery", "java")  
	        .userAgent("Mozilla")  
	        //.cookie("auth", "token")  
	        .timeout(50000)  
	        .get();  
	        Date enddate=new Date();  
	        Long time=enddate.getTime()-startdate.getTime();  
	        System.out.println("使用Jsoup耗时=="+time);  
	        System.out.println("time=====end");  
	        content=doc.toString();//获取iteye网站的源码html内容  
	        System.out.println(doc.title());//获取iteye网站的标题  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	    //System.out.println(content);   
	    return content;  
	}
	
	public static String getDivContentByJsoup(String content){  
	    String divContent="";  
	    Document doc=Jsoup.parse(content);  
	    Elements divs=doc.getElementsByClass("tb-rmb-num");
	    Elements select = doc.select("em");
	    divContent=divs.toString();  
	    System.out.println("div==="+divContent);
	    System.out.println("select==="+select.toString());
	    for (Element element : divs) {
	    	System.out.println("价格==="+element.html());
		}
	    return divContent;  
	}  
	
	public static void getLinksByJsoup(String divContent){  
        String abs="http://www.iteye.com/";  
        Document doc=Jsoup.parse(divContent,abs);  
        Elements linkStrs=doc.getElementsByTag("li");  
        System.out.println("链接==="+linkStrs.size());  
        for(Element linkStr:linkStrs){  
            String url=linkStr.getElementsByTag("a").attr("href");  
            String title=linkStr.getElementsByTag("a").text();  
            System.out.println("标题:"+title+" url:"+url);  
        }  
    } 
	@Test
	public void test(){
		String url="https://item.taobao.com/item.htm?spm=a1z0k.7385961.1997985097.d4918997.kzf0yf&id=523029474501&_u=p1im8dp5bb6c";  
        String HtmlContent=getContentByJsoup(url);  
        String divContent=getDivContentByJsoup(HtmlContent);  
        getLinksByJsoup(divContent); 
	}
}
