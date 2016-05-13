package learn.push.getui;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.PushSingleException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;


/**
 * @author chenmfa
 * @description 传入参数： baiduChannelId,text,
 * @description 可选参数： transmissionType，(phoneType,account,nickName(判断ios还是android)),badge,contentAvailable,sound
 */
public class GeTuiPush implements Runnable{
	
	private static Logger logger = LoggerFactory.getLogger(GeTuiPush.class);
	
	private Map<String,String> properties;
	
	public GeTuiPush(Map<String,String> properties){
		this.properties = properties;
	}
	
	public static void main(String[] args) {
		new Thread(new GeTuiPush(new HashMap<String,String>())).start();
	  
  }
	
  public void run() {
  	push(properties);
  }
	
	public void push(Map<String, String> properties){
		properties.putAll(new HashMap<String,String>(){
			private static final long serialVersionUID = 330781199007134865L;
			{
				put("text", "101|15257121663|枫哥的世界|您的账号在其他地方上线，请重新登录，修改密码");
				put("openUrl","www.dessmann.com.cn");
				put("deviceType","ios");
				put("account","15257121663");
			}
		});
	  //useSSL表明是使用ssl传输还是普通http传输
	  IGtPush push = new IGtPush(Constants.gtAppKey, Constants.gtMasterSecret, false);
	  // host为域名。根据host来区分是http还是https􀜐􁦓
	  ///IGtPush push = new IGtPush(Constants.gtHost, Constants.gtAppKey, Constants.gtMasterSecret);
	  TransmissionTemplate template = GtTemplateFactory.getTransmissionTemplate(properties);
	  //LinkTemplate template = GtTemplateFactory.getLinkTemplate(properties);
	  SingleMessage singleMessage = new SingleMessage();
	  singleMessage.setOffline(true);
	  //设置消息有效时间为1个小时
	  singleMessage.setOfflineExpireTime(3600*1000);
	  singleMessage.setData(template);
	  //设置推送的网络，1为WIFI环境下，0为不限制网络
	  singleMessage.setPushNetWorkType(0);
	  Target target = new Target();
	  target.setAppId(Constants.gtAppId);
	  //CID暂时写死，后期换成baiduchannelId这个字段
	  if(StringUtils.isBlank(properties.get("baiduChannelId"))){	  	
	  	target.setClientId(Constants.gtCid);
	  }else{
	  	logger.info("获取到的cid："+properties.get("baiduChannelId"));
	  	target.setClientId(properties.get("baiduChannelId"));
	  }
	  //用户别名和cid只能任选一个来推送
	  //target.setAlias(alias);
	  IPushResult result = null;
    try {
	    result = push.pushMessageToSingle(singleMessage, target);
    } catch (PushSingleException e) {
	    logger.error(e.getMessage(),e);
	    result = push.pushMessageToSingle(singleMessage, target, e.getRequestId());
    } catch (Exception e){
    	logger.error(e.getMessage(),e);
    }
	  String msg = result.getResponse().toString();
    logger.info(msg);
    //return msg;
	}
}
