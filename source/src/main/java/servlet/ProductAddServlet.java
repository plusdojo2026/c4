package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ProductAddServlet")
public class ProductAddServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		
		String jan = request.getParameter("jan");
		String name = request.getParameter("productname");
		String term = request.getParameter("term");
		
		//確認用		
		//request.setAttribute("jan", jan);
		//request.setAttribute("name", name);
		//request.setAttribute("term", term);
		
		RequestDispatcher dispatcher =
	            request.getRequestDispatcher("/WEB-INF/jsp/product.jsp");
	        dispatcher.forward(request, response);
	}
}
	
