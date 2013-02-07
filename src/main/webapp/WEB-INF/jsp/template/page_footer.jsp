<%@ include file="../includes/include.jsp" %>
&copy; <jsp:useBean id="now" class="java.util.Date" scope="request" /><fmt:formatDate value="${now}" pattern="yyyy" />, The Checklist Team