var myGym = {
	addBtnList: $('.add-content')
}

myGym.addBtnList.eq(0).find('.add-btn').on('click', function() {
	myGym.addBtnList.eq(0).css('display', 'none');
	myGym.addBtnList.eq(1).css('display', 'block');
	myGym.addBtnList.eq(2).css('display', 'none');
})
myGym.addBtnList.eq(0).find('.next-btn').on('click', function() {
	myGym.addBtnList.eq(0).css('display', 'none');
	myGym.addBtnList.eq(1).css('display', 'none');
	myGym.addBtnList.eq(2).css('display', 'block');
})
myGym.addBtnList.eq(1).find('.cancel-btn').on('click', function() {
	myGym.addBtnList.eq(0).css('display', 'block');
	myGym.addBtnList.eq(1).css('display', 'none');
	myGym.addBtnList.eq(2).css('display', 'none');
})
myGym.addBtnList.eq(1).find('.next-btn').on('click', function() {
	myGym.addBtnList.eq(0).css('display', 'block');
	myGym.addBtnList.eq(1).css('display', 'none');
	myGym.addBtnList.eq(2).css('display', 'none');
})
myGym.addBtnList.eq(2).find('.pre-btn').on('click', function() {
	myGym.addBtnList.eq(0).css('display', 'block');
	myGym.addBtnList.eq(1).css('display', 'none');
	myGym.addBtnList.eq(2).css('display', 'none');
})

/*myGym.addBtnList.eq(2).find('.con-btn').on('click', function() {
	$('.modal').hide();
	$('.modal').hide();
	myGym.addBtnList.eq(0).css('display', 'block');
	myGym.addBtnList.eq(1).css('display', 'none');
	myGym.addBtnList.eq(2).css('display', 'none');
})*/
