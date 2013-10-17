var ActiveChecklist = CLModel.extend();
var ActiveChecklistCollection = CLCollection.extend({
	model : ActiveChecklist,
	paginator_core : {
		type : 'GET',
		dataType : 'json',
		url : 'activeChecklists?'
	},
	paginator_ui : {
		firstPage : 0,
		currentPage : 0,
		perPage : 3,
		totalPages : 0
	},
});
var ActiveChecklistView = CLView.extend({
	entryTpl : _.template($('#active-checklist-template').html()),
	events : {
		'click #activechecklist-view' : 'viewChecklist',
	},
	viewChecklist : function(e) {
		e.preventDefault();
		window.alert("View Checklist!");
	},
});
var ActiveNoResultView = CLNoModelView.extend({
	entryTpl : _.template($('#active-checklist-noresult-template').html()),
});
var ActiveChecklistDisplayView = CLDisplayView.extend({
	selectorName : 'activechecklist',
	view : 'ActiveChecklistView',
	noResultView : 'ActiveNoResultView',
	footerView : 'ActiveChecklistFooterView',
});
var ActiveChecklistFooterView = CLFooterView.extend({
	entryTpl : _.template($('#active-checklist-footer-template').html()),
});
$(document).ready(function() {
	var checklists = new ActiveChecklistCollection();
	checklists.fetch({
		async : false
	});
	new ActiveChecklistDisplayView({
		el : $('#activechecklists'),
		collection : checklists
	});
});