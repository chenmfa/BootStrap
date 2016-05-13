package learn.push.getui;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

/**
 * @author chenmfa
 * @description 个推通知模板
 */
public class GtTemplateFactory {
	
	private static Logger logger = LoggerFactory.getLogger(GtTemplateFactory.class);
	//默认标题
	private static String title = "小滴提醒";
	//默认文字内容
	//private static String text = "";
	//默认图标
	private static String logo = "icon.png";
	//默认图标的url
	private static String logoUrl = "";
	//默认点击通知后显示的网页,必填，否则将收不到消息
	private static String openUrl = "www.dessmann.com.cn";
	//默认响铃提醒
	private static String isRing = "true";
	//默认震动提醒
	private static String isVibrate = "true";
	//默认通知可以清除
	private static String isClearable = "true";
	
	private static String transmissionType = "2";
	
	private static String badge = "1";
	
	private static String contentAvailable = "1";
	
	private static String sound = "default";
	
	private GtTemplateFactory(){
	}
	
	/**
	 * @Description 这个原来用的是properties，现在改成map
	 *
	 */
	public static LinkTemplate getLinkTemplate(Map<String, String> map){
		LinkTemplate template = new LinkTemplate();
		Assert.notNull(map.get("openUrl"),"通知打开的地址为必填");
		//APPID和APPKEY
		template.setAppId(Constants.gtAppId);
		template.setAppkey(Constants.gtAppKey);
		//设置标题与消息内容
		template.setTitle(getProperty(map,"title", title));
		template.setText(getProperty(map,"text", ""));
		//设置通知栏显示图片
		template.setLogo(getProperty(map,"logo", logo));
		//设置通知栏网络图标，填写图标的url地址
		template.setLogoUrl(getProperty(map,"logoUrl", logoUrl));
		//设置通知是否响铃，震动或者可清除
		template.setIsRing(Boolean.parseBoolean(getProperty(map,"isRing", isRing)));
		template.setIsVibrate(Boolean.parseBoolean(getProperty(map,"isVibrate", isVibrate)));
		template.setIsClearable(Boolean.parseBoolean(getProperty(map,"isClearable", isClearable)));
		//设置打开的网址地址
		template.setUrl(getProperty(map,"openUrl", openUrl));
		return template;
	}
	
	public static TransmissionTemplate getTransmissionTemplate(Map<String, String> map){
		TransmissionTemplate template = new  TransmissionTemplate();
		template.setAppId(Constants.gtAppId);
		template.setAppkey(Constants.gtAppKey);
		template.setTransmissionType(Integer.parseInt(getProperty(map,"transmissionType",transmissionType)));
		String text = getProperty(map,"text","");
				
		if("ios".equalsIgnoreCase(getProperty(map,"deviceType","android"))){
			String[] msgArr = text.split("\\|");
			String url = "";
			String tips ="";
			if(msgArr.length>5){
				url = msgArr[4];
				tips = msgArr[5];
			}
			logger.info("The message is:"+text);
			/*String message ="{\"aps\":{\"alert\": \""+msgArr[3]+"\"},\"userid\":\""+getProperty(map,"account","")+"\"," +
					"\"nickname\":\""+getProperty(map,"nickName","")+"\",\"messageid\":\""+msgArr[0]+"\",\"macaddress\":\""+msgArr[1]+"\"," +
					"\"devicename\":\"\",\"url\":\""+url+"\",\"tips\":\""+tips+"\"}";*/
			text = text+"|"+getProperty(map,"account","")+"|"+getProperty(map,"nickName","")+"|"+url+"|"+tips;
			template.setTransmissionContent(text);
			APNPayload payload = new APNPayload();
			payload.setBadge(Integer.parseInt(getProperty(map,"badge",badge)));
			payload.setContentAvailable(Integer.parseInt(getProperty(map,"contentAvailable",contentAvailable)));
			payload.setSound(getProperty(map,"sound",sound));
			//payload.addCustomMsg(key, value);
			//设置触发特定的action或按钮显示，暂时设为无
			//payload.setCategory("");
			//APNPayload.SimpleMsg  设置通知消息体
			//payload.setAlertMsg(new APNPayload.SimpleAlertMsg("hello"));
			//􀨁􀙎􀽜􀭗􀖵􁊠􀓥􁘏
			//payload.setAlertMsg(getDictionaryAlertMsg());
			template.setAPNInfo(payload);
		}else{
			template.setTransmissionContent(text);
		}
		return template;
	}
	
	public static String getProperty(Map<String, String> map, String key,String defaultValue){
		String value = map.get(key);
		if(null == value){
			return defaultValue;
		}
		return value;
	}
}
