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
    <title>select spare part category</title>

</head>
<body>
<div class="container">
    <c:set var="car" scope="request" value="carList"></c:set>
    <c:set var="carTypeId" value="${requestScope.carTypeId}"></c:set>
        <span class="label label-info">${carTypeId} geely N</span>
    <div class="row">
             <table>
                <tr>
                    <c:forEach var="categories" items="${requestScope.categoryList}">
                    <th>
                        <a href="/catalog.jsp?categoryId=${categories.category_id}">
                            ${categories.category_name}
                        </a>
                    </th>
                    </c:forEach>
                </tr>
             </table>
    </div>

    <div class="row">
        <div class="col-md-12">
            <table>
                <c:forEach var="parts" items="${requestScope.partList}">
                    <tr>
                        <th>${parts.part_id}</th>
                        <th>${parts.part_detais.part_name}</th>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>

</body>
</html>
