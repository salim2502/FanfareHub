<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Inscription</title>
</head>
<body>
<h2>Inscription Fanfaron</h2>
<form action="../GestionComptesServlet?action=inscrire" method="post">
  NomFanfaron : <input type="text" name="nomFanfaron" required><br>
  Nom : <input type="text" name="nom"><br>
  Prénom : <input type="text" name="prenom"><br>
  Email : <input type="email" name="email" required><br>
  Mot de passe : <input type="password" name="motdepasse" required><br>
  Genre :
  <select name="genre">
    <option value="H">Homme</option>
    <option value="F">Femme</option>
    <option value="Autre">Autre</option>
  </select><br>
  Contraintes alimentaires : <input type="text" name="contrainte"><br>
  <input type="submit" value="S'inscrire">
</form>
<a href="loginPage.jsp"> Connexion</a>
</body>
</html>