<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<!-- 
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
  --> 
  <form class="form" action="j_spring_security_check" method="POST">
    <h1><span class="title">Log in</span></h1>
    <p class="float">
        <label for="login">Username</label>
        <input type="text" name="j_username" placeholder="Username" tabIndex="1">
    </p>
    <p class="float">
        <label for="password">Password</label>
        <input type="password" name="j_password" placeholder="Password" class="showpassword" tabIndex="2"> 
    </p>
    <p class="clearfix"> 
        <input type="submit" name="submit" value="Log in" tabIndex="3">
    </p>       
</form>
