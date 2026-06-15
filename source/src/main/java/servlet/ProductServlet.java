package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProductDAO;
import model.Product;

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        // ① DAO から商品一覧を取得（DAO完成次第）
        ProductDAO dao = new ProductDAO();
        List<Product> productList = dao.selectAll();

        // ② JSP に渡す
        request.setAttribute("productList", productList);

        // ③ product.jsp に forward
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/product.jsp");
        dispatcher.forward(request, response);
    }
}
