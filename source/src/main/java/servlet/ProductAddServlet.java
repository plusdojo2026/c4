package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProductDAO;
import model.Product;

@WebServlet("/ProductAddServlet")
public class ProductAddServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		//jspから受け取る		
		String jan = request.getParameter("jan");
		String name = request.getParameter("productname");
		String termStr = request.getParameter("term");
		
		int term = Integer.parseInt(termStr);
		
		//プロダクトオブジェクトにセット		
		Product p = new Product();
        p.setJanCode(jan);
        p.setProductName(name);
        p.setDurationDays(term);
        
        p.setBaseProductId(null);
        p.setCaseQuantity(0);
        p.setPhotoPath(null);
        
        //DAOに登録        
        ProductDAO dao = new ProductDAO();
        dao.insert(p);
        
        //一覧へ
        response.sendRedirect("/product");
		
	}
}
	
