var MyChecklist = CLModel.extend();
var MyChecklistCollection = CLCollection.extend({
	model : MyChecklist,
	paginator_core : {
		type : 'GET',
		dataType : 'json',
		url : 'myChecklists?'
	},
	paginator_ui : {
		firstPage : 0,
		currentPage : 0,
		perPage : 4,
		totalPages : 0
	},
});
var MyChecklistView = CLView.extend({
	entryTpl : _.template($('#my-checklist-template').html()),
	events : {
		'click #mychecklist-view' : 'viewChecklist',
	},
	viewChecklist : function(e) {
		e.preventDefault();
		window.alert("View Checklist!");
	},
});
var MyChecklistNoResultView = CLNoModelView.extend({
	entryTpl : _.template($('#my-checklist-noresult-template').html()),
	events : {
		'click #mychecklist-create' : 'createChecklist',
	},
	createChecklist : function(e) {
		e.preventDefault();
		window.alert("Create Checklist!");
	},
});
var MyChecklistDisplayView = CLDisplayView.extend({
	selectorName : 'mychecklist',
	view : 'MyChecklistView',
	noResultView : 'MyChecklistNoResultView',
	footerView : 'MyChecklistFooterView',
});
var MyChecklistFooterView = CLFooterView.extend({
	entryTpl : _.template($('#my-checklist-footer-template').html()),
});
$(document).ready(function() {
	var checklists = new MyChecklistCollection();
	checklists.fetch({
		async : false
	});
	new MyChecklistDisplayView({
		el : $('#mychecklists'),
		collection : checklists
	});
});