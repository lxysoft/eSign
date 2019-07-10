/**分页要的数据  and 拼接代码*/

//首页
var firstPageJQ=1;

//上一页  强转int
function preNumber(thisNo){
	return Number(thisNo)-1;
}
//下一页 强转int
function nextNumber(thisNo){
	return Number(thisNo)+1;
}
function firstNumber(){
	return firstPageJQ;
}
//是否有上一页 
function preBoolean(thisNo){
	return thisNo == 1 ;
}
//是否有下一页
function nextBoolean(thisNo,lastPage){
	return thisNo == lastPage;
}

//是否是首页
function firstBoolean(thisNo){
	return thisNo == firstPageJQ;
}
//是否是末页
function lastBoolean(thisNo,lastPage){
	return thisNo == lastPage
}
//是否未定义这个变量
function isUndefinde(num){
	return num == undefined?1:num;
}

/*
 * divId:div的id
 * funName:function方法名
 * pi:pageInfo数据
 * 
 */
function paging(divId,funName,pi){
	var pages= pi.pages;
	var pageNum=pi.pageNum;
	var html="";
	if(pages > 0){
		var first = firstBoolean(pageNum);
		if(first){
			html+="<a  class='page-btn active'  onclick='"+funName+"(1,true)'    > <<最前页</a>";
		}else{
			html+="<a  class='page-btn'  onclick='"+funName+"(1,"+first+")'    > <<最前页</a>";
		}
		
		
		html+='<div class="page-list">';
		for(var i =1 ;  i <= pages; i++){
			if(i < pageNum && i > pageNum-5){
				html+="<span class='span-page' onclick='"+funName+"("+i+",false)' >";
				html+=i;
				html+="</span>";
			}
			if(i == pageNum){
				//当前页点击当前页无效
				html+="<span class='span-page active'  onclick='"+funName+"("+i+",true)'   >";
				html+=i;
				html+="</span>";
			}
			if(i > pageNum && i < pageNum+5){
				html+="<span class='span-page'  onclick='"+funName+"("+i+",false)'   >";
				html+=i;
				html+="</span>";
			}	
		}
		html+="</div>";
		var last = lastBoolean(pageNum,pages);
		if(last){
			html+="<a class='page-btn active'   onclick='"+funName+"("+pages+",true)'    > >>最后页</a>";
		}else{
		 	html+="<a class='page-btn'   onclick='"+funName+"("+pages+","+last+")'    > >>最后页</a>";
		}
		
	}
	$("#"+divId).html(html);
	return html;	 
}

/*
 * 隐藏按钮
 * 
 * 
 */
function pagingDemo(divId,funName,pi){
	var pages= pi.pages;
	var pageNum=pi.pageNum;
	var html="";
	if(pages > 0){
		if(1 != pageNum){
			html+="<a  class='page-btn'  onclick='"+funName+"(1,"+firstBoolean(pageNum)+")'    > <<最前页</a>";
			html+="<a class='page-btn'  onclick='"+funName+"("+(pageNum==1?1:parseInt(pageNum)-1)+","+preBoolean(pageNum)+")'     >上一页</a>";
		}else{
			html+="<a  class='page-btn' style='visibility: hidden;'  onclick='"+funName+"(1,"+firstBoolean(pageNum)+")'    > <<最前页</a>";
			html+="<a class='page-btn'  style='visibility: hidden;'  onclick='"+funName+"("+(pageNum==1?1:parseInt(pageNum)-1)+","+preBoolean(pageNum)+")'     >上一页</a>";
		}
		
		html+='<div class="page-list">';
		for(var i =1 ;  i <= pages; i++){
			if(i < pageNum && i > pageNum-7){
				html+="<span class='span-page' onclick='"+funName+"("+i+",false)' >";
				html+=i;
				html+="</span>";
			}
			if(i == pageNum){
				html+="<span class='span-page active'  onclick='"+funName+"("+i+",true)'   >";
				html+=i;
				html+="</span>";
			}
			if(i > pageNum && i < pageNum+7){
				html+="<span class='span-page'  onclick='"+funName+"("+i+",false)'   >";
				html+=i;
				html+="</span>";
			}	
		}
		html+="</div>";
		if(pageNum == pages){
		  	html+="<a class='page-btn' style='visibility: hidden;'   onclick='"+funName+"("+(pageNum==pages?pages:parseInt(pageNum)+1)+","+nextBoolean(pageNum,pages)+")'    >下一页</a>";
			html+="<a class='page-btn' style='visibility: hidden;'  onclick='"+funName+"("+pages+","+lastBoolean(pageNum,pages)+")'    > >>最后页</a>";
	
		}else{
			html+="<a class='page-btn'   onclick='"+funName+"("+(pageNum==pages?pages:parseInt(pageNum)+1)+","+nextBoolean(pageNum,pages)+")'    >下一页</a>";
			html+="<a class='page-btn'   onclick='"+funName+"("+pages+","+lastBoolean(pageNum,pages)+")'    > >>最后页</a>";

		}
		
	}
	$("#"+divId).html(html);
	return html;	 
}

var path = document.getElementById("project").value;
//导出Excel
function exportExcel(type){
	var t=new Date().valueOf();
	window.location.href=path+'yundongjia/exportall?type='+type+'&_time='+t;
}









