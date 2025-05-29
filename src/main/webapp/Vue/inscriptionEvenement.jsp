<%@ page import="metier.Evenement" %>
<%@ page import="metier.Fanfaron" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
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
    List<String> pupitres = (List<String>) request.getAttribute("pupitres");
    List<Map<String, String>> inscriptions = (List<Map<String, String>>) request.getAttribute("inscriptions");

    // Vérifier si l'utilisateur est déjà inscrit
    Map<String, String> userInscription = null;
    if (inscriptions != null) {
        for (Map<String, String> insc : inscriptions) {
            if (insc.get("nomFanfaron").equals(user.getNomFanfaron())) {
                userInscription = insc;
                break;
            }
        }
    }
%>
<html>
<head>
    <title>Inscription à l'événement</title>
    <style>
        .present { color: green; }
        .absent { color: red; }
        .incertain { color: orange; }
    </style>
</head>
<body>
<h2>Inscription à l'événement: <%= event.getNom() %></h2>
<p>Date: <%= event.getHorodatage() %></p>
<p>Lieu: <%= event.getLieu() %></p>
<p>Description: <%= event.getDescription() %></p>

<h3>Mon inscription</h3>
<form method="post" action="GestionEvenementServlet">
    <input type="hidden" name="action" value="inscription">
    <input type="hidden" name="eventId" value="<%= event.getId() %>">

    Pupitre:
    <select name="pupitre" required>
        <% for (String pupitre : pupitres) { %>
        <option value="<%= pupitre %>"
                <%= (userInscription != null && userInscription.get("pupitre").equals(pupitre)) ? "selected" : "" %>>
            <%= pupitre %>
        </option>
        <% } %>
    </select><br>

    Statut:
    <select name="statut" required>
        <option value="present" class="present"
                <%= (userInscription != null && userInscription.get("statut").equals("present")) ? "selected" : "" %>>Présent</option>
        <option value="absent" class="absent"
                <%= (userInscription != null && userInscription.get("statut").equals("absent")) ? "selected" : "" %>>Absent</option>
        <option value="incertain" class="incertain"
                <%= (userInscription != null && userInscription.get("statut").equals("incertain")) ? "selected" : "" %>>Incertain</option>
    </select><br>

    <% if (userInscription != null) { %>
    <button type="submit" name="subAction" value="modifier">Modifier mon inscription</button>
    <button type="submit" name="subAction" value="supprimer">Se désinscrire</button>
    <% } else { %>
    <button type="submit" name="subAction" value="ajouter">S'inscrire</button>
    <% } %>
</form>

<h3>Liste des participants</h3>
<table border="1">
    <tr>
        <th>Nom</th>
        <th>Pupitre</th>
        <th>Statut</th>
    </tr>
    <% for (Map<String, String> insc : inscriptions) { %>
    <tr>
        <td><%= insc.get("nomComplet") %></td>
        <td><%= insc.get("pupitre") %></td>
        <td class="<%= insc.get("statut") %>">
            <%= insc.get("statut").substring(0, 1).toUpperCase() + insc.get("statut").substring(1) %>
        </td>
    </tr>
    <% } %>
</table>

<a href="GestionEvenementServlet?action=afficher">Retour à la liste des événements</a>
</body>
</html>