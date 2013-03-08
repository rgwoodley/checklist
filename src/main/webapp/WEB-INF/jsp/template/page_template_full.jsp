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
   <link rel="stylesheet" media="screen" href="css/menu.css"/> 
   <link rel="stylesheet" media="screen" href="css/form.css"/>   
   <script src="js/jquery-1.9.1.min.js"></script>
   <script src="js/underscore-min.js"></script>
   <script src="js/json2.js"></script>
   <script src="js/backbone-min.js"></script>
   <script src="<tiles:getAsString name="jsFile"/>"></script>
</head>
<body>
<div id="wrapper">
<header id="header">
<div id="logo">
<tiles:insertAttribute name="header"/>
</div>
<div id="topmenu">
<nav>
<tiles:insertAttribute name="top_menu"/>
</nav>
</div>
</header>
<div id="innerwrapper">
<section id="topbar">
<div id="topbartext">
Some Text
</div>
<div id="topbarsearch">
<input type="text" placeholder="Search">
</div>
</section>
<section id="middle">
<div id="container">
<div id="contentfull">
<tiles:insertAttribute name="main"/>
</div>
</div>
</section>
</div>
<br>
<footer id="footer">
<tiles:insertAttribute name="footer"/>
</footer>
</div>
</body>
</html>