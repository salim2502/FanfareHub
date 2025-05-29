<%@ page import="metier.*" %>

<%@ page import="java.util.List" %>
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

  List<Evenement> evenements = (List<Evenement>) request.getAttribute("evenements");
  List<String> userCommissions = (List<String>) request.getAttribute("userCommissions");
  String message = (String) request.getAttribute("message");
  String erreur = (String) request.getAttribute("erreur");
%>
<html>
<head>
  <title>Liste des Événements</title>
</head>
<body>
<% if (message != null) { %>
<div style="color: green;"><%= message %></div>
<% } %>
<% if (erreur != null) { %>
<div style="color: red;"><%= erreur %></div>
<% } %>

<h2>Liste des Événements</h2>

<table border="1">
  <tr>
    <th>Nom</th>
    <th>Date/Heure</th>
    <th>Durée (min)</th>
    <th>Lieu</th>
    <th>Description</th>
    <th>Créé par</th>
    <th>Actions</th>
  </tr>
  <% for (Evenement event : evenements) { %>
  <tr>
    <td><%= event.getNom() %></td>
    <td><%= event.getHorodatage() %></td>
    <td><%= event.getDuree() %></td>
    <td><%= event.getLieu() %></td>
    <td><%= event.getDescription() %></td>
    <td><%= event.getNomFanfaron() %></td>
    <td>
      <% if (user.getNomFanfaron().equals(event.getNomFanfaron()) || userCommissions.contains("prestation")) { %>
      <a href="GestionEvenementServlet?action=modifier&id=<%= event.getId() %>">Modifier</a>
      <a href="GestionEvenementServlet?action=supprimer&id=<%= event.getId() %>"
         onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet événement?')">Supprimer</a>
      <% } %>
      <a href="GestionEvenementServlet?action=inscription&id=<%= event.getId() %>">S'inscrire</a>
    </td>
  </tr>
  <% } %>
</table>

<a href="GestionComptesServlet">Retour à l'accueil</a>
</body>
</html>