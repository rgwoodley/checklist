function toggleSortOrder(currentOrder) {
	if ('asc' === currentOrder.toLowerCase()) {
		return 'desc';
	} else {
		return 'asc';
	}
}
var CLCollection = Backbone.Paginator.requestPager.extend({
	model : null,
	events : {},
	server_api : {
		'filter' : '',
		'perPage' : function() {
			return this.perPage;
		},
		'page' : function() {
			return this.currentPage;
		},
		'orderBy' : 'name',
		'format' : 'json',
		'sortOrder' : 'asc',
	},
	parse : function(response) {
		var tags = response.results;
		this.totalPages = response.count;
		return tags;
	}
});

var CLModel = Backbone.Model.extend({
	idAttribute : "objectKey"
});

var CLFooter = Backbone.Model.extend({
	cols : 2,
	page : 0,
	perPage : 0,
});

var CLFooterView = Backbone.View.extend({
	tagName : "tr",
	initialize : function() {
		this.render();
	},
	render : function() {
		this.$el.addClass("table-data-footer");
		this.$el.html(this.entryTpl(this.model.toJSON()));
	},
});
var CLView = Backbone.View.extend({
	tagName : "tr",
	initialize : function() {
		this.render();
	},
	render : function() {
		this.$el.html(this.entryTpl(this.model.toJSON()));
	},
});
var CLNoModelView = Backbone.View.extend({
	tagName : "tr",
	initialize : function() {
		this.render();
	},
	render : function() {
		this.$el.html(this.entryTpl());
	},
});
var CLDisplayView = Backbone.View.extend({
	selectorName : '',
	view : '',
	noResultView : '',
	footer : 'CLFooter',
	footerView : '',
	events : function() {
		var _events = {};
		_events["click #" + this.selectorName + "-next"] = "nextPage";
		_events["click #" + this.selectorName + "-prev"] = "prevPage";
		_events["click [id^=" + this.selectorName + "-sort]"] = "sort";
		return _events;
	},
	initialize : function() {
		this.render();
	},
	appendEntry : function(object) {
		var modelView = new window[this.view]({
			model : object
		});
		this.$('tbody').append(modelView.el);
	},
	render : function() {
		var view = this;
		this.$('tbody').empty();
		if (this.collection.isEmpty()) {
			var noResultView = new window[this.noResultView]();
			this.$('tbody').append(noResultView.el);
		} else {
			this.collection.each(function(object, index) {
				view.appendEntry(object);
			});
			this.$('tfoot').empty();
			var footer = new window[this.footer];
			footer.set("page", this.collection.currentPage + 1);
			footer.set("totalPages", this.collection.totalPages);
			var footerView = new window[this.footerView]({
				model : footer
			});
			this.$('tfoot').append(footerView.el);
			if (this.collection.totalPages <= (this.collection.currentPage + 1)) {
				$('#' + this.selectorName + '-next').hide();
			}
			if (this.collection.currentPage <= 0) {
				$('#' + this.selectorName + '-prev').hide();
			}
		}
	},
	sort : function(e) {
		e.preventDefault();
		var targetId = e.target.id;
		var fieldName = targetId.substr(targetId.indexOf('|') + 1);
		var currentOrderBy = this.collection.server_api.orderBy;
		if (currentOrderBy !== fieldName) {
			this.collection.server_api.orderBy = fieldName;
			this.collection.server_api.sortOrder = 'asc';
		} else {
			this.collection.server_api.sortOrder = toggleSortOrder(this.collection.server_api.sortOrder);
		}
		this.collection.goTo(0);
		this.collection.fetch({
			async : false
		});
		this.render();
	},
	nextPage : function(e) {
		e.preventDefault();
		this.collection.nextPage({
			async : false
		});
		this.render();
	},
	prevPage : function(e) {
		e.preventDefault();
		this.collection.prevPage({
			async : false
		});
		this.render();
	},
});