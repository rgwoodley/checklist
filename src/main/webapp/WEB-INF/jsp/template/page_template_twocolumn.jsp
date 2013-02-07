<%@ include file="../includes/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
   <meta charset="utf-8" />
   <!--[if IE]><script src="js/html5shim.js"></script><![endif]-->
   <base href="<%= request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/" %>">
   <title><tiles:getAsString name="title"/></title>
   <meta http-equiv="Pragma" content="no-cache">
   <meta http-equiv="Expires" content="-1">
   <meta http-equiv="Content-Type" content="text/html" charset="iso-8859-1">      
   <link rel="stylesheet" media="screen" href="css/template.css"/>  
   <script src="js/jquery-1.9.0.min.js"></script>
</head>
<body>
<div id="wrapper">
<header id="header">
<div id="logo">
<tiles:insertAttribute name="header"/>
</div>
<div id="topmenu">
Menu
</div>
</header>
<div id="innerwrapper">
<section id="searchbar">
Search
</section>
<section id="middle">
<div id="container">
<div id="content">
<tiles:insertAttribute name="main"/>
</div>
</div>
<aside id="sideLeft">
<div id="container">
</div>
</aside>
</section>
</div>
<br>
<footer id="footer">
<tiles:insertAttribute name="footer"/>
</footer>
</div>
</body>
</html>