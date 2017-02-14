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
	
	public ActionTest(){
	  urcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    String pattern =  "classpath*:xmls/sqlmap/*/*.xml";//ResourceLoader.CLASSPATH_URL_PREFIX
    Resource[] resources = resolver.getResources(pattern);
		System.out.println("ActionTest---begin");
	}
	
	@RequestMapping("getLogger.json")
	@ResponseBody
	public String testLogger(){
		logger.info("Info test");
		logger.error("Logger Error Test");
		logger.debug("debugMsg");
		return "succeed";
	}
	
	/**
	 * @param cid 个推的用户id
	 * @param request httprequest请求
	 * @return String
	 * @throws Exception
	 * @description
	 */
	@RequestMapping("sendMsg.json")
	@ResponseBody
	public String sendMsg(final String cid, HttpServletRequest request) throws Exception{
		request.getParameter("");
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
	
	/**
	 * @param actionType
	 * @param action
	 * @return JSONString
	 * @description 修改运行时的日志级别，1为查看所有存在的日志，2为修改root日志级别
	 */
	@RequestMapping("logger.json")
	@ResponseBody
	public String logger(int actionType,String action){
		String result = "";
		switch(actionType){
			case 1:
				List<org.apache.log4j.Logger> logList = LogUtil.getLogList("");
				for(org.apache.log4j.Logger single: logList){
					System.out.println(single.getName()+"----"+single.getLevel().toString());
				}
				result = JSON.toJSONString(logList);
				break;
			case 2:
				result = JSON.toJSONString(LogUtil.getLogList(""));
				break;
			case 3:
				LogUtil.changeLogger("learn", action);
				result= "成功";
				break;
			default:
				result = "请求类型非法";
				break;
		}
		return result;
	}
}
