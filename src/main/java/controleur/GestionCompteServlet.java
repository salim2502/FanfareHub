package controleur;

import dao.FanfaronJDBCDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import metier.Fanfaron;

import java.io.IOException;
import java.util.List;

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
                            req.getParameter("nomFanfaron"),
                            req.getParameter("email"),
                            req.getParameter("motdepasse"),
                            req.getParameter("nom"),
                            req.getParameter("prenom"),
                            req.getParameter("genre"),
                            req.getParameter("contrainte"),
                            false
                    );
                    fanfaronJDBCDAO.insert(f);
                    res.sendRedirect("Vue/loginPage.jsp");
                    return;

                case "connecter":
                    String identifiant = req.getParameter("nomFanfaron");
                    String mdp = req.getParameter("motdepasse");
                    Fanfaron utilisateur = fanfaronJDBCDAO.findByNameMdp(identifiant, mdp);

                    if (utilisateur != null) {
                        req.getSession().setAttribute("user", utilisateur);
                        vue = "Vue/accueil.jsp";
                        if(utilisateur.isAdmin()){
                            req.getSession().setAttribute("admin", true);
                            List<Fanfaron> fanfarons = fanfaronJDBCDAO.findAll();
                            req.setAttribute("fanfarons", fanfarons);
                            vue = "Vue/accueilAdmin.jsp";
                        }
                    } else {
                        req.getSession().setAttribute("erreur", "Identifiants incorrects");
                        res.sendRedirect("Vue/loginPage.jsp");
                        return;
                    }
                    break;
                case "deconnecter":
                    req.getSession().invalidate();
                    res.sendRedirect("Vue/loginPage.jsp");
                    return;

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
