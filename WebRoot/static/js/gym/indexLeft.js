//图片预览
		function change(picId,fileId) {
			var reallocalpath ="";
		    var pic = document.getElementById(picId);
		    var file = document.getElementById(fileId);
		    if(window.FileReader){//chrome,firefox7+,opera,IE10,IE9，IE9也可以用滤镜来实现
		       oFReader = new FileReader();
		       oFReader.readAsDataURL(file.files[0]);
		       oFReader.onload = function (oFREvent) {pic.src = oFREvent.target.result;};  
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
		        if (file.files.item(0)) {
		            url = file.files.item(0).getAsDataURL();
		            pic.src = url;
		        }
		    } 
		};
		
		function CheckImgExists(imgurl) {  
			  var ImgObj = new Image(); //判断图片是否存在  
			  ImgObj.src = imgurl;  
			  //没有图片，则返回-1  
			  if (ImgObj.fileSize > 0 || (ImgObj.width > 0 && ImgObj.height > 0)) {  
			    return true;  
			  } else {  
			    return false;
			  }  
			}  
