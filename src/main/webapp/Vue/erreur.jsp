<%--
  Created by IntelliJ IDEA.
  User: etulyon1
  Date: 29/05/2025
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Erreur</title>
</head>
<body>
<p>Erreur: <%= (String) request.getAttribute("message")%></p>
</body>
</html>
