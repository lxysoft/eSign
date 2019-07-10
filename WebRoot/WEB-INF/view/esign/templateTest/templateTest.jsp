<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style type="text/css">
.padding15{
	padding:15px 0px;
}
.form-group label {
	text-align: right;
}
.control-label {
    font-size: 15px;
    color: #777777;
}
#selectTemplate{
	padding: 0;
}
.buttondiv button{
	height: 35px;
    border-radius: 15px;
    background-color: #E83F14;
    border: 0px;
}
.buttondiv button:hover{
	height: 35px;
    border-radius: 15px;
    background-color: #E83F14;
    border: 0px;
}
.buttondiv{
	margin-top: 40px;
}
</style>
</head>
<body>
<div class="main-content" style="padding-top: 30px">
 <div class="container" style="margin-left: 0px;width:100%;">
 	 <form id="formInfo2" name="form2" enctype="multipart/form-data"   onsubmit="return false;"  method="post">	
		 <div  class="form-group padding15" >
		  	<label for="file" class="col-sm-2 control-label">请上传模板文件</label>
		  	<div class="col-sm-9">
				<input id="file0" type="file" name="file"  value="上传" accept="application/msword"/>
			</div>
		  </div>
	  </form>
 	 <form id="formInfo1" name="form2" enctype="multipart/form-data"   onsubmit="return false;"  method="post" hidden="hidden">
		  <div  class="form-group padding15" >
		  	<label for="signX" class="col-sm-2 control-label">本公司X轴坐标</label>
		  	<div class="col-sm-9">
				<input type="text" name="signX" class="form-control" id="signX" placeholder="本公司X轴坐标" >
			</div>
		  </div>
		  <div  class="form-group padding15" >
		  	<label for="signY" class="col-sm-2 control-label">本公司Y轴坐标</label>
		  	<div class="col-sm-9">
				<input type="text" name="signY" class="form-control" id="signY" placeholder="本公司Y轴坐标" >
			</div>
		  </div>
		  <div  class="form-group padding15" >
		  	<label for="signOtherX" class="col-sm-2 control-label">签署方X轴坐标</label>
		  	<div class="col-sm-9">
				<input type="text" name="signOtherX" class="form-control" id="signOtherX" placeholder="签署方X轴坐标" >
			</div>
		  </div>
		   <div  class="form-group padding15">
		  	<label for="signOtherY" class="col-sm-2 control-label">签署方Y轴坐标</label>
		  	<div class="col-sm-9">
				<input type="text" name="signOtherY" class="form-control" id="signOtherY" placeholder="签署方Y轴坐标" >
			</div>
		  </div>
		  <div  class="form-group padding15">
		  	<label for="signPage" class="col-sm-2 control-label">盖章页数</label>
		  	<div class="col-sm-9">
				<input type="text" name="signPage" class="form-control" id="signPage" placeholder="盖章页数" >
			</div>
		  </div>
	</form>
	  <div  class="form-group padding15 col-sm-12 buttondiv" >
	  		<div class="col-sm-offset-1 col-sm-4">
  				<button type="button" class="btn btn-danger btn-lg btn-block " onclick="word2pdf();">模板word转PDF</button>
	  		</div>
	  		<div class="col-sm-offset-1 col-sm-4">
  				<button  type="button" class="btn btn-danger btn-lg btn-block signtest" onclick="testSign();" disabled="disabled">签署测试模板</button>
	  		</div>
	  </div>
 </div>
</div>
<script type="text/javascript" src="<%=path %>/static/js/jquery-1.11.0.js"></script>
<script src="<%=path%>/static/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/static/css/bootstrap/js/bootstrapValidator.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="<%=path %>/static/layer/layer.js"></script>
<script type="text/javascript">
	function word2pdf(){
		if($("#file0").val() == ""){
			layer.tips("请上传word文档" , '#file0' ,{tips : 1});
			return ;
		}
		//获取表单信息
		var data = new FormData($('#formInfo2')[0]);
		$.ajax({
			url:'<%=path%>/zjEsignPC/test/word2Pdf',
			type: 'POST',  
	        data: data,
	        dataType: 'JSON', 
			cache: false,  
	        processData: false, 
	        contentType: false, 
			beforeSend: function () {  
	        	layer.load(1);
            },
			success:function(data){
				if(data.result){
					window.open("<%=path %>/" + data.path);
				}else{
					layer.msg(data.message , {icon : 2 , time : 2000});
				}
				$(".signtest").removeAttr("disabled");
				$("#formInfo1").removeAttr("hidden");
				layer.closeAll("loading");
			},
			error: function() {
				layer.msg("亲！您的网络不太顺畅喔~", {icon: 2});
				layer.closeAll("loading");
			}
		});
	}
	
	function testSign(){
		/*手动验证表单，当是普通按钮时。*/  
		$('#formInfo1').data('bootstrapValidator').validate();  
	    if(!$('#formInfo1').data('bootstrapValidator').isValid()){ 
	        return ;  
	    }  
	    
	  //获取表单信息
		var data1 = new FormData($('#formInfo1')[0]);
		$.ajax({
			url:'<%=path%>/zjEsignPC/test/testSignPDF',
			type: 'POST',  
	        data: data1,
	        dataType: 'JSON', 
			cache: false,  
	        processData: false, 
	        contentType: false, 
			beforeSend: function () {  
	        	layer.load(1);
            },
			success:function(data){
				if(data.result){
					window.open("<%=path %>/" + data.path);
				}else{
					layer.msg(data.message , {icon : 2 , time : 2000});
				}
				layer.closeAll("loading");
			},
			error: function() {
				layer.msg("亲！您的网络不太顺畅喔~", {icon: 2});
				layer.closeAll("loading");
			}
		});
	}
	
	$('#formInfo1').bootstrapValidator({
		fields : {
			signX : {
				validators: {
					notEmpty : {
						message : '该项不能为空'
					},
					regexp: {
		                 regexp: /\d$/,
		                 message: '请输入正确的数字'
		             }
				}
			},
			signY : {
				validators: {
					notEmpty : {
						message : '该项不能为空'
					},
					regexp: {
		                 regexp: /\d$/,
		                 message: '请输入正确的数字'
		             }
				}
			},
			signOtherX : {
				validators: {
					regexp: {
		                 regexp: /\d$/,
		                 message: '请输入正确的数字'
		             }
				}
			},
			signOtherY : {
				validators: {
					regexp: {
		                 regexp: /\d$/,
		                 message: '请输入正确的数字'
		             }
				}
			},
			signPage : {
				validators: {
					notEmpty : {
						message : '该项不能为空'
					},
					regexp: {
		                 regexp: /\d$/,
		                 message: '请输入正确的数字'
		             }
				}
			},
		  }
	});
</script>
</body>
</html>