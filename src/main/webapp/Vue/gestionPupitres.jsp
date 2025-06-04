<%--
  Created by IntelliJ IDEA.
  User: etulyon1
  Date: 04/06/2025
  Time: 03:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.PupitreJDBCDAO, java.util.List" %>
<%@ page import="metier.*" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    Fanfaron user = (Fanfaron) session.getAttribute("user");
    if (user == null || !user.isAdmin()) {
        response.sendRedirect("./Vue/accueil.jsp");
        return;
    }
    List<String> pupitres = (List<String>) request.getAttribute("pupitres");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Gestion des Pupitres</title>
    <style>
        .success { color: green; }
        .error { color: red; }
    </style>
</head>
<body>
<h1>Gestion des Pupitres</h1>
<h2>Ajouter un Pupitre</h2>
<form action="AdminServlet" method="post">
    <input type="hidden" name="action" value="ajouterPupitre">
    Nom du pupitre: <input type="text" name="nomPupitre" required>
    <input type="submit" value="Ajouter">
</form>

<h2>Liste des Pupitres</h2>
<table border="1">
    <tr>
        <th>Nom</th>
        <th>Action</th>
    </tr>
    <% for (String pupitre : pupitres) { %>
    <tr>
        <td><%= pupitre %></td>
        <td>
            <form action="AdminServlet" method="post" style="display:inline;">
                <input type="hidden" name="action" value="supprimerPupitre">
                <input type="hidden" name="nomPupitre" value="<%= pupitre %>">
                <input type="submit" value="Supprimer"
                       onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce pupitre?')">
            </form>
        </td>
    </tr>
    <% } %>
</table>

<br>
<a href="GestionComptesServlet">Retour à l'accueil</a>
</body>
</html>