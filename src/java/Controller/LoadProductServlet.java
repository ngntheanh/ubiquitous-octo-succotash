/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import dal.CategoryDao;
import dal.ProductDao;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Category;
import model.Product;
import model.Ticket;

/**
 *
 * @author 3nick
 */
public class LoadProductServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        String id = request.getParameter("pid");
        ProductDao dao = new ProductDao();
        Product p = dao.getProductByID(id);

        CategoryDao da = new CategoryDao();
        List<Category> listC = da.getAll();
        List<Ticket> listT = da.getTicketAll();
        request.setAttribute("listCC", listC);
        request.setAttribute("detail", p);
        request.setAttribute("listP", listT);

        request.getRequestDispatcher("loadProduct.jsp").forward(request, response);

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
        String pid = request.getParameter("id");
        String pname = request.getParameter("name");
        String pimage = request.getParameter("image");
        String pprice = request.getParameter("price");
        String ticket_type = request.getParameter("ticket_type");
        String detail_description = request.getParameter("detail_description");
        String pdescription = request.getParameter("description");
        String pcategory = request.getParameter("category");

        ProductDao dao = new ProductDao();
        String err = "";
        int n = Integer.parseInt(pprice);
        if (n > 0) {
            String ppprice = String.valueOf(n);
            dao.editProduct(pname, pimage, ppprice, ticket_type, detail_description, pdescription, pcategory, pid);
            response.sendRedirect("manage");
        } else {
            err = "Gia tien > 0";
            int ppcategory = Integer.parseInt(pcategory);
            int pticket_type = Integer.parseInt(ticket_type);
            request.setAttribute("err", err);
            request.setAttribute("detail", new Product(pid, ppcategory, pname, pimage, pticket_type, pdescription));
            request.getRequestDispatcher("loadProduct.jsp").forward(request, response);
        }

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
