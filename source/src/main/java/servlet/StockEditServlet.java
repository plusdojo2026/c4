package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StockDAO;


@WebServlet("/product/edit")
public class StockEditServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        String jancode = request.getParameter("jancode");
        int newQuantity = Integer.parseInt(request.getParameter("newQuantity"));

        StockDAO dao = new StockDAO();
        dao.updateQuantity(jancode, newQuantity);

        response.sendRedirect("/stock");
    }
}

