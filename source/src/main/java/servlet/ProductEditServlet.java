package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProductDAO;
import model.Product;


@WebServlet("/product/edit")
public class ProductEditServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    	request.setCharacterEncoding("UTF-8");
        Product p = new Product();
        p.setJanCode(request.getParameter("janCode"));
        p.setProductName(request.getParameter("productName"));
        p.setBaseProductId(request.getParameter("baseProductId"));
        p.setCaseQuantity(Integer.parseInt(request.getParameter("caseQuantity")));
        p.setPhotoPath(request.getParameter("photoPath"));
        p.setDurationDays(Integer.parseInt(request.getParameter("durationDays")));

        ProductDAO dao = new ProductDAO();
        dao.update(p);

        response.sendRedirect("/c4/product");
    }
}

