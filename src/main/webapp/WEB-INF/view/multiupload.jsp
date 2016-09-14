<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/contain/head.jsp" %>
<title>多文件上传HTML5实例</title>
<body>
<!--  -->
<p >本页面将在<a>10</a>秒后跳转</p>
<!-- 上传空间div -->
<div id="upload"></div>
</body>
<%@ include file="/WEB-INF/view/contain/bottom.jsp" %>
<script type="text/javascript">

	setInterval("count()",1000);
	function count(){
		var num = $('p a').html();
		$('p a').html(--num);
	}
</script>
</html>