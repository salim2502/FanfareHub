package controleur;

import dao.CommissionJDBCDAO;
import dao.FanfaronJDBCDAO;
import dao.PupitreJDBCDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import metier.Fanfaron;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Boolean isAdmin = (Boolean) req.getSession().getAttribute("admin");
        if (isAdmin == null || !isAdmin) {
            res.sendRedirect("accueil.jsp");
            return;
        }

        String action = req.getParameter("action");

        try {
            FanfaronJDBCDAO dao = new FanfaronJDBCDAO();
            PupitreJDBCDAO pupitreDao = new PupitreJDBCDAO();
            CommissionJDBCDAO commissionDao = new CommissionJDBCDAO();
            switch (action) {
                case "Ajouter":
                    Fanfaron f = new Fanfaron(
                            req.getParameter("nomFanfaron"),
                            req.getParameter("email"),
                            req.getParameter("motdepasse"),
                            req.getParameter("nom"),
                            req.getParameter("prenom"),
                            req.getParameter("genre"),
                            req.getParameter("contrainte"),
                            null,
                            true
                    );
                    dao.insert(f);
                    break;
                case "supprimer":
                    dao.delete(req.getParameter("nomFanfaron"));
                    break;
                case "modifier":
                    String nomFanfaron = req.getParameter("nomFanfaron");
                    Fanfaron newFanfaron = dao.findByName(nomFanfaron);
                    newFanfaron.setAdmin(!newFanfaron.isAdmin());
                    dao.update(newFanfaron);
                    break;
                case "afficherPupitres":
                    List<String> pupitres = pupitreDao.getAllPupitres();
                    req.setAttribute("pupitres", pupitres);
                    req.getRequestDispatcher("Vue/gestionPupitres.jsp").forward(req, res);
                    break;
                case "afficherCommissions":
                    List<String> commissions = commissionDao.getAllCommissions();
                    req.setAttribute("commissions", commissions);
                    req.getRequestDispatcher("Vue/gestionCommissions.jsp").forward(req, res);
                    break;
                case "ajouterPupitre":
                    pupitreDao.addPupitre(req.getParameter("nomPupitre"));
                    break;
                case "supprimerPupitre":
                    pupitreDao.removePupitre(req.getParameter("nomPupitre"));
                    break;
                case "ajouterCommission":
                    commissionDao.addCommission(req.getParameter("nomCommission"));
                    break;
                case "supprimerCommission":
                    commissionDao.removeCommission(req.getParameter("nomCommission"));
                    break;
            }
            req.setAttribute("fanfarons", dao.findAll());
            Fanfaron user = (Fanfaron) req.getSession().getAttribute("user");
            CommissionJDBCDAO commissionJDBCDAO = new CommissionJDBCDAO();
            req.setAttribute("userCommissions", commissionJDBCDAO.getCommissionsByFanfaron(user.getNomFanfaron()));
            req.getRequestDispatcher("Vue/accueilAdmin.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            res.sendError(500, "Erreur serveur : " + e.getMessage());
        }
    }
}

