package controleur;

import dao.CommissionJDBCDAO;
import dao.EvenementJDBCDAO;
import dao.PupitreJDBCDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import metier.Evenement;
import metier.Fanfaron;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/GestionEvenementServlet")
public class GestionEvenementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Fanfaron user = (Fanfaron) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("loginPage.jsp");
            return;
        }

        String action = request.getParameter("action");
        EvenementJDBCDAO dao = new EvenementJDBCDAO();

        try {
            switch (action) {
                case "afficher":
                    request.setAttribute("evenements", dao.findAll());
                    List<String> userCommissions = new CommissionJDBCDAO().getCommissionsByFanfaron(user.getNomFanfaron());
                    request.setAttribute("userCommissions", userCommissions);
                    request.getRequestDispatcher("Vue/listeEvenements.jsp").forward(request, response);
                    return;
                case "creer":
                    request.getRequestDispatcher("Vue/creationEvenement.jsp").forward(request, response);
                    return;
                case "modifier":
                    int id = Integer.parseInt(request.getParameter("id"));
                    Evenement evenement = dao.findById(id);
                    if (evenement != null) {
                        request.setAttribute("evenement", evenement);
                        request.getRequestDispatcher("Vue/modificationEvenement.jsp").forward(request, response);
                    } else {
                        response.sendError(404, "Événement non trouvé");
                    }
                    return;
                case "supprimer":
                    id = Integer.parseInt(request.getParameter("id"));
                    Evenement eventToDelete = dao.findById(id);
                    userCommissions = new CommissionJDBCDAO().getCommissionsByFanfaron(user.getNomFanfaron());
                    request.setAttribute("userCommissions", userCommissions);
                    if (!eventToDelete.getNomFanfaron().equals(user.getNomFanfaron()) && !userCommissions.contains("Prestation")) {
                        response.sendError(403, "Accès refusé : vous ne pouvez pas supprimer cet événement");
                        return;
                    }
                    if (dao.delete(id)) {
                        request.setAttribute("message", "Événement supprimé avec succès");
                    } else {
                        request.setAttribute("erreur", "Échec de la suppression de l'événement");
                    }
                    request.setAttribute("evenements", dao.findAll());
                    request.getRequestDispatcher("Vue/listeEvenements.jsp").forward(request, response);
                    return;
                case "inscription":
                    int eventId = Integer.parseInt(request.getParameter("id"));
                    Evenement event = dao.findById(eventId);
                    if (event != null) {
                        PupitreJDBCDAO pupitreDAO = new PupitreJDBCDAO();
                        request.setAttribute("evenement", event);
                        request.setAttribute("pupitres", pupitreDAO.getPupitresByFanfaron(user.getNomFanfaron()));
                        request.setAttribute("inscriptions", dao.getInscriptionsForEvent(eventId));
                        request.getRequestDispatcher("Vue/inscriptionEvenement.jsp").forward(request, response);
                    } else {
                        response.sendError(404, "Événement non trouvé");
                    }
                    return;
                default:
                    response.sendError(400, "Action non reconnue");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Erreur serveur: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Fanfaron user = (Fanfaron) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("loginPage.jsp");
            return;
        }

        String action = request.getParameter("action");
        EvenementJDBCDAO dao = new EvenementJDBCDAO();

        try {
            switch (action) {
                case "creer":
                    // Vérifier que l'utilisateur fait partie de la commission Prestation
                    List<String> commissions = new CommissionJDBCDAO().getCommissionsByFanfaron(user.getNomFanfaron());
                    if (!commissions.contains("Prestation")) {
                        response.sendError(403, "Accès refusé : vous n'avez pas les droits nécessaires");
                        return;
                    }
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("horodatage"), formatter);

                    Evenement newEvent = new Evenement(
                            0, // ID sera généré par la base de données
                            request.getParameter("nom"),
                            Timestamp.valueOf(dateTime),
                            Integer.parseInt(request.getParameter("duree")),
                            request.getParameter("lieu"),
                            request.getParameter("description"),
                            user.getNomFanfaron()
                    );
                    dao.insert(newEvent);
                    request.setAttribute("message", "Événement créé avec succès");
                    request.setAttribute("evenements", dao.findAll());
                    request.getRequestDispatcher("Vue/listeEvenements.jsp").forward(request, response);
                    break;
                case "modifier":
                    int id = Integer.parseInt(request.getParameter("id"));
                    Evenement existingEvent = dao.findById(id);
                    List<String> userCommissions = new CommissionJDBCDAO().getCommissionsByFanfaron(user.getNomFanfaron());
                    if (!existingEvent.getNomFanfaron().equals(user.getNomFanfaron()) && !userCommissions.contains("Prestation")) {
                        response.sendError(403, "Accès refusé : vous ne pouvez pas modifier cet événement");
                        return;
                    }
                    if (existingEvent != null && existingEvent.getNomFanfaron().equals(user.getNomFanfaron())) {
                        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime dt = LocalDateTime.parse(request.getParameter("horodatage"), fmt);

                        existingEvent.setNom(request.getParameter("nom"));
                        existingEvent.setHorodatage(Timestamp.valueOf(dt));
                        existingEvent.setDuree(Integer.parseInt(request.getParameter("duree")));
                        existingEvent.setLieu(request.getParameter("lieu"));
                        existingEvent.setDescription(request.getParameter("description"));

                        dao.update(existingEvent);
                        request.setAttribute("message", "Événement modifié avec succès");
                    } else {
                        request.setAttribute("erreur", "Vous ne pouvez pas modifier cet événement");
                    }
                    request.setAttribute("evenements", dao.findAll());
                    request.getRequestDispatcher("Vue/listeEvenements.jsp").forward(request, response);
                    break;
                case "inscription":
                    int eventId = Integer.parseInt(request.getParameter("eventId"));
                    String subAction = request.getParameter("subAction");
                        switch (subAction) {
                            case "ajouter":
                            case "modifier":
                                String pupitre = request.getParameter("pupitre");
                                String statut = request.getParameter("statut");
                                if (dao.inscrireFanfaron(user.getNomFanfaron(), eventId, pupitre, statut)) {
                                    request.setAttribute("message", "Inscription mise à jour avec succès");
                                } else {
                                    request.setAttribute("erreur", "Erreur lors de la mise à jour de l'inscription");
                                }
                                break;
                            case "supprimer":
                                if (dao.desinscrireFanfaron(user.getNomFanfaron(), eventId)) {
                                    request.setAttribute("message", "Désinscription effectuée avec succès");
                                } else {
                                    request.setAttribute("erreur", "Erreur lors de la désinscription");
                                }
                                break;
                        }

                        Evenement event = dao.findById(eventId);
                        PupitreJDBCDAO pupitreDAO = new PupitreJDBCDAO();
                        request.setAttribute("evenement", event);
                        request.setAttribute("pupitres", pupitreDAO.getPupitresByFanfaron(user.getNomFanfaron()));
                        request.setAttribute("inscriptions", dao.getInscriptionsForEvent(eventId));
                        request.getRequestDispatcher("Vue/inscriptionEvenement.jsp").forward(request, response);
                    break;
                default:
                    response.sendError(400, "Action non reconnue");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Erreur serveur: " + e.getMessage());
        }
    }
}