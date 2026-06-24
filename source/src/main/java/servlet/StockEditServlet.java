package servlet;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StockDAO;
import model.Stock;
import model.StockMovement;

@WebServlet("/stock/edit")
public class StockEditServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String jancode = request.getParameter("jancode");
        int newQuantity = Integer.parseInt(request.getParameter("newQuantity"));
        int changeQuantity = Integer.parseInt(request.getParameter("changeQuantity"));
        String reason = request.getParameter("reason");
        LocalDate receivedAt = LocalDate.parse(request.getParameter("receivedAt"));
        LocalDate notifyAt = LocalDate.parse(request.getParameter("notifyAt"));

        Stock stock = new Stock();
        stock.setId(id);
        stock.setJancode(jancode);
        stock.setStockQuantity(newQuantity);

        StockMovement movement = new StockMovement();
        movement.setQuantity(changeQuantity);
        movement.setReason(reason);
        movement.setReceivedAt(receivedAt);
        movement.setNotifyAt(notifyAt);

        StockDAO dao = new StockDAO();
        dao.update(stock, movement);

        response.sendRedirect(request.getContextPath() + "/stock");
    }
}

