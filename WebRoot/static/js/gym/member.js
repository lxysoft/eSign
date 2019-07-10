/*课程预约加载详情*/
var member = {
	detailBtn: $('.detail-btn'),
}
var indexRight = $('.index-right');

function clearData() {
	indexRight.html('');
};
member.detailBtn.on('click', function() {
	clearData();
	indexRight.load('view/MyGymnasium/DataManagement/memberDetail.html');
})