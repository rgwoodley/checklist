_.templateSettings = {
    interpolate: /\<\@\=(.+?)\@\>/gim,
    evaluate: /\<\@(.+?)\@\>/gim,
    escape: /\<\@\-(.+?)\@\>/gim
};

var Entry = Backbone.Model.extend({
    idAttribute: "objectKey"
});
 
var EntryCollection = Backbone.Collection.extend({
    model:Entry,
    url:"auditLogEntries"
});

var EntryView = Backbone.View.extend({
   tagName: "li",  
   updateTpl: _.template($('#entry-update-template').html()), 
   entryTpl: _.template($('#entry-template').html()), 
   events: {
    'click #entry-delete': 'deleteEntry',
    'click #entry-update': 'updateEntry',
    'click #entry-update-cancel': 'cancelUpdate',
    'keypress input[type=text]': 'checkForEnter'
   }, 
   initialize: function(){
      this.render();
   },
   render: function() {
      this.$el.html(this.entryTpl(this.model.toJSON()));
   },
   deleteEntry: function(e) {
      e.preventDefault();
      this.model.destroy();
   },
   updateEntry: function(e) {
      e.preventDefault();
      this.$el.html(this.updateTpl(this.model.toJSON()));
      this.$('input:text').focus();
   },
   cancelUpdate: function(e) {
      e.preventDefault();
      this.$el.html(this.entryTpl(this.model.toJSON()));
   },
   checkForEnter: function(e) {
      if (e.keyCode == 13) {
         e.preventDefault();
         var txt = this.$('input:text').val();
         this.model.set({
            action:txt,
            detail:txt,
            type:txt
         });
         this.model.save();
         this.$el.html(this.entryTpl(this.model.toJSON()));
      }
   }
});

var GetEntryView = Backbone.View.extend({
    events: {
        'submit form': 'addEntry'
    },
    initialize: function() {
        this.collection.on('add', this.clearInput, this);
    },
    addEntry: function(e) {
        e.preventDefault();
        var txt = this.$('textarea').val();
        var newEntry = new Entry({
           action:txt,
           detail:txt,
           type:txt
        });
        this.collection.create(newEntry);
    },
    clearInput: function() {
       this.$('textarea').val('');
    } 
});

var EntryDisplayView = Backbone.View.extend({
    initialize: function() {
        this.render();
        this.collection.on('add', this.appendEntry, this);
        this.collection.on('destroy', this.removeEntry, this);
        this.collection.bind('remove', this.render, this);
    },
    appendEntry: function(entry) {
        var entryView = new EntryView({model:entry});
        this.$('ul').append(entryView.el);
    },
    removeEntry: function(entry) {
        this.collection.remove(entry);
    },
    render: function() {
      var view = this;
      this.$('ul').empty();
      this.collection.each(function(entry, index) {
         view.appendEntry(entry);
      });
   },
});

$(document).ready(function() {
    var entries = new EntryCollection();
    entries.fetch({async: false});   
    new GetEntryView({ el: $('#entry-form'), collection: entries });
    new EntryDisplayView({ el: $('#log-entries'), collection: entries });
});