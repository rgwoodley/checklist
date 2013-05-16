<%@ include file="../includes/include.jsp" %>
<sec:authorize access="isAuthenticated()">
	Welcome back, <c:out value="${logged_in_user_formatted_name}"/>!
</sec:authorize>
<sec:authorize access="isAnonymous()">
	Welcome to Checklist!
</sec:authorize>