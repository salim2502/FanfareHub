<%@ page import="metier.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
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

    Evenement event = (Evenement) request.getAttribute("evenement");
    if (event == null) {
        response.sendRedirect("GestionEvenementServlet?action=afficher");
        return;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String dateStr = sdf.format(event.getHorodatage());
%>
<html>
<head>
    <title>Modifier un Événement</title>
</head>
<body>
<h2>Modifier l'Événement</h2>
<form method="post" action="GestionEvenementServlet">
    <input type="hidden" name="action" value="modifier">
    <input type="hidden" name="id" value="<%= event.getId() %>">

    Nom de l'événement : <input type="text" name="nom" value="<%= event.getNom() %>" required><br><br>
    Date et heure (format: yyyy-MM-dd HH:mm:ss) : <input type="text" name="horodatage" value="<%= dateStr %>" required><br><br>
    Durée (en minutes) : <input type="number" name="duree" value="<%= event.getDuree() %>" required><br><br>
    Lieu : <input type="text" name="lieu" value="<%= event.getLieu() %>" required><br><br>
    Description : <textarea name="description" rows="4" cols="50"><%= event.getDescription() %></textarea><br><br>

    <input type="submit" value="Enregistrer les modifications">
    <a href="GestionEvenementServlet?action=afficher">Annuler</a>
</form>
</body>
</html>