<%@ include file="../includes/include.jsp" %>
<sec:authorize access="isAuthenticated()">
	<form name="searchForm" id="searchForm" action="search" method="POST">
		<input type="text" name="searchText" id="searchText" maxLength="50"/>&nbsp;
		<a id="searchButton" href="#"><img id="img-search" src="img/search.png" height="18" width="18"></img></a>
	</form>
</sec:authorize>
<sec:authorize access="isAnonymous()">
	&nbsp;
</sec:authorize>