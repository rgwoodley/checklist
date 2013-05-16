<%@ include file="../includes/include.jsp" %>
<sec:authorize access="isAuthenticated()">
	<form:form commandName="searchForm" name="searchForm" id="searchForm" action="search" method="POST">
		<form:input id="searchText" path="searchText" tabIndex="1" maxLength="50"/>&nbsp;
		<a id="searchButton" href="#"><img id="img-search" src="img/search.png" height="18" width="18"></img></a>
	</form:form>
</sec:authorize>
<sec:authorize access="isAnonymous()">
	&nbsp;
</sec:authorize>