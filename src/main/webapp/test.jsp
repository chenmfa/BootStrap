<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/contain/head.jsp"%>

<title>css制作的弹出式图片预览效果</title>
<style type="text/css">
#ImageMain {     
    width: 630px;     
    height: 500px;     
    margin: 0 auto;     
    margin-top: 100px;     
}     
#ImageMain>img{     
    width:200px;     
    height:200px;     
    cursor:pointer;     
    float:left;     
    margin-left:10px;     
    margin-top:10px;     
}     
#ImageMain>img:hover{     
    opacity:0.8;     
}     
#ImageScaBg{     
    position:fixed;     
    background-color:#000;     
    top:0px;     
    left:0px;     
    opacity:0.6;     
    width:100%;     
    height:100%;     
    display:none;     
}     
#ImageSca{     
    position:absolute;     
    background-color:#333;     
    border:1px solid #ccc;     
     -webkit-border-radius:5px;       
    -moz-border-radius:5px;      
    border-radius:5px;       
    display:none;     
}     
#ImageContainer{     
    float:left;     
    text-align:center;     
}     
    
#ImageInfo{     
    float:left;     
    width:300px;     
    background-color:#fff;     
     -webkit-border-radius:0 3px 3px 0;       
    -moz-border-radius:0 3px 3px 0;       
    border-radius:0 3px 3px 0;       
}     
#imgName{     
    font: 15px "微软雅黑", Arial, Helvetica, sans-serif;     
    padding-left:10px;     
    font-weight:500px;     
}     
#imgInfo{     
    font: 13px "微软雅黑", Arial, Helvetica, sans-serif;     
    padding-left:10px;     
    color:#808080;     
}     
#imgLunbo{     
    height:80px;     
    position:absolute;     
    margin-left:50px;     
}     
.imgLunboItem{     
    width:76px;     
    height:76px;     
    margin-left:10px;     
}    
</style>
</head>
<body>
	<div id="ImageMain">
		<img src="/SysBack/data/qrcode/AB148.png"/>
		<img src="/SysBack/data/qrcode/AB148.png"/>
		<img src="/SysBack/data/qrcode/AB148.png"/>
		<img src="/SysBack/data/qrcode/AB148.png"/>
		<img src="/SysBack/data/qrcode/AB148.png"/>
		<img src="/SysBack/data/qrcode/AB148.png"/>
	</div>
<div id="ImageScaBg"></div>     
<div id="ImageSca">     
   <div id="ImageContainer">     
      <img id="imgCenter" src="/SysBack/data/qrcode/AB148.png"/>     
      <div id="imgLunbo"><img class="imgLunboItem" src="/SysBack/data/qrcode/AB148.png"/></div>     
   </div>     
   <div id="ImageInfo">     
     <h3 id="imgName"></h3>     
     <p id="imgInfo"></p>     
   </div>     
</div>
</body>
<script>
var ImageScaHandler={     
	    ImageMaxWidth:800,     
	    ImageMaxHeight:600,     
	    ImagePathJson:[{imgName:"预览弹出层测试图片1",imgPath:"1img1.jpg",imgInfo:"HTML图片预览弹出层测试图片-豪车图片集锦(图片均来自互联网)"},     
	    {imgName:"预览弹出层测试图片2",imgPath:"/SysBack/data/qrcode/AB148.png",imgInfo:"HTML图片预览弹出层测试图片-豪车图片集锦(图片均来自互联网)"},     
	    {imgName:"预览弹出层测试图片3",imgPath:"/SysBack/data/qrcode/AB148.png",imgInfo:"HTML图片预览弹出层测试图片-豪车图片集锦(图片均来自互联网)"},     
	    {imgName:"预览弹出层测试图片4",imgPath:"/SysBack/data/qrcode/AB148.png",imgInfo:"HTML图片预览弹出层测试图片-豪车图片集锦(图片均来自互联网)"},     
	    {imgName:"预览弹出层测试图片5",imgPath:"/SysBack/data/qrcode/AB148.png",imgInfo:"HTML图片预览弹出层测试图片-豪车图片集锦(图片均来自互联网)"},     
	    {imgName:"预览弹出层测试图片6",imgPath:"/SysBack/data/qrcode/AB148.png",imgInfo:"HTML图片预览弹出层测试图片-豪车图片集锦(图片均来自互联网)"}     
	    ],     
	    Init:function(){     
	        $("#ImageSca").css("width",(ImageScaHandler.ImageMaxWidth-200)+"px");     
	        $("#ImageSca").css("height",(ImageScaHandler.ImageMaxHeight-10)+"px");     
	        $("#ImageSca").css("top",($(window).height()-$("#ImageSca").height())/2 +"px");     
	        $("#ImageSca").css("left",($(window).width()-$("#ImageSca").width())/2+"px");     
	        $("#ImageContainer").css("width",$("#ImageSca").width()-300+"px").css("height",$("#ImageSca").height());     
	        $("#imgLunbo").css("width",$("#ImageSca").width()-300-100+"px").css("top",($("#ImageSca").height()-90)+"px");     
	        $("#ImageInfo").css("height",$("#ImageSca").height());     
	        $("#ImageMain>img").click(function(){     
	            ImageScaHandler.ChangeImage($(this));     
	        });     
	                ImageScaHandler.GetImage();     
	        $("#ImageSca").click(function(event){     
	            event.stopPropagation();     
	        });     
	        $("#ImageScaBg").click(function(event){     
	            ImageScaHandler.Hide();     
	        });     
	    },     
	    Show:function(){     
	        $("#ImageSca").css("display","block");     
	        $("#ImageScaBg").css("display","block");     
	    },     
	    Hide:function(){     
	        $("#ImageSca").css("display","none");     
	        $("#ImageScaBg").css("display","none");     
	    },     
	    GetImage:function(){     
	        $("#imgLunbo").children().remove();     
	        for(var i=0;i<ImageScaHandler.ImagePathJson.length;i  ){     
	           var mImage=document.createElement("img");     
	           mImage.className="imgLunboItem";     
	           mImage.src=ImageScaHandler.ImagePathJson[i].imgPath;     
	           $("#imgLunbo").append(mImage);     
	           mImage.onclick=function(){     
	               $(".imgLunboItem").css("border","0px solid #000");     
	               ImageScaHandler.ChangeImage($(this));     
	           }     
	        }     
	    },     
	    ChangeImage:function(target){     
	            $("#ImageContainer>img").attr("src",$(target).attr("src"));     
	            $("#ImageContainer>img").css("margin-top","100px");     
	            ImageScaHandler.Show();     
	             $(".imgLunboItem").css("border","0px solid #000");     
	            for(var i=0;i<ImageScaHandler.ImagePathJson.length;i  ){     
	                if(ImageScaHandler.ImagePathJson[i].imgPath==$(target).attr("src")){     
	                    $("#imgName").html(ImageScaHandler.ImagePathJson[i].imgName);     
	                    $("#imgInfo").html(ImageScaHandler.ImagePathJson[i].imgInfo);     
	                    $($(".imgLunboItem")[i]).css("border","2px solid #efefef");     
	                }     
	            }     
	    }     
	}
ImageScaHandler.Init();
ImageScaHandler.Show();
</script>
</html>

