/* 省 */
		$("#formInfo").on('change','#province',function(){
			var value = $("#province").val();
			var t = new Date().valueOf();
			//加载市
			$.ajax({
				url:link+'yundongjia/getcitybypro',
				type:'post',
				dataType:'JSON',
				data:{
					_time:t,
					proCode:value
				},
				success:function(data){
					$("#city").html("");
					$("#city").append("<option value='-1'>市</option>");
					$("#city").append($("#cityTmpl").tmpl(data));
				}
			});
		});
		/* 市 */
		$("#formInfo").on('change','#city',function(){
			var value = $("#city").val();
			var t = new Date().valueOf();
			//加载区
			$.ajax({
				url:link+'yundongjia/getareabycity',
				type:'post',
				dataType:'JSON',
				data:{
					_time:t,
					cityCode:value
				},
				success:function(data){
					$("#area").html("");
					$("#area").append("<option value='-1'>区</option>");
					$("#area").append($("#cityTmpl").tmpl(data));
				}
			});
		});
		
		//24小时
		$(function(){
			 $("#starttime").empty();
		   	 $("#endtime").empty();
		   for(var i=0;i<24 ;i++){
		   	var str="";
		   	if(i<10){
		   		str = "0"+i+":00";
		   	}else{
		   		str = i+":00";
		   	}
		   	     $("#starttime").append("<option>"+str+"</option>");
		   	     $("#endtime").append("<option>"+str+"</option>");
		   }
		});
		
		
		
//省change事件
function loadCity(obj){
	var proid = $(obj).val();
	var value = $("#province").val();
	var t = new Date().valueOf();
	//加载市
	$.ajax({
		url:link+'yundongjia/getcitybypro',
		type:'post',
		dataType:'JSON',
		data:{
			_time:t,
			proCode:proid
		},
		success:function(data){
			$("#city").html("");
			$("#city").append("<option value='-1'>市</option>");
			$("#city").append($("#cityTmpl").tmpl(data));
		}
	});
}		
//市change事件
function loadArea(obj){
	var value = $(obj).val();
	var t = new Date().valueOf();
	//加载区
	$.ajax({
		url:link+'yundongjia/getareabycity',
		type:'post',
		dataType:'JSON',
		data:{
			_time:t,
			cityCode:value
		},
		success:function(data){
			$("#area").html("");
			$("#area").append("<option value='-1'>区</option>");
			$("#area").append($("#cityTmpl").tmpl(data));
		}
	});
}		
	

//查询手机号码是否已存在
function validMobilePhone(mp,tType){
	var reg = /^\d{11}$/;
	if(!reg.test(mp)){
		return false;
	}
	var t=new Date().valueOf();
	var result = true;
	$.ajax({
		url:link+'yundongjia/getvalidmobilephone',
		type:'post',
		async:false,
		dataType:'json',
		data:{
			mobilePhone:mp,
			tType:tType,
			_time:t
		},
		success:function(data){
			if(data > 0){
				result = false;
			}else{
				
			}
		}
	});
	return result;
}


