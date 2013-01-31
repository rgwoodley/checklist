<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="expires" content="0">
<base href="<%= request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/" %>">
<% response.sendRedirect("dashboard"); %>