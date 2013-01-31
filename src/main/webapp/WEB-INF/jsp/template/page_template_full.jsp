<%@ include file="../includes/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
   <base href="<%= request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/" %>">
   <title><tiles:getAsString name="title"/></title>
   <meta http-equiv="Pragma" content="no-cache">
   <meta http-equiv="Expires" content="-1">
   <meta http-equiv="Content-Type" content="text/html" charset="iso-8859-1">   
</head>
<body>
<tiles:insertAttribute name="header"/>
<br/>
<tiles:insertAttribute name="body"/>
<br/>
<tiles:insertAttribute name="footer"/>
<br/>
</body>
</html>