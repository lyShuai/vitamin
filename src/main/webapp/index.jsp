<%@ page language="java" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("BasePath",basePath);
request.setAttribute("Path",path);
%>

<!DOCTYPE HTML>
<html>
  <head>
  	<meta charset="GBK">
    <title>维他命</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="lyShuai">
    <link rel="short icon" href="${BasePath }favicon.ico">
    <link rel="stylesheet" type="text/css" href="${Path }/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${Path }/bootstrap/css/bootstrap-responsive.min.css">
  </head>
  
  <body>
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="${BasePath }">维他命&trade;</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li class="active"><a href="${BasePath }#home">首页</a></li>
              <li><a href="${BasePath }#source">源码</a></li>
              <li class="dropdown">
                <a href="${BasePath }#other" class="dropdown-toggle" data-toggle="dropdown">其它 <b class="caret"></b></a>
                <ul class="dropdown-menu">
                  <li><a href="#">Bootstrap</a></li>
                  <li class="divider"></li>
                  <li><a href="#">One more separated link</a></li>
                </ul>
              </li>
              <li><a href="${BasePath }#about">关于维他命</a></li>
            </ul>
            <form class="navbar-form pull-right">
              <input class="span2" type="text" placeholder="Email">
              <input class="span2" type="password" placeholder="Password">
              <button type="submit" class="btn">Sign in</button>
            </form>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>
    
    <!-- Javascript
    ============================================ -->
    <script type="text/javascript" src="${BasePath }jquery/jquery.js"></script>
    <script type="text/javascript" src="${BasePath }bootstrap/js/bootstrap.js"></script>
  </body>
</html>
