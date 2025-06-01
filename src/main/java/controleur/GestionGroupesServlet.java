package controleur;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import dao.CommissionDAO;
import dao.CommissionJDBCDAO;
import dao.PupitreDAO;
import dao.PupitreJDBCDAO;
import metier.Fanfaron;

import java.io.IOException;
import java.util.Arrays;

@WebServlet("/GestionGroupesServlet")
public class GestionGroupesServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String vue = "Vue/gestionGroupes.jsp";

        Fanfaron user = (Fanfaron) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("loginPage.jsp");
            return;
        }

        try {
            PupitreDAO pupitreDAO = new PupitreJDBCDAO();
            CommissionDAO commissionDAO = new CommissionJDBCDAO();

            switch (action) {
                case "afficher":
                    request.setAttribute("pupitres", pupitreDAO.getAllPupitres());
                    request.setAttribute("commissions", commissionDAO.getAllCommissions());
                    request.setAttribute("userPupitres", pupitreDAO.getPupitresByFanfaron(user.getNomFanfaron()));
                    request.setAttribute("userCommissions", commissionDAO.getCommissionsByFanfaron(user.getNomFanfaron()));
                    break;

                case "majGroupes":
                    String[] selectedPupitres = request.getParameterValues("pupitres");
                    String[] selectedCommissions = request.getParameterValues("commissions");

                    pupitreDAO.getPupitresByFanfaron(user.getNomFanfaron()).forEach(pupitre -> {
                        pupitreDAO.removePupitreFromFanfaron(user.getNomFanfaron(), pupitre);
                    });
                    if (selectedPupitres != null) {
                        Arrays.stream(selectedPupitres).forEach(pupitre -> {
                            pupitreDAO.addPupitreToFanfaron(user.getNomFanfaron(), pupitre);
                        });
                    }

                    commissionDAO.getCommissionsByFanfaron(user.getNomFanfaron()).forEach(commission -> {
                        commissionDAO.removeCommissionFromFanfaron(user.getNomFanfaron(), commission);
                    });
                    if (selectedCommissions != null) {
                        Arrays.stream(selectedCommissions).forEach(commission -> {
                            commissionDAO.addCommissionToFanfaron(user.getNomFanfaron(), commission);
                        });
                    }

                    request.setAttribute("pupitres", pupitreDAO.getAllPupitres());
                    request.setAttribute("commissions", commissionDAO.getAllCommissions());
                    request.setAttribute("userPupitres", pupitreDAO.getPupitresByFanfaron(user.getNomFanfaron()));
                    request.setAttribute("userCommissions", commissionDAO.getCommissionsByFanfaron(user.getNomFanfaron()));
                    request.setAttribute("message", "Vos groupes ont été mis à jour avec succès!");
                    break;

                default:
                    response.sendError(404, "Action non supportée : " + action);
                    return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Une erreur est survenue : " + e.getMessage());
        }

        request.getRequestDispatcher(vue).forward(request, response);
    }
}