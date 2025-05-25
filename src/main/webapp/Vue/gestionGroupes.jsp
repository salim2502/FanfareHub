<%@ page import="java.util.List" %>
<%@ page import="metier.Fanfaron" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Gestion des groupes et pupitres</title>
</head>
<%  response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    Fanfaron user = (Fanfaron) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("GestionComptesServlet?action=connecter");
        return;
    }
    List<String> pupitres = (List<String>) request.getAttribute("pupitres");
    List<String> userPupitres = (List<String>) request.getAttribute("userPupitres");
    List<String> commissions = (List<String>) request.getAttribute("commissions");
    List<String> userCommissions = (List<String>) request.getAttribute("userCommissions");
    System.out.println(userPupitres);
    System.out.println(userCommissions);
%>
<body>
<h1>Gestion de vos groupes et pupitres</h1>

<form action="GestionGroupesServlet?action=majGroupes" method="post">
    <h2>Pupitres</h2>
    <% for (String pupitre : pupitres) {
        if(userPupitres!=null) {
            if(userPupitres.contains(pupitre)){
            out.print("<input type=\"checkbox\" name=\"pupitres\" value=\""+ pupitre + "\" checked>");
        }
            else{
            out.print("<input type=\"checkbox\" name=\"pupitres\" value=\""+ pupitre + "\">");
        }
        }
        else{
            out.print("<input type=\"checkbox\" name=\"pupitres\" value=\""+ pupitre + "\">");
        }

    %>
        <label><%= pupitre %></label><br>
     <% } %>
    <h2>Commissions</h2>
    <% for (String commission : commissions) {
        if(userCommissions!=null) {
        if(userCommissions.contains(commission)){
            out.print("<input type=\"checkbox\" name=\"commissions\" value=\""+ commission + "\" checked>");
        }else{
            out.print("<input type=\"checkbox\" name=\"commissions\" value=\""+ commission + "\">");
        }}else{
            out.print("<input type=\"checkbox\" name=\"commissions\" value=\""+ commission + "\">");
        }
    %>
    <label><%= commission %></label><br>
    <% } %>
    <input type="submit" value="Enregistrer les modifications">
</form>

<a href="GestionComptesServlet">Retour à l'accueil</a>
</body>
</html>