<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

     <form:form commandName="form" name="form" id="form" action="createLogEntry" method="POST">
     
     <spring:hasBindErrors name="form">
     <span class="error">Hey!  There are error.  Fix Them!</span>
     </spring:hasBindErrors>
     
     <form:errors path="action" cssClass="error"/>
     <form:input id="action" path="action" tabIndex="1" maxLength="50"/>
     
     <form:errors path="type" cssClass="error"/>
     <form:input id="type" path="type" tabIndex="2" maxLength="50"/>
     
     <form:errors path="detail" cssClass="error"/>
     <form:input id="detail" path="detail" tabIndex="3" maxLength="50"/>
     
     <input type="submit" value="submit">
     
     </form:form>