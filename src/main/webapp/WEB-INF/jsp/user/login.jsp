<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

  <h1>Log In</h1>
  <form name="form" action="j_spring_security_check" method="POST" id="form">
  <p>Enter your Username and Password
  <c:choose>
  <c:when test="${param.e != null}">
  <br>Username not found or password incorrect.  Please try again.
  </c:when>
  <c:otherwise>
  <br>&nbsp;
  </c:otherwise>
  </c:choose>
  </p>
  Enter your username:
  <input type="text" id="j_username" name="j_username" tabIndex="1" maxLength="50"/>
  <br/>
  Enter your password:
  <input type="text" id="j_password" name="j_password" tabIndex="2" maxLength="15"/>
  <br/>
  <input type="submit" tabIndex="3"/>
  </form>
