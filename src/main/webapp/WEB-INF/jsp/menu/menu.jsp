<%@ include file="../includes/include.jsp" %>
<sec:authorize access="isAuthenticated()">
      <ul>
        <li><a href="item1" class="first">My Checklists</a></li>
        <li><a href="item2">Active Checklists</a></li>
        <li class="last"><a href="item3" class="last">Create A Checklist</a></li>
      </ul>
</sec:authorize>
<sec:authorize access="isAnonymous()">
      <ul>
        <li><a href="item1" class="first">About</a></li>
        <li class="last"><a href="item3" class="last">Contact</a></li>
      </ul>
</sec:authorize>