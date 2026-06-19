package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccountDAO;
import model.Account;


@WebServlet("/account/edit")
public class AccountEditServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        Account p = new Account();
        p.setJanCode(request.getParameter("janCode"));
        p.setProductName(request.getParameter("productName"));
        p.setBaseProductId(request.getParameter("baseProductId"));
        p.setCaseQuantity(Integer.parseInt(request.getParameter("caseQuantity")));
        p.setPhotoPath(request.getParameter("photoPath"));
        p.setDurationDays(Integer.parseInt(request.getParameter("durationDays")));

        AccountDAO dao = new AccountDAO();
        dao.update(p);

        response.sendRedirect("/c4/admin");
    }
}

