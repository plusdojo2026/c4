package servlet;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StockDAO;
import model.Stock;
import model.StockMovement;

@WebServlet("/stock/add")
public class StockAddServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        String jancode = request.getParameter("jancode");
        String productName = request.getParameter("productName");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String reason = request.getParameter("reason");
        LocalDate receivedAt = LocalDate.parse(request.getParameter("receivedAt"));
        LocalDate notifyAt = LocalDate.parse(request.getParameter("notifyAt"));

        Stock stock = new Stock();
        stock.setJancode(jancode);
        stock.setStockQuantity(quantity);
        stock.setStores(1);

        StockMovement movement = new StockMovement();
        movement.setReason(reason);
        movement.setReceivedAt(receivedAt);
        movement.setNotifyAt(notifyAt);

        StockDAO dao = new StockDAO();

        if(dao.insert(stock, movement)) {
            response.sendRedirect(request.getContextPath() + "/stock");
        } else {
            request.setAttribute("isInserted", true);
            request.setAttribute("jancode", jancode);
            request.setAttribute("productName", productName);
            request.setAttribute("stockList", dao.selectAll());

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/stock.jsp");
            dispatcher.forward(request, response);
        }
    }
}

