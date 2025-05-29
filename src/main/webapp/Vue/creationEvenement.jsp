<%@ page import="java.sql.Timestamp" %>
<%@ page import="metier.Evenement" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Créer un Événement</title>
</head>
<body>
<h2>Créer un Événement</h2>
<form method="post" action="GestionEvenementServlet">
    <input type="hidden" name="action" value="creer">
    Nom de l'événement : <input type="text" name="nom" required><br><br>
    Date et heure (format: yyyy-MM-dd HH:mm:ss) : <input type="text" name="horodatage" required><br><br>
    Durée (en minutes) : <input type="number" name="duree" required><br><br>
    Lieu : <input type="text" name="lieu" required><br><br>
    Description : <textarea name="description" rows="4" cols="50"></textarea><br><br>
    <input type="submit" value="Créer">
</form>
<a href="GestionComptesServlet">Retour à l'accueil</a>
</body>
</html>
