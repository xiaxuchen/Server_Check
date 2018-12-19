<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/11/28
  Time: 23:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="<c:url value='/user/addStus'/>" method="post" enctype="multipart/form-data">
    <input type="file" name="file">
    <input type="hidden" name="type" value="0">
    <input type="hidden" name="gradeId" value="1702">
    <input type="submit" value="确认">
</form>

</body>
</html>
