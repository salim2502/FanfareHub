package controleur;

import dao.FanfaronJDBCDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import metier.Fanfaron;

import java.io.IOException;

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

            switch (action) {
                case "Ajouter":
                    System.out.println("why isn't working");
                    Fanfaron f = new Fanfaron(
                            req.getParameter("nomFanfaron"),
                            req.getParameter("email"),
                            req.getParameter("motdepasse"),
                            req.getParameter("nom"),
                            req.getParameter("prenom"),
                            req.getParameter("genre"),
                            req.getParameter("contrainte"),
                            true
                    );
                    dao.insert(f);
                    break;
                case "supprimer":
                    dao.delete(req.getParameter("nomFanfaron"));
                    break;
                case "modifier":
                    dao.editAdmin(req.getParameter("nomFanfaron"));
                    break;
            }

            req.setAttribute("fanfarons", dao.findAll());
            req.getRequestDispatcher("Vue/accueilAdmin.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            res.sendError(500, "Erreur serveur : " + e.getMessage());
        }
    }
}

