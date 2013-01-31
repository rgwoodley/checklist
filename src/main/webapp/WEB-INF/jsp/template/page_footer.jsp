<%@ include file="../includes/include.jsp" %>
<hr/>
<h5>&copy; <jsp:useBean id="now" class="java.util.Date" scope="request" /><fmt:formatDate value="${now}" pattern="yyyy" />, CME Group PTT</h5>