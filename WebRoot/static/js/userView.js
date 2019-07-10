/*总控系统-用户*/
var userView = {
	userViewList: $('.userView'),
	userBtnList: $('.userView .add-btn'),
	userDetBtn: $('.userView .detail-btn'),
	conBtn: $('.userView .con-btn'),
	celBtn: $('.userView .cel-btn')
}
userView.userBtnList.eq(0).on('click', function() {
	userView.userViewList.eq(0).css('display', 'none');
	userView.userViewList.eq(1).css('display', 'block');
	userView.userViewList.eq(2).css('display', 'none');
})
userView.userBtnList.eq(1).on('click', function() {
	userView.userViewList.eq(0).css('display', 'block');
	userView.userViewList.eq(1).css('display', 'none');
	userView.userViewList.eq(2).css('display', 'none');
})
userView.conBtn.on('click', function() {
	userView.userViewList.eq(0).css('display', 'block');
	userView.userViewList.eq(1).css('display', 'none');
	userView.userViewList.eq(2).css('display', 'none');
})
userView.celBtn.on('click', function() {
	userView.userViewList.eq(0).css('display', 'block');
	userView.userViewList.eq(1).css('display', 'none');
	userView.userViewList.eq(2).css('display', 'none');
})
userView.userDetBtn.on('click', function() {
	userView.userViewList.eq(0).css('display', 'none');
	userView.userViewList.eq(1).css('display', 'none');
	userView.userViewList.eq(2).css('display', 'block');
})