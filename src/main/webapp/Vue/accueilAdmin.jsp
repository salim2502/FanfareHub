<%--
  Created by IntelliJ IDEA.
  User: chabc
  Date: 18/05/2025
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="metier.Fonfaron" %>
<%@ page contentType="text/html;charset=UTF-8" import="java.util.List" %>
<%
    Boolean isAdmin = (Boolean) session.getAttribute("admin");
    if (isAdmin == null || !isAdmin) {
        response.sendRedirect("accueil.jsp");
        return;
    }

    List<Fonfaron> fonfarons = (List<Fonfaron>) request.getAttribute("fonfarons");
%>

<h2>Liste des comptes fonfarons</h2>
<table border="1">
    <tr>
        <th>Nom</th><th>Email</th><th>Admin</th><th>Actions</th>
    </tr>
    <% for (Fonfaron f : fonfarons) { %>
    <tr>
        <td><%= f.getNomFanfaron() %></td>
        <td><%= f.getEmail() %></td>
        <td><%= f.isAdmin() ? "Oui" : "Non" %></td>
        <td>
            <form action="AdminServlet" method="post" style="display:inline;">
                <input type="hidden" name="action" value="supprimer" />
                <input type="hidden" name="nomFonfaron" value="<%= f.getNomFanfaron() %>" />
                <input type="submit" value="Supprimer" />
            </form>
            <form action="AdminServlet" method="post" style="display:inline;">
                <input type="hidden" name="action" value="modifier" />
                <input type="hidden" name="nomFonfaron" value="<%= f.getNomFanfaron() %>" />
                <input type="submit" value="Changer rôle admin" />
            </form>
        </td>
    </tr>
    <% } %>
</table>
<h2>Ajouter un fonfaron</h2>
<form action="AdminServlet" method="post">
    <input type="hidden" name="action" value="Ajouter" />
    NomFonfaron : <input type="text" name="nomFonfaron" required><br>
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
    <input type="submit" value="Ajouter">
</form>

