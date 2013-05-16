<%@ include file="../includes/include.jsp" %>
<c:forEach var="result" items="${searchResults}">
<p>
<a href="checklist/<c:out value="${result.objectKey}"/>"><c:out value="${result.name}"/></a>
<br/><c:out value="${result.description}"/>
</p>
</c:forEach>