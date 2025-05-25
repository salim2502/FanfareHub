<%@ page import="metier.Fanfaron" %><%--
  Created by IntelliJ IDEA.
  User: chabc
  Date: 16/05/2025
  Time: 09:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    Fanfaron user = (Fanfaron) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("loginPage.jsp");
        return;
    }
%>
<html>
<head>
    <title>Accueil</title>
</head>
<body>
<h1>Yokuzu</h1>
<a href="../GestionGroupesServlet?action=afficher">Gérer mes groupes et pupitres</a>
<form action="../GestionComptesServlet" method="post">
    <input type="hidden" name="action" value="deconnecter" />
    <input type="submit" value="Déconnexion" />
</form>

</body>
</html>
