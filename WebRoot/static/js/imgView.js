//编辑器默认提示
var placeText = "请输入一些内容,如果图片需要自适应APP,建议宽度设置为:一行一张图宽度为350,一行两张图每张宽度为150,默认图片宽度350";
//图片回显
function imgview(div,fileId,len){
	if(undefined == len){
		len = 4;//默认5张图
	}
	var reallocalpath ="";
    var file = document.getElementById(fileId);
    if(window.FileReader){//chrome,firefox7+,opera,IE10,IE9，IE9也可以用滤镜来实现
    	if(file.files.length > len){
    		alert("上传图片数量不符！");
    		$("input[type='file']").val("");
    		$("#"+div).html(""); 
    		return ;
    	}
    	$("#"+div).html("");
    	for(var i = 0; i < file.files.length; i++){
	       oFReader = new FileReader();
	       oFReader.readAsDataURL(file.files[i]);
	       oFReader.onload = function (oFREvent) {
	    	  	 $("#"+div).append("<img  class='col-md-2'  alt='' src="+ oFREvent.target.result +" width='100px' height='100px' style='padding: 5px;'>");
	    	   };
    	} 
    }
    else if (document.all) {//IE8-
        file.select();
         reallocalpath = document.selection.createRange().text;//IE下获取实际的本地文件路径
        if (window.ie6) pic.src = reallocalpath; //IE6浏览器设置img的src为本地路径可以直接显示图片
        else { //非IE6版本的IE由于安全问题直接设置img的src无法显示本地图片，但是可以通过滤镜来实现，IE10浏览器不支持滤镜，需要用FileReader来实现，所以注意判断FileReader先
            pic.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image',src=\"" + reallocalpath + "\")";
            pic.src = 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';//设置img的src为base64编码的透明图片，要不会显示红xx
            imgview.src = 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';//设置img的src为base64编码的透明图片，要不会显示红xx
        }
        
    }
    else if (file.files) {//firefox6-
    	if(file.files.item.length>5){
    		alert("上传图片数量不符！");
    		$("input[type='file']").val("");
    		return ;
    	}
    	$("#"+div).html("");
    	for(var i = 0; i < file.files.item.length; i++){
	        if (file.files.item(i)) {
	            url = file.files.item(i).getAsDataURL();
	            $("#"+div).append("<img class='col-md-2'  alt='' src="+  url +" width='100px' height='100px'  style='padding: 5px;'>");
	        }
    	}
    } 
	
	
}

/**
 * @param inputId  input  id
 * @param divId  图片展示id
 */
function delPageImage(inputId,divId){
	$("#"+inputId).val("");
	$("#"+divId).html("");
}

//判断图片是否存在  
function CheckImgExists(imgurl) {
		var ImgObj = new Image(); 
		ImgObj.src = imgurl;
		//没有图片，则返回-1  
		if (ImgObj.fileSize > 0 || (ImgObj.width > 0 && ImgObj.height > 0)) {
			return true;
		} else {
			return false;
		}
};