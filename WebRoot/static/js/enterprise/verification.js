function notnull(idname){
	var name = $("#"+idname).val();
	if(name == "" || name == null){
		layer.tips('该输入项不可为空', "#"+idname , {tips : 1 , time : 2000});
		return false;
	}
	return true;
}

function onlyNum(idname){
	var name = $("#"+idname).val();
	if(!/^\d{n}$/.test(name)){
		layer.tips('请输入数字', "#"+idname , {tips : 1 , time : 2000});
		return false;
    }
	return true;
}

function isPhone(idname){
	var name = $("#"+idname).val();
	if(!/^1[34578]\d{9}$/.test(name)){
		layer.tips('请输入正确的手机号', "#"+idname , {tips : 1 , time : 2000});
		return false;
    }
	return true;
}

