package mybatis.dynamicproxy.impl;

import learn.annotation.CacheableService;

public class ProxyManagerImpl {
  
	@CacheableService
  public String proxyTest(String str){
    
    System.out.println("Proxy test succeed");
    return "ProxyManager-success";
  }
}
