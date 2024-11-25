/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package indy;

import indy.dao.IndyWinnerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author strif
 */
@WebServlet(name = "IndyWinner", urlPatterns = {"/IndyWinner"})
public class IndyWinnerServlet extends HttpServlet {
    public static final String PAGE_NUMBER = "PAGE_NUMBER";
    public static final String WINNER_LIST = "WINNER_LIST";
    public static final String LAST_PAGE = "LAST_PAGE";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Boolean lastPage = Boolean.FALSE;
        String pageString = request.getParameter("page");
        int page = Integer.parseInt(pageString);
        request.getSession().setAttribute(PAGE_NUMBER, Integer.valueOf(page));
        IndyWinnerDAO dao = new IndyWinnerDAO();
        List<IndyWinner> indyWinnerList = dao.findWinners(page);
        request.getSession().setAttribute(WINNER_LIST, indyWinnerList);
        int totalRows = dao.recordCount();
        lastPage = Boolean.valueOf(totalRows < (page * 10));
        request.getSession().setAttribute(LAST_PAGE, lastPage);
        request.getRequestDispatcher("/indy.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
