var Entry = Backbone.Model.extend({
    url: '/getAuditLogEntries'
});

var Entries = Backbone.Collection.extend({
    model: Entry
});

var GetEntryView = Backbone.View.extend({
    events: {
        'submit form': 'getEntry'
    },

    initialize: function() {
        this.collection.on('request', this.clearInput, this);
    },

    getEntry: function(e) {
        e.preventDefault();
        var entry = this.collection.get(1);
        //var entry = this.collection.get(this.$('textarea').val());
        console.log(JSON.stringify(entry));
    },

    clearInput: function() {
        this.$('textarea').val('');
    }
});

var EntriesView = Backbone.View.extend({
    initialize: function() {
        this.collection.on('request', this.appendEntry, this);
    },

    appendEntry: function(entry) {
        this.$('ul').append('<li>' + entry.escape('action') + '</li>');
    }
});

$(document).ready(function() {
    var entries = new Entries();
    new GetEntryView({ el: $('#entry-form'), collection: entries });
    new EntriesView({ el: $('#log-entries'), collection: entries });
});