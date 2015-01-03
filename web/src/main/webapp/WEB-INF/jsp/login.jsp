<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head lang="en">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, max-age=0, must-revalidate"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="Fri, 01 Jan 1990 00:00:00 GMT"/>

    <meta charset="UTF-8">
    <script src = "../../front-end/js/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src = "../../front-end/js/bootstrap.min.js" type="text/javascript"></script>

    <link rel = "stylesheet" href="/front-end/style/bootstrap.min.css" />
    <link rel = "stylesheet" href="/front-end/style/login.css" />

    <title>Login</title>
</head>
<body>
<div class="container">
<img src="/front-end/img/background.jpg" style="display: block; margin: 0 auto;">
  <div class="row">
      <form class="form-horizontal" method="post" action="/cars.jsp">
		<div class="col-md-3"></div>		
		<div class="col-md-1">Login</div>
        <div class="col-md-1"><input type="text" id="inputEmail" name="user_name" placeholder="Login"></div>
		<div class="col-md-1"></div>
		<div class="col-md-1">Password</div>
		<div class="col-md-1"><input type="password" name="user_pass" placeholder="Password"></div>
		<div class="col-md-1"></div>
        <div class="col-md-1"><button type="submit">Sign in</button></div>		
		<div class="col-md-2"></div>
      </form>
  </div>
</div>
</body>
</html>
