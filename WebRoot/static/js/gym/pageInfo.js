	//分页页数封装
	function pageNum(data ,funName){
		$("#page").html("");
		if(data.pageNum == 1){
			$("#page").append('<a onclick="'+ funName +'(1);" class="active">&lt;&lt;最前页</a><div class="page-list"></div>');
		}else{
			$("#page").append('<a onclick="'+ funName +'(1);" >&lt;&lt;最前页</a><div class="page-list"></div>');
		}
		for(var i = 1; i <= data.pages; i++){
			if(data.pageNum == i){
				$(".page-list").append('<span onclick="'+ funName +'(' + i + ');"  class="active">' + i + '</span>');
			}else if(i < data.pageNum && i > data.pageNum - 4){
				$(".page-list").append('<span onclick="'+ funName +'(' + i + ');">' + i + '</span>');
			}else if(i > data.pageNum && i < data.pageNum + 4){
				$(".page-list").append('<span onclick="'+ funName +'(' + i + ');">' + i + '</span>');
			}
		}
		if(data.pageNum == data.pages){
			$("#page").append('<a onclick="'+ funName +'('+ data.pages+');" class="active">最后页&gt;&gt;</a>');
		}else{
			$("#page").append('<a onclick="'+ funName +'('+ data.pages+');" >最后页&gt;&gt;</a>');
		}
	}