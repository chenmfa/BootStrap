<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.sysback.action.sys.SysUtil"%>

<html>
<head>
<title>css制作的弹出式图片预览效果</title>
<style type="text/css">

.thumbnail{
position: relative;
z-index: 0;
}

.thumbnail:hover{
background-color: transparent;
z-index: 50;
}

.thumbnail span{ /*CSS for enlarged image*/
position: absolute;
background-color: lightyellow;
padding: 5px;
left: -1000px;
border: 1px dashed gray;
visibility: hidden;
color: black;
text-decoration: none;
}

.thumbnail span img{ /*CSS for enlarged image*/
border-width: 0;
padding: 2px;
}

.thumbnail:hover span{ /*CSS for enlarged image on hover*/
visibility: visible;
top: 0;
left: 60px; /*position where enlarged image should offset horizontally */

}

</style>
</head>
<body>
<a class="thumbnail" href="#"><img src="/SysBack/data/qrcode\AB148.png" width="100px" height="66px" border="0" /><span><img src="http://myjs.jz123.cn/img/1.jpg" /><br />Simply beautiful.</span></a>

<a class="thumbnail" href="#"><img src="/SysBack/data/qrcode\AB148.png" width="100px" height="66px" border="0" /><span><img src="http://myjs.jz123.cn/img/2.jpg" /><br />So real, it's unreal. Or is it?</span></a>
</body>
</html>

