<%@ include file="../includes/include.jsp" %>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<sec:authorize access="isAuthenticated()">
	<fmt:formatDate value="${now}" pattern="MM/dd/yyyy hh:mm a" />
	&nbsp;|&nbsp;<a href="#">Help</a>&nbsp;|&nbsp;<a href="j_spring_security_logout">Logout</a>
</sec:authorize>
<sec:authorize access="isAnonymous()">
	<fmt:formatDate value="${now}" pattern="MM/dd/yyyy hh:mm a" />
	&nbsp;|&nbsp;<a href="#">Help</a>&nbsp;|&nbsp;<a href="login">Log In</a>
</sec:authorize>
