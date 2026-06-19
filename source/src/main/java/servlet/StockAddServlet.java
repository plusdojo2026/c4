package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import dao.StockDAO;
import model.Stock;

@WebServlet("/stock/add")
public class StockAddServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        String jancode = request.getParameter("jancode");
        String productName = request.getParameter("productName");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        Stock s = new Stock();
        s.setJancode(jancode);
        s.setProductName(productName);
        s.setStockQuantity(quantity);

        StockDAO dao = new StockDAO();

        if(dao.insert(s)) {
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

