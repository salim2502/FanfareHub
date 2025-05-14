package controleur;

import dao.FanfaronJDBCDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import metier.Fanfaron;

import java.io.IOException;

@WebServlet("/GestionComptesServlet")
public class GestionCompteServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");
        String vue = "erreur.jsp";

        try {
            FanfaronJDBCDAO fanfaronJDBCDAO = new FanfaronJDBCDAO();

            switch (action) {
                case "inscrire":
                    Fanfaron f = new Fanfaron(
                            req.getParameter("nomFanaron"),
                            req.getParameter("email"),
                            req.getParameter("motdepasse"),
                            req.getParameter("nom"),
                            req.getParameter("prenom"),
                            req.getParameter("genre"),
                            req.getParameter("contrainte")
                    );
                    fanfaronJDBCDAO.insert(f);
                    vue = "loginPage.jsp";
                    break;

                case "connecter":
                    String identifiant = req.getParameter("nomFanaron");
                    String mdp = req.getParameter("motdepasse");

                    Fanfaron utilisateur = fanfaronJDBCDAO.findByNameMdp(identifiant, mdp);
                    if (utilisateur != null) {
                        req.getSession().setAttribute("user", utilisateur);
                        if(utilisateur.isAdmin()){
                            vue = "accueilAdmin.jsp";
                        }
                        vue = "accueil.jsp";
                    } else {
                        req.setAttribute("erreur", "Identifiants incorrects");
                        vue = "connexion.jsp";
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
