<%@page isErrorPage="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>

<body>

<div class="jumbotron">
    <div class="container">
        <br>
        <h4>Application error: </h4>
        <h2>${exception.message}</h2>
</div>
</div>
</body>
</html>