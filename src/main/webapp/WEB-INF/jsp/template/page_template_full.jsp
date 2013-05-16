<%@ include file="../includes/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
   <!--[if IE]><script src="js/html5shim.js"></script><![endif]-->
   <base href="<%= request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/" %>">
   <title><tiles:getAsString name="title"/></title>
   <meta http-equiv="Pragma" content="no-cache">
   <meta http-equiv="Expires" content="-1">
   <meta http-equiv="Content-Type" content="text/html" charset="iso-8859-1">      
   <link href="css/cl-css3.css" rel="stylesheet" type="text/css">
   <link href="css/cl-form-css3.css" rel="stylesheet" type="text/css"> 
   <script src="js/jquery-1.9.1.min.js"></script>
   <script src="js/underscore-min.js"></script>
   <script src="js/json2.js"></script>
   <script src="js/backbone-min.js"></script>
   <script src="js/cl-common.js"></script>
</head>
<body>
<div id="top_container">
  <div id="top_nav">
    <div id="welcome">
      <tiles:insertAttribute name="welcome_message"/>
    </div>
    <div id="top_menu">
      <tiles:insertAttribute name="menu"/>
    </div>
  </div>
  <div id="page_container">
    <div id="header">
        <tiles:insertAttribute name="logo"/>
        <div id="info">
        	<tiles:insertAttribute name="submenu"/>      		
      	</div>
      	<div id="search">
        	<tiles:insertAttribute name="search"/>
      	</div>
    </div>
    <div id="bc-outer">
    	<div id="breadcrumb"><tiles:insertAttribute name="breadcrumb"/></div>
    	<div id="bc-settings"><tiles:insertAttribute name="settings"/></div>
    </div>
    <div id="page-title"><tiles:getAsString name="heading"/></div>
    <div id="content">
    	<tiles:insertAttribute name="main"/>
    </div>
    <div id="footer">
        <tiles:insertAttribute name="footer"/>
    </div>
  </div>
</div>
</body>
<script src="<tiles:getAsString name="jsFile"/>"></script>
</html>