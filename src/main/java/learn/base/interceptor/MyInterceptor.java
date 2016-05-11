package learn.base.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import learn.util.RequestUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//implements HandlerInterceptor
public class MyInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(MyInterceptor.class);

  @Override
  public void afterCompletion(HttpServletRequest request,
      HttpServletResponse response, Object obj, Exception e) throws Exception {
  	logger.info("After Completion");
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response,
      Object obj, ModelAndView mv) throws Exception {
  	logger.info("Post Handle");
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object obj) throws Exception {
		String ipAddr = RequestUtil.getIpAddr(request);
		logger.info("本次请求来自于--"+ipAddr);
    return true;
  }

}
