<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored ="false" %>
<html>
<head>
    <meta http-equiv="Cache-Control" content="no-cache, no-store, max-age=0, must-revalidate"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="Fri, 01 Jan 1990 00:00:00 GMT"/>

    <link rel = "stylesheet" href="../../../front-end/style/bootstrap.min.css" />
    <%--<link rel = "stylesheet" href="../../../front-end/style/main.css" />--%>
    <meta charset="UTF-8">
    <script src = "/front-end/js/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src = "/front-end/js/bootstrap.min.js" type="text/javascript"></script>
    <title>select car type</title>

</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-2"><img src="/front-end/img/logo.gif" style="display: block; margin: 0 auto;"></div>
        <div class="col-md-8"></div>
    </div>
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8"><br/><span class="label label-info">Выберите модель автомобиля</span><br/></div>
        <div class="col-md-2"></div>
    </div>
    <br/>
    <div class="row">
        <div class="col-md-2"></div>
        <c:forEach var="car" items="${requestScope.carList}">
             <div class="col-md-2">
                 <a href="/catalog.jsp?carTypeId=${car.car_id}">
                    <img src="/front-end/img/${car.car_name}.jpg" style="display: block; margin: 0 auto;">
                    <strong>${car.car_name}</strong>
                 </a>
             </div>
        </c:forEach>
    </div>
</div>

</body>
</html>
