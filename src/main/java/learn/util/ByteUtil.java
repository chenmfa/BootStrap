package learn.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteUtil {
  private static final Logger logger = LoggerFactory.getLogger(ByteUtil.class);
	/**
	 * 
	 * 获得设备UUID
	 * 
	 * @param uuidByte
	 * @return
	 */
	public static String getUUID(byte[] uuidByte) {

		String strs = "";
		try {
			strs = new String(uuidByte, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		  logger.error("根据字节码获取设备的UUID出错",e);
		}
		return strs;
	}

	/**
	 * 
	 * 获得设备UUID---数字
	 * 
	 * @param uuidByte
	 * @return
	 */
	public static String getUuidByNum(byte[] uuidByte) {

		String strs = "";
		try {
			for (int i = 0; i < uuidByte.length; i++) {
				strs = strs + Integer.toHexString(uuidByte[i] & 0xFF) + "-";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strs.substring(0, strs.length() - 1);
	}

	/**
	 * UUIDStr获得byte数组
	 * 
	 * @param uuidStr
	 * @return
	 */
	public static byte[] getUuidByte(String uuidStr) {

		String[] strs = uuidStr.split("-");
		byte[] uuidb = new byte[8];
		try {
			for (int i = 0; i < strs.length; i++) {
				uuidb[i] = (byte) Integer.parseInt((strs[i]), 16);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uuidb;
	}

	/**
	 * 
	 * 获得设备UUID
	 * 
	 * @param uuidByte
	 * @return
	 */
	public static String getStr(byte[] strb) {

		String strs = "";
		try {
			strs = new String(strb, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return strs;
	}

	/**
	 * 
	 * 获得手机登录帐号
	 * 
	 * @param account
	 * @return
	 */
	public static String accountoruuid(byte[] account) {

		String strs = "";
		try {
			strs = new String(account, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return strs;
	}

	/**
	 * 打印接受到的信息
	 * 
	 * @param buf
	 */
	public static void printMsg(ByteBuf buf) {
		System.out.println("");
		System.out.print("接受到数据为：");
		byte[] msgByte = new byte[buf.readableBytes()];
		buf.readBytes(msgByte);
		for (int c = 0; c < msgByte.length; c++) {
			System.out.print(String.format("%02x ", msgByte[c] & 0xFF));
		}
	}

	/**
	 * 通过value获得map里的key
	 * 
	 * @param map
	 * @param values
	 * @return
	 */
	public static String getKey(Map<String, String> map, String values) {
		String key = "";
		for (Map.Entry<String,String> entry : map.entrySet()) {
			if (entry.getValue() == values) {
				key = (String) entry.getKey();
			}
		}
		return key;
	}

	/**
	 * 返回原始数据
	 * 
	 * @param ctx
	 * @param buf
	 */
	public static void sendMsg(ChannelHandlerContext ctx, ByteBuf buf) {
		ByteBuf bb = ctx.alloc().buffer();
		bb.writeBytes(buf);
		ctx.writeAndFlush(bb);
	}

	public static byte[] connTwoBytes(byte[] b1, byte[] b2) {
		int b1length = b1.length;
		int b2length = b2.length;
		byte[] nbt = new byte[b1length + b2length];

		for (int i = 0; i < b1length; i++) {
			nbt[i] = b1[i];
		}
		for (int i = 0; i < b2length; i++) {
			nbt[i] = b1[b1length + i];
		}
		return nbt;
	}

	public static byte[] getUtilByte(ByteBuf buf, String roomStr) {
		byte[] nbt = new byte[26];
		byte[] head = new byte[16];
		buf.getBytes(0, head);
		for (int i = 0; i < head.length; i++) {

			head[2] = 0x02;
			head[3] = 0x01;
			head[4] = 0x00;
			head[5] = 0x07;
			nbt[i] = head[i];

		}
		byte[] room = roomStr.getBytes();
		if (room.length >= 6) {
			nbt[16] = room[0];
			nbt[17] = room[1];
			nbt[18] = room[2];
			nbt[19] = room[3];
			nbt[20] = room[4];
			nbt[21] = room[5];
		} else {
			for (int i = 0; i < room.length; i++) {
				nbt[i + 16] = room[i];
			}
		}
		// nbt[16] = 0x00;
		// nbt[17] = 0x00;
		// nbt[18] = 0x00;
		// nbt[19] = 0x00;
		// nbt[20] = 0x00;
		// nbt[21] = 0x00;

		nbt[22] = 0x00;

		nbt[23] = 0x00;
		nbt[24] = 0x00;
		nbt[25] = (byte) 0xFE;
		return nbt;
	}

	/**
	 * 将IP地址转化成byte数组 如: 192.168.10.5== c0 a8 a 5
	 * 
	 * @param ipByte
	 * @return
	 */
	public static byte[] str2IpBytes(String ipStr) {
		byte ipbyte[] = new byte[4];
		String[] ipStrs = ipStr.split("\\.");
		for (int i = 0; i < ipStrs.length; i++) {
			ipbyte[i] = (byte) (Integer.parseInt(ipStrs[i]));
		}
		return ipbyte;
	}
	
	/**
	 * 根据输入的mac Byte数组的得到mac地址的字符串 如：c0 a8 a 5 fe ce = C0-A8-0A-05-FE-CE
	 * 
	 * @param macByte
	 * @return
	 */
	public static String macByteToString(byte[] macByte) {
		String macStr = "";
		for (int i = 0; i < macByte.length; i++) {
			String str = Integer.toHexString((macByte[i] & 0xff));
			if (str.length() == 1) {
				str = "0" + str;
			}
			macStr += str + ":";
		}
		return macStr.substring(0, macStr.length() - 1).toUpperCase();
	}
	
	/**
	 * 根据输入的IC card Byte数组的得到IC卡的字符串 如：c0 a8 a 5 fe ce = C0 A8 0A 05 FE CE
	 * 
	 * @param macByte
	 * @return
	 */
	public static String byteToString(byte[] macByte) {
		String macStr = "";
		for (int i = 0; i < macByte.length; i++) {
			String str = Integer.toHexString((macByte[i] & 0xff));
			if (str.length() == 1) {
				str = "0" + str;
			}
			macStr += str + " ";
		}
		return macStr.trim();
	}

	/**
	 * 根据输入的mac Byte数组的得UUID值
	 * 
	 * @param macByte
	 * @return
	 */
	public static String timeToString(byte[] b) {
		String timeStr = "";
		String year = b[1]+"";
		if(year.length()<2){
			year = "0"+year;
		}
		timeStr = b[0]+""+year+"-"+b[2]+"-"+b[3]+" "+b[4]+":"+b[5];
		return timeStr;
	}
	/**
	 * 根据输入的byte值
	 * 
	 * @param macByte
	 * @return
	 */
	public static String byteToHexString(byte[] b) {
		StringBuilder msg = new StringBuilder();		
		for(int k=0;k<b.length;k++){
			String temp = Integer.toHexString(b[k]  & 0xFF);
			if(Integer.toHexString(b[k]).length()==1){
			  msg.append("0");
			}
			msg.append(temp+" ");
		}
		return msg.substring(0, msg.length() - 1);
	}
}
