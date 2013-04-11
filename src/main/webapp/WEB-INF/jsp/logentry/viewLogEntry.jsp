<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<script type="text/template" id="entry-template">
<@- action @>&nbsp;&nbsp;<a href="#" id="entry-delete">Delete</a>&nbsp;&nbsp;<a href="#" id="entry-update">Update</a>
</script>
<script type="text/template" id="entry-update-template">
<input type="text" id="item-for-update" value="<@- action @>" size="15" maxlength="50">&nbsp;&nbsp;<a href="#" id="entry-update-cancel">Cancel</a>
</script>

    <div id="entry-form">
      <h2>Enter Log Entry ID:</h2>
      <form action="">
        <textarea></textarea><br>
        <input type="submit""/>
      </form>
    </div>

    <div id="log-entries">
      <form action="">
      <h2>Entries</h2>
      <ul id="log-entries-ul"></ul>
      </form>
    </div>