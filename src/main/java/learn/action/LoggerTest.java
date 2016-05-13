package learn.action;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import learn.push.getui.GeTuiPush;
import learn.util.RequestUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/logger")
public class LoggerTest {
	
	private final Logger  logger = LoggerFactory.getLogger(LoggerTest.class);
	
	@RequestMapping("getLogger.json")
	@ResponseBody
	public String testLogger(){
		logger.info("Info test");
		logger.error("Logger Error Test");
		return "succeed";
	}
	
	@RequestMapping("sendMsg.json")
	@ResponseBody
	public String sendMsg(final String cid, HttpServletRequest request){


		new Thread(new GeTuiPush(new HashMap<String,String>(){

      private static final long serialVersionUID = 1L;
      {
      	put("baiduChannelId",cid);
      }
			
		})).start();

		return "succeed";
	}
}
