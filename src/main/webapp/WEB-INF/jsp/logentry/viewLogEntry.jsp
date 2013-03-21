<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<script type="text/template" id="entry-template">
<@- action @>&nbsp;&nbsp;<a href="#" id="entry-delete">Delete</a>
</script>

    <div id="entry-form">
      <h2>Enter Log Entry ID:</h2>
      <form action="">
        <textarea></textarea><br>
        <input type="submit""/>
      </form>
    </div>

    <div id="log-entries">
      <h2>Entries</h2>
      <ul id="log-entries-ul"></ul>
    </div>