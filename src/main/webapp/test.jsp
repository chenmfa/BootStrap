<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page
  import="java.util.*,org.springframework.context.ApplicationContext"%>
<%@ page
  import="org.springframework.web.context.support.WebApplicationContextUtils"%>

<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://"
      + request.getServerName() + ":" + request.getServerPort()
      + path + "/";
%>
