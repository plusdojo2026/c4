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

@WebServlet("/product")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	
    	// 在庫ページから受け取る   	
    	String jancode = request.getParameter("jancode");
    	String productName = request.getParameter("productName");
    	
    	if (jancode != null && !jancode.isEmpty() &&
    		    productName != null && !productName.isEmpty()) {
    	    request.setAttribute("jancode", jancode);
    	    request.setAttribute("productName", productName);
    	}
    	
    	// 削除結果パラメータを受け取る
    	String success = request.getParameter("success");
    	String fail = request.getParameter("fail");

    	if (success != null || fail != null) {
    	    request.setAttribute("showDeleteResult", true);
    	    request.setAttribute("success", success);
    	    request.setAttribute("fail", fail);
    	}

        //  DAO から商品一覧を取得
        ProductDAO dao = new ProductDAO();
        List<Product> productList = dao.selectAll();

        //  JSP に渡す
        request.setAttribute("productList", productList);

        //  product.jsp に forward
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/product.jsp");
        dispatcher.forward(request, response);
    }
}
