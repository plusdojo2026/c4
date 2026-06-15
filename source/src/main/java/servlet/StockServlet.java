package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StockDAO;
import model.Stock;

@WebServlet("/stock")
public class StockServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        StockDAO dao = new StockDAO();
        List<Stock> stockList = dao.selectAll();

        request.setAttribute("stockList", stockList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/stock.jsp");
        dispatcher.forward(request, response);
    }
}
