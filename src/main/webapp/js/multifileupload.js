;
(function($,window,document,undefined){
	var MultiUpload=function(element, option){
		this.$element =element;
		this.dafaults ={
			fileTypeExts:'xls,xlsx',	//文件类型
			url:'',										//提交文件的赋值
			auto:false,								//是否自动上传
			multiple:true,						//是否支持多文件上传
			buttonName:'请选择文件',					//按钮显示的文字
			fadeOutTime:1000,					//上传完成后的渐隐时间
			liTemplate:'<li id="${fileId}file">'+
									'<div class="progress"><div class="progressbar"></div></div>'+
									'<span class="filename">${fileName}</span>'+
									'<span class="progressnum">0/${fileSize}</span>'+
									'<a class="uploadbtn">上传</a><a class="delfilebtn">删除</a>'+
									'</li>', //文件上传html模板
			filter:undefined,
			onBeginUpload:undefined,		//上传开始
			onUploadSuccess:undefined,	//上传成功
			onAfterUpload:undefined,		//上传完成
			onUploadError:undefined,		//上传失败
			onInit:undefined,						//初始化调用
			
		};
		
		this.options =$.extend({}, this.defaults, option);		
	};
	
	MultiUpload.prototype = {
		init:function(){
			var _this = this;
			var isMultiple = '';
			if(_this.options.multiple){
				isMultiple = 'multiple = "multiple"';
			}
			//文件选择域
			var inputStr= '<input type="file" name ="fileselect[]" class="uploadfile" hidden="true" '+
										isMultiple+' />';
			//单击按钮
			inputStr += '<a href="javascript:void(0)" class="uploadfilebtn">'+_this.options.buttonName+'</a>';
			var fileInputBtn =$(inputStr);
			var uploadFileList = '<ul class="fileList"></ul>';
			_this.append(fileInputBtn,uploadFileList);
			
		},
		//将文件的byte转成MB或者KB
		fileSizeFormat:function(fileSize){
			var displaySize;
			if(fileSize > 1024* 1024){
				displaySize = fileSize/1024 + '.' + fileSize%1024 + 'MB';
			}else{
				displaySize = Math.round(fileSize*100/1024)/100 + 'KB';
			}
			return displaySize;
		},
		//获取文件
		achieveFile:function(index, files){
			for(var i in files){
				if(files[i].index == index){
					return files[i];
				}
			}
		},
		//分割文件类型参数为数组
		achieveFileTypes:function(fileName){
			if(fileName){
				return fileName.split(',');			
			}else{
				return false;
			}
		},
		
	};
	
	
	$.fn.uploadfile = function(option){
		this.each(function(){
		});
	};
})(jQuery, window, document);