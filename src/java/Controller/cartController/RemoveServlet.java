/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.cartController;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author vip2021
 */
public class RemoveServlet extends HttpServlet {

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
        Cookie arr[] = request.getCookies();
        String txt = "";
        for (Cookie o : arr) {
            if (o.getName().equals("id")) {
                txt = txt + o.getValue();
                o.setMaxAge(0);
                response.addCookie(o);
            }
        }
        String ids[] = txt.split("#");
        String txtOutPut = "";
        int check = 0;
        for (int i = 0; i < ids.length; i++) {
            if (!ids[i].equals(id)) {
                if (txtOutPut.isEmpty()) {
                    txtOutPut = ids[i];
                } else {
                    txtOutPut = txtOutPut + "#" + ids[i];
                }
            }
        }
        if (!txtOutPut.isEmpty()) {
            Cookie c = new Cookie("id", txtOutPut);
            c.setMaxAge(60 * 60 * 24);
            response.addCookie(c);
        }
        response.sendRedirect("cart");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the product ID to RemoveServlet from the request parameter
        String productIdToRemove = request.getParameter("pid");

        // Retrieve existing cookies
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            // Iterate through cookies to find the one containing product IDs
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("id")) {
                    // Split the cookie value to get individual product IDs
                    String[] productIds = cookie.getValue().split("#");

                    // Construct a new cookie value without the product to be removed
                    StringBuilder updatedCookieValue = new StringBuilder();
                    for (String productId : productIds) {
                        if (!productId.equals(productIdToRemove)) {
                            if (updatedCookieValue.length() > 0) {
                                updatedCookieValue.append("#");
                            }
                            updatedCookieValue.append(productId);
                        }
                    }

                    // Create a new cookie with the updated value
                    Cookie updatedCookie = new Cookie("id", updatedCookieValue.toString());
                    updatedCookie.setMaxAge(60 * 60 * 24); // Set the expiration time, if needed
                    response.addCookie(updatedCookie);

                    break; // Exit the loop after updating the cookie
                }
            }
        }

        // Redirect back to the cart page
        response.sendRedirect(request.getContextPath() + "/cart");
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
