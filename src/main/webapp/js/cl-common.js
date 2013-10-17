_.templateSettings = {
	interpolate : /\<\@\=(.+?)\@\>/gim,
	evaluate : /\<\@(.+?)\@\>/gim,
	escape : /\<\@\-(.+?)\@\>/gim
};
$(function() {
	$('#searchButton').click(function(e) {
		e.preventDefault();
		$('#searchForm').submit();
	});
});