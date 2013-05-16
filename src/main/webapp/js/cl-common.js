$(function() {
	$('#searchButton').click(function(e) {
		e.preventDefault();
		$('#searchForm').submit();
	});
});
_.templateSettings = {
    interpolate: /\<\@\=(.+?)\@\>/gim,
    evaluate: /\<\@(.+?)\@\>/gim,
    escape: /\<\@\-(.+?)\@\>/gim
};