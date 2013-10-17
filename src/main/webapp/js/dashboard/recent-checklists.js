var RecentChecklist = CLModel.extend();
var RecentChecklistCollection = CLCollection.extend({
	model : RecentChecklist,
	paginator_core : {
		type : 'GET',
		dataType : 'json',
		url : 'recentChecklists?'
	},
	paginator_ui : {
		firstPage : 0,
		currentPage : 0,
		perPage : 4,
		totalPages : 0
	},
});
var RecentChecklistView = CLView.extend({
	entryTpl : _.template($('#recent-checklist-template').html()),
	events : {
		'click #recentchecklist-view' : 'viewChecklist',
	},
	viewChecklist : function(e) {
		e.preventDefault();
		window.alert("View Checklist!");
	},
});
var RecentNoResultView = CLNoModelView.extend({
	entryTpl : _.template($('#recent-checklist-noresult-template').html()),
});
var RecentChecklistDisplayView = CLDisplayView.extend({
	selectorName : 'recentchecklist',
	view : 'RecentChecklistView',
	noResultView : 'RecentNoResultView',
	footerView : 'RecentChecklistFooterView',
});
var RecentChecklistFooterView = CLFooterView.extend({
	entryTpl : _.template($('#recent-checklist-footer-template').html()),
});
$(document).ready(function() {
	var checklists = new RecentChecklistCollection();
	checklists.fetch({
		async : false
	});
	new RecentChecklistDisplayView({
		el : $('#recentchecklists'),
		collection : checklists
	});
});