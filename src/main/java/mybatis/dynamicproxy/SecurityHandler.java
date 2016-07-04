package mybatis.dynamicproxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;

import learn.annotation.CacheableService;
import learn.redis.RedisUtil;
import learn.util.MD5Encrypt;
import mybatis.datasource.DataSourceHolder;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class SecurityHandler /*implements MethodInterceptor*/{
	
  public Object checkSecurity(ProceedingJoinPoint npoint) throws Throwable{
  	System.out.println("Check cache begins");
    MethodSignature signature = (MethodSignature)npoint.getSignature();
    
    Class<?> declaringType = signature.getDeclaringType();
    
    Method method = signature.getMethod();
    Class<?> returnType = method.getReturnType();
    Type genericReturnType = method.getGenericReturnType();
    Object[] args = npoint.getArgs();
    StringBuilder sb = new StringBuilder();
    sb.append(declaringType.getName());
    sb.append(method.getName());
    //System.out.println(sb.toString());
    sb.append(JSON.toJSONString(args, SerializerFeature.DisableCircularReferenceDetect));
    String hashCode = MD5Encrypt.md5(sb.toString());
    //MessageDigest md=MessageDigest.getInstance("SHA1");
    //byte[] dig=md.digest(str.getBytes());
    //String hashCode = dig.toString();
    System.out.println(hashCode);
    Annotation[] annotations = method.getAnnotations();
    boolean needCache = false;
    if(null != annotations && annotations.length>0){
    	for(Annotation anno:annotations){
    		if(anno instanceof CacheableService){
    			needCache= true;
    			break;
    		}
    	}
    }
  	Object proceed = new Object();
  	RedisUtil util = new RedisUtil();
  	String hash = String.valueOf(hashCode);
    if(needCache){
    	if(util.isExists(hash)){
    		System.out.println("取缓存数据"+this.getClass());
    		String json = util.getCacheObject(hash);
    		Object cacheObject = null;
    		if(genericReturnType instanceof ParameterizedType){
    			//cacheObject = JSONArray.parseArray(json, returnType);
    			ParameterizedType pt = (ParameterizedType) genericReturnType;
    			Class<?> clz = (Class<?>)pt.getActualTypeArguments()[0];
    			cacheObject = JSON.parseArray(json, clz);
    		}else{
    			cacheObject = JSON.parseObject(json, returnType) ;
    		}
    		if(null == cacheObject){
    			System.out.println("缓存数据已过期，重新取数据库数据");
    			proceed = npoint.proceed();
    			if(null != proceed){
    				util.storeJSON(hash, JSON.toJSONString(proceed, SerializerFeature.DisableCircularReferenceDetect));
    			}
    			return proceed;
    		}
    		return cacheObject;
    	}
    } 	
  	proceed = npoint.proceed();
  	if(needCache){
  		//保存在缓存里，以备下次查询
  		System.out.println("保存查询结果至缓存。");
  		if(null != proceed){
				util.storeJSON(hash, JSON.toJSONString(proceed, SerializerFeature.DisableCircularReferenceDetect));
			}
  	}
    return proceed;
  }
  
  public void setDynamicDataSource(JoinPoint jp) {
  	MethodSignature signature = (MethodSignature)jp.getSignature();
    
    Method method = signature.getMethod();
    String methodName = method.getName();
    if(methodName.startsWith("add")||methodName.startsWith("create")  
        ||methodName.startsWith("del")||methodName.startsWith("edit")  
        ||methodName.startsWith("insert")||methodName.startsWith("save")  
        ||methodName.startsWith("update")||methodName.startsWith("modeify")){
    	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    	DataSourceHolder.setSlaveSource();
    }else{
    	DataSourceHolder.setMasterSource();
    }
  }
}
