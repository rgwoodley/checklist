$(document).ready(function() {
    $('#entry-form form').submit(function(e) {
        e.preventDefault();
        $.ajax({
            url: 'getAuditLogEntry',
            type: 'GET',
            dataType: 'json',
            data: { id: $('#entry-form').find('textarea').val() },
            success: function(data) {
                $('#log-entries').append('<li>' + data.action + '</li>');
                $('#entry-form').find('textarea').val('');
            }
        });
    });
});