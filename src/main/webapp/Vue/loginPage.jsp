<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
</head>
<body>
<h1>Bienvenue sur le Fanfaron Hub</h1>
<h2>Connexion</h2>
<%
    String erreur = (String) session.getAttribute("erreur");
    if (erreur != null) {
%>
<p style="color:red;"><%= erreur %></p>
<%
        session.removeAttribute("erreur");
    }
%>
<form action="../GestionComptesServlet?action=connecter" method="post">
    <label for="login">Nom Fanfaron :</label>
    <input type="text" id="login" name="nomFanfaron" required><br><br>

    <label for="password">Mot de passe :</label>
    <input type="password" id="password" name="motdepasse" required><br><br>

    <input type="submit" value="Se connecter">
</form>
<a href="inscription.jsp">Inscription</a>
</body>
</html>
