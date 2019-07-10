$(function() {
	var $authManage = $('.menu-list');
	$authManage.children('ul').children('li').not(':first').children('ul').slideUp();
	$authManage.children('ul').children('li').on('click', function() {
		$(this).addClass('active');
		$(this).children('ul').slideDown();
		$(this).siblings().children('ul').slideUp();
		$(this).siblings().removeClass('active');
	})
	$authManage.children('ul').children('li').find('li').children('ul').slideUp();
	$authManage.children('ul').children('li').find('li').on('click', function() {
		$(this).addClass('active');
		$(this).children('ul').slideDown();
		$(this).siblings().children('ul').slideUp();
		$(this).siblings().removeClass('active');
	})

	var workerList = $('.auth-given>.worker li');
	var workerGivenUl = $('.auth-given>.worker-given>ul');
	var workerGivenList = $('.auth-given>.worker-given>ul>li');
	var delBtn = $('.auth-given>.del a');
	var authIndex = null;
	workerList.on('click', function() {
		$(this).addClass('active');
		$(this).siblings().removeClass('active');
		var r = true;
		for(var i = 0; i < $("#haveAu>li").length; i++){
			if($("#haveAu>li").eq(i).attr("userId") == $(this).attr("userId")){
				r = false;
			}
		}
		if(r){
			workerGivenUl.append('<li userId="'+ $(this).attr("userId") +'">' + $(this).html() + '</li>');
		}
		$('.worker-given>ul>li').on('click', function() {
			$(this).addClass('active');
			$(this).siblings().removeClass('active');
			authIndex = $(this).index()

		})
	})
	$('.auth-given>.del a').on('click', function() {
		for(var i = 0; i < $(".worker-given>ul li").length; i++){
			if($(".worker-given>ul li").eq(i).attr("class") == "active"){
				$(".worker-given>ul li").eq(i).remove();
			}
		}
	})
})