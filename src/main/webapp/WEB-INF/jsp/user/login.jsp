<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
  <form class="form" action="j_spring_security_check" method="POST">
    <h1><span class="title">Log in</span></h1>
    <c:if test="${not empty param.e}">
    <ul class="error-messages">
    	<li>Invalid Username or Password</li>
    </ul>
    </c:if>
    <p class="float">
        <label for="login">Username</label>
        <input type="text" name="j_username" placeholder="Username" tabIndex="1">
    </p>
    <p class="float">
        <label for="password">Password</label>
        <input type="password" name="j_password" placeholder="Password" class="showpassword" tabIndex="2"> 
    </p>
    <div class="button-area">
    <p class="clearfix">
    	<input type="submit" name="submit" value="Log In" tabIndex="3">
    </p>       
    </div>       
</form>

