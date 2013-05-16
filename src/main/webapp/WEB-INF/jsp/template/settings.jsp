<%@ include file="../includes/include.jsp" %>
<sec:authorize access="isAuthenticated()">
	<a href="#"><img id="img-settings" src="img/settings.png" height="20" width="20"></img></a>
</sec:authorize>
<sec:authorize access="isAnonymous()">
	&nbsp;
</sec:authorize>