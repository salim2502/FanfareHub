<%@ page import="metier.Fanfaron" %>
<%@ page import="java.util.List" %><%--
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
    List<String> userPupitres = (List<String>) request.getAttribute("userPupitres");
    List<String> userCommissions = (List<String>) request.getAttribute("userCommissions");
%>
<html>
<head>
    <title>Accueil</title>
</head>
<body>
<h1>Yokozo</h1>
<h2>Pupitres</h2>
<ul>
    <% for (String pupitre : userPupitres) {%>
    <li><%= pupitre %></li>
        <%} %>
</ul>

<h2>Commissions</h2>
<ul>
    <% for (String commission : userCommissions) {%>
    <li><%= commission %></li>
    <%} %>
</ul>
<h2>Événements</h2>
<a href="./GestionEvenementServlet?action=afficher">Voir tous les événements<br></a>
<% if (userCommissions.contains("Prestation")){ %>
<a href="./GestionEvenementServlet?action=creer">Créer un nouvel événement<br></a>
<% } %>
<a href="./GestionGroupesServlet?action=afficher">Gérer mes groupes et pupitres</a>
<form action="./GestionComptesServlet" method="post">
    <input type="hidden" name="action" value="deconnecter" />
    <input type="submit" value="Déconnexion" />
</form>

</body>
</html>
