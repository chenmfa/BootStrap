<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/head.jsp"%>
<style>
	*{
		margin:0;
		padding:0;
	}
	.win-mask{
		position:absolute;
		left:0;
		top:0;
		width:100%;
		height:100%;
		background:rgba(0,0,0,0.5);
		z-index:99;
	}
	.win-content{
		position:absolute;
		left:50%;
		top:50%;
		width:400px;
		height:300px;
		transform:translate(-50%,-50%);
		box-sizing:border-box;
		z-index:100;
	}
	.win-content .win-header,.win-content .win-footer{
		height:50px;
		background:#999;
		line-height:50px;
	}
	
	.win-content .win-body{
		height:200px;
   	background:#999;
		box-sizing:border-box;
		padding:10px;
	}
	
	.win-content a[data-role="button"]{
		float:right;
		display:block;
		height:25px;
		line-height:25px;
		padding:10px 50px;
		margin:3px 10px 0 0;
		border-radius:5px;
		color:#ccc;
		text-decoration:none;
		text-align:center;
	}
	
	.win-header{
		padding-left:10px;
		box-sizing:border-box;		
	}
	
</style>
<body>
	<div class="win-pop">
		<div class="win-mask">
			<div class="win-content">
				<div class="win-header">
					弹出层头部
				</div>
				<div class="win-body">
					弹出层主题
				</div>
				<div class="win-footer">
					<a href="javascript:void(0)" data-role="button">确定</a>
					<a href="javascript:void(0)" data-role="button">取消</a>
				</div>			
			</div>
		</div>
	</div>
</body>
<%@ include file="/foot.jsp"%>