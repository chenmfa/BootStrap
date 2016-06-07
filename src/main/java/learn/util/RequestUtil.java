package learn.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(RequestUtil.class);
	
	/**
	 * @description 获取request 的来源
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("HTTP_CLIENT_IP");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		//对于本地地址,有时候会显示成ipv6的
		if(null != ip && (ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1"))){
		  InetAddress address = null;
		  try {
	      address = InetAddress.getLocalHost();
	      ip = address.getHostAddress();
      } catch (UnknownHostException e){
      	ip= "127.0.0.1";
      }
		}
		return ip;
	}
	
	/**
	 * @description 获取访问ip的mac地址
	 * @param request
	 * @return
	 */
	public static String getMacAddress(HttpServletRequest request){
		try {
	    String ip = getIpAddr(request);	    
	    String LOOP_ADDRESS = "127.0.0.1";
	    String localIp = InetAddress.getLocalHost().getHostAddress();
	    if(LOOP_ADDRESS.equals(ip) || localIp.equals(ip)){
	    	byte[] hardwareAddress;
	        InetAddress address = InetAddress.getLocalHost();
	        hardwareAddress = NetworkInterface.getByInetAddress(address).getHardwareAddress();
	        StringBuilder sb = new StringBuilder();
	    		for(byte by :hardwareAddress){
	    			if(sb.length()>0){
	    				sb.append("-");
	    			}
	    			String hexString = Integer.toHexString(by & 0xFF);
	    			sb.append(hexString.length()==1?"0"+hexString:hexString);
	    		}
	    		return sb.toString().toUpperCase();		
	    }else{
	    	//Process process = Runtime.getRuntime().exec("nbtstat -a "+ ip);
	    	Process process = Runtime.getRuntime().exec("arp -a ");
	    	process.waitFor();
	    	BufferedReader bfr =new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
	    	String line ="";		
		    //String macPrefix = "MAC 地址 =";
	    	String macPrefix = ip;
	    	while((line = bfr.readLine()) != null){
	        //logger.info(line);
	    		int index= line.indexOf(macPrefix);
	    		if(index !=-1){
	    			String macAddress = line.substring(index + macPrefix.length()).trim().toUpperCase();  
	    			return macAddress;
	    		}
	    	}
	    	return "";
	    }
    } catch (IOException |InterruptedException e) {
      logger.error("获取MAC地址出错",e);
      return "";
    }
	}

}
