//hj   企业分页工具

function getPrePage(pageNum,pages){
	var p = parseInt(pageNum) - 1;
	if(p > 0 && p <= pages){
		return p;
	}else{
		return -1;
	}
}
function getNextPage(pageNum,pages){
	var np = parseInt(pageNum) + 1;
	if(np <= pages){
		return np;
	}else{
		return -1;
	}
}

/**
 * @param tagId div的ID
 * @param funName jquery function名称
 * @param pi 分页数据
 */
function pagePading(tagId,funName,pi){
	var pages= pi.pages;
	var pageNum=pi.pageNum;
	var html="";
	$("#"+tagId).html("");
	if(pages > 0){
		var prePageNum = getPrePage(pageNum, pages);
		//console.log(prePageNum);
		if(prePageNum > -1){
			$("#"+tagId).append('<li><a href="javascript:void(0);" onclick="'+funName+'('+prePageNum+');"> < </a></li>');
		}else{
			//$("#"+tagId).append('<li class="disabled"><a href="#">&laquo;</a></li>');
		}
		for(var i=1;i<=pages;i++){
			if(i < pageNum && i > pageNum-5){
				
				$("#"+tagId).append('<li><a href="javascript:void(0);" onclick="'+funName+'('+i+');">'+i+'</a></li>');
			}
			
			if(i == pageNum){
				$("#"+tagId).append('<li class="active"><a href="javascript:void(0);">'+i+'</a></li>');
			}
			
			if(i > pageNum && i < pageNum+5){
				
				$("#"+tagId).append('<li><a href="javascript:void(0);" onclick="'+funName+'('+i+');">'+i+'</a></li>');
			}
		}
		
		var nextPageNum = getNextPage(pageNum, pages);
		if(nextPageNum > -1){
			$("#"+tagId).append('<li><a href="#" onclick="'+funName+'('+nextPageNum+');"> > </a></li>');
		}else{
			//$("#"+tagId).append('<li class="disabled"><a href="#">&laquo;</a></li>');
		}
		
		
	}
}