<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/contain/head.jsp" %>
<title>JS 模拟 HTTP Ping</title>
<style>
html{
	height: 100%;
	overflow: hidden;
}
body{
	#background: #000;
	color: #C0C0C0;
	font-weight: bold;
	font-size: 14px;
	font-family: Lucida Console;
	height: 100%;
	margin: 0 0 0 5px;
}
#divContent{
	height: 90%;
	overflow: auto;
}
#txtTimeout{
	width: 40px;
}
button{
	margin-left: 10px;
}

.align-center{
	text-align:center;
}
</style>
<script type="text/javascript" src="../plugins/jquery-1.10.2.js"></script>
<script type="text/javascript" src="../js/common/common.js"></script>
</head>
<body>
<div id="divInput">
	<span>URL:</span>
	<input id="txtURL" type="text" />
	<span>Timeout:</span>
	<input id="txtTimeout" type="text" value="2000" />
	<input id="btnSwitch" type="button" value="Start" onclick="handleBtnClick()" />
	<hr/>
</div>
<div id="divContent"></div>
<%@ include file="/WEB-INF/view/contain/bottom.jsp" %>
<script>
	$.ping = function(option){
		var defaults = {
				beforeSend:undefined,
				afterSend:undefined,
				url:"",
				timeout: 2000,  //设置超时时间
				interVal:undefined, //是否连续发送测试包
				type: 'GET',
				delay:1,
		};
		
		var ping, requestTime, responseTime;//
		
		var options = $.extend(true, defaults, option);
		
		var beforeSend = function(){
			if(options.beforeSend && typeof(options.beforeSend) == 'function'){
				options.beforeSend();
			}
			requestTime = new Date().getTime();
		};
		
		var completePing = function(data){
			var aaa = arguments.length;
			responseTime = new Date().getTime();
		  ping = Math.abs(requestTime - responseTime);
		 	options.delay = ping;
		 	println("Reply from " +
		 			options.url +" time" +
		 			((ping<1)?("<1"):("="+ping)) +"ms");
		  if(options.afterSend && options.afterSend == 'function'){
			  options.afterSend(data);
		  }
		};
		
		var repackUrl = function(url){
			if(!url){
				throw new Exception('url不能为空');
			}
			if(!url.startWith('http')){
				url = "http://"+url;
			}
			if(!url.endWith('/') && !url.endWith('\\') ){
				url = url + '/';
			}

			if(!(url.indexOf('.') > 0)){
				url += ((new Date().getTime()) + '.html');
			}
			return url;
		};
		
		$.ajax({
			url: repackUrl(options.url),
			dataType:"html",
			type: options.type,
			timeout:options.timeout,
			beforeSend: beforeSend,
			complete: completePing,
		});
		
		if(options.interVal && options.interVal >0){
			var interval_value = options.interVal*1000;
			setTimeout(function(){$.ping(option);},interval_value);
		}
	
	};
	
	$.ping({
		url:"http://localhost:8080/BootStrap/single-test",
		interVal:1,
	});
	
	
	
	function println(str){
		//创建一个div对象
		var objDIV = document.createElement("div");
		if(objDIV.innerText != null)
		 objDIV.innerText = str;
		else
		 objDIV.textContent = str;
		 $('#divContent').append(objDIV);
		 //objContent.scrollTop = objContent.scrollHeight;
	}
	
</script>
<div class="align-center">
	如不能显示效果，请按Ctrl+F5刷新本页，更多网页代码：
	<a href='http://www.cheermanfa.com/' target='_blank'>
		http://www.cheermanfa.com/
	</a>
</div>
</body>
</html>