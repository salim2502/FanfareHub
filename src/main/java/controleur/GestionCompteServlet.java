package controleur;

import dao.FonfaronJDBCDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import metier.Fonfaron;

import java.io.IOException;

@WebServlet("/GestionComptesServlet")
public class GestionCompteServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");
        String vue = "erreur.jsp";

        try {
            FonfaronJDBCDAO fanfaronJDBCDAO = new FonfaronJDBCDAO();

            switch (action) {
                case "inscrire":
                    Fonfaron f = new Fonfaron(
                            req.getParameter("nomFonfaron"),
                            req.getParameter("email"),
                            req.getParameter("motdepasse"),
                            req.getParameter("nom"),
                            req.getParameter("prenom"),
                            req.getParameter("genre"),
                            req.getParameter("contrainte")
                    );
                    fanfaronJDBCDAO.insert(f);
                    vue = "Vue/loginPage.jsp";
                    res.sendRedirect(vue);
                    break;

                case "connecter":
                    String identifiant = req.getParameter("nomFonfaron");
                    String mdp = req.getParameter("motdepasse");
                    System.out.println(identifiant);
                    System.out.println(mdp);
                    Fonfaron utilisateur = fanfaronJDBCDAO.findByNameMdp(identifiant, mdp);

                    if (utilisateur != null) {
                        req.getSession().setAttribute("user", utilisateur);
                        if(utilisateur.isAdmin()){
                            vue = "accueilAdmin.jsp";
                        }
                        vue = "Vue/accueil.jsp";
                    } else {
                        req.setAttribute("erreur", "Identifiants incorrects");
                        vue = "Vue/loginPage.jsp";
                    }
                    break;

                default:
                    res.sendError(404, "Action non supportée : " + action);
                    return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            res.sendError(500, "Erreur serveur : " + e.getMessage());
            return;
        }

        req.getRequestDispatcher(vue).forward(req, res);
    }
}
