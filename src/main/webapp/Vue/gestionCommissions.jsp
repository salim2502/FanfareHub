<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.CommissionJDBCDAO, java.util.List, metier.*" %>
<%
  response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
  response.setHeader("Pragma", "no-cache");
  response.setDateHeader("Expires", 0);

  Fanfaron user = (Fanfaron) session.getAttribute("user");
  if (user == null || !user.isAdmin()) {
    response.sendRedirect("./Vue/accueil.jsp");
    return;
  }

  List<String> commissions = (List<String>) request.getAttribute("commissions");
%>
<!DOCTYPE html>
<html>
<head>
  <title>Gestion des Commissions</title>
  <style>
    .success { color: green; }
    .error { color: red; }
  </style>
</head>
<body>
<h1>Gestion des Commissions</h1>
<h2>Ajouter une Commission</h2>
<form action="AdminServlet" method="post">
  <input type="hidden" name="action" value="ajouterCommission">
  Nom de la commission: <input type="text" name="nomCommission" required>
  <input type="submit" value="Ajouter">
</form>

<h2>Liste des Commissions</h2>
<table border="1">
  <tr>
    <th>Nom</th>
    <th>Action</th>
  </tr>
  <% for (String commission : commissions) { %>
  <tr>
    <td><%= commission %></td>
    <td>
      <form action="AdminServlet" method="post" style="display:inline;">
        <input type="hidden" name="action" value="supprimerCommission">
        <input type="hidden" name="nomCommission" value="<%= commission %>">
        <input type="submit" value="Supprimer"
               onclick="return confirm('Êtes-vous sûr de vouloir supprimer cette commission?')">
      </form>
    </td>
  </tr>
  <% } %>
</table>

<br>
<a href="GestionComptesServlet">Retour à l'accueil</a>
</body>
</html>