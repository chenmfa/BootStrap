package learn.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import learn.loginit.LogUtil;
import learn.push.getui.GeTuiPush;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/logger")
public class ActionTest {
	
	private final Logger  logger = LoggerFactory.getLogger(ActionTest.class);
	
	private static final ExecutorService executor = Executors.newFixedThreadPool(2);
	
	@RequestMapping("getLogger.json")
	@ResponseBody
	public String testLogger(){
		logger.info("Info test");
		logger.error("Logger Error Test");
		return "succeed";
	}
	
	@RequestMapping("sendMsg.json")
	@ResponseBody
	public String sendMsg(final String cid, HttpServletRequest request) throws Exception{

		Map<String, String> properties =new HashMap<String,String>(){
      private static final long serialVersionUID = 1L;
      {
      	put("baiduChannelId",cid);
      }
		};
		new Thread(new GeTuiPush(properties)).start();
		Callable<String> call = new GeTuiPush(properties);
		
//		FutureTask<String> task = new FutureTask<>(call);
//		executor.execute(task);
//		String string = task.get(2000, TimeUnit.MILLISECONDS);
		Future<String> future = executor.submit(call);
		String result = future.get(2000, TimeUnit.MILLISECONDS);
		return result;
	}
	
	@RequestMapping("logger.json")
	@ResponseBody
	public Object logger(int actionType,String action){
		if(actionType==1){
			List<org.apache.log4j.Logger> logList = LogUtil.getLogList();
			return JSON.toJSONString(logList);
		}else if(actionType==2){
			
		}
		return "sunncee";
	}
}
