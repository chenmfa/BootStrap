/**
 *@description 将中文编码成unicode字符
 */
var decToHex = function(str) {
	var res=[];
	for(var i=0;i < str.length;i++){
	    //(charCodeAt(i))返回指定位置的字符的 Unicode 编码
		//NumberObject.toString(radix) radix为2 ~ 36 之间的整数。
		//若省略该参数，则使用基数 10
		res[i]=("00"+str.charCodeAt(i).toString(16)).slice(-4);
	}
	return "\\u"+res.join("\\u");
};

/**
 *@description 将中文编码成unicode字符
 */
var hexToDec = function(str) {
	str=str.replace(/\\/g,"%"); //将\转换成%
	return unescape(str);
};
/**
 * @description 测试名称是否符合规范(包含中文，字母，数字，下划线)
 */
function isLegalName(name){
	//3000-303F(\u3000-\u303F)   CJK 符号和标点   FF00-FFEF(\uFF00-\uFFEF)   全角ASCII、全角标点
	var regex=/^[\w\u2E80-\u2EFF\u2F00-\u2FDF\u31C0-\u31EF\u3200-\u32FF\u3300-\u33FF\u3400-\u4DBF\u4DC0-\u4DFF\u4E00-\u9FBF\uF900-\uFAFF\uFE30-\uFE4F]{10,50}$/g;
	//测试名称
	name ='ss中文中文中中文中文中中文中文中中文中文中文ss333??';
	console.log('\u4e2d\u6587\u4e2d\u6587\u4e2d\u4e2d\u6587\u4e2d\u6587\u4e2d\u4e2d\u6587\u4e2d\u6587\u4e2d\u4e2d\u6587\u4e2d\u6587\u4e2d\u6587sss\u0020sss\u3000sss');
	console.log(escape(name).toLocaleLowerCase().replace(/%u/gi, '\\u'));
	//console.log(/^\S+$/g.test('s中文中文中中文中文中中文中文中中文中文中文s'));
	var isLegal = regex.test(name);
	console.log(isLegal);
	return isLegal;
}
//格式化输入字符串
function getNoSpaceData(data) {
	data = $.trim(data);
	if(data.length!=0){
		while(data.substring(0,1)=="\u0000")
		{
			data=data.substring(1);
			data = $.trim(data);
		}
		while(data.substring(length-1)=="\u0000")
		{
			data=data.substring(0,length-1);
			data = $.trim(data);
		}
	}
	return data;
};

String.prototype.startWith = function(str){
	if(!str || str.length == 0 || str.length > this.length){
		return false;
	}else if(this.substr(0,str.length) == str){
		return true;
	}else{
		return false;
	}	
};

String.prototype.endWith = function(str){
	if(!str || str.length == 0 || str.length > this.length){
		return false;
	}else if(this.substr(this.length - str.length -1, this.length) == str){
		return true;
	}else{
		return false;
		
	}
};
