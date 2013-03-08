// Models
var Entry = Backbone.Model.extend({
    urlRoot:"auditLogEntries",
    defaults:{
        "name":"",
        "type":"",
        "details":""
    }
});
 
var EntryCollection = Backbone.Collection.extend({
    model:Entry,
    url:"auditLogEntries"
});

var GetEntryView = Backbone.View.extend({
    events: {
        'submit form': 'getEntry'
    },

    initialize: function() {
        //this.collection.on('request', this.clearInput, this);
    },

    getEntry: function(e) {
        e.preventDefault();
        console.log(JSON.stringify(Entry.get(1)));
    },

    clearInput: function() {
        this.$('textarea').val('');
    }
});

var EntriesView = Backbone.View.extend({
    initialize: function() {
        //this.collection.on('request', this.appendEntry, this);
    },

    appendEntry: function(entry) {
        //this.$('ul').append('<li>' + entry.escape('action') + '</li>');
    }
});

$(document).ready(function() {
    var entries = new EntryCollection();
    new GetEntryView({ el: $('#entry-form'), collection: entries });
    new EntriesView({ el: $('#log-entries'), collection: entries });
});