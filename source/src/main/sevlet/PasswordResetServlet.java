package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PasswordResetServlet")
public class PasswordResetServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String employeeNumber = request.getParameter("employeeNumber");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		String newPassword = request.getParameter("newPassword");

		System.out.println("社員番号 : " + employeeNumber);
		System.out.println("生年月日 : " + year + "/" + month + "/" + day);
		System.out.println("新規パスワード : " + newPassword);

		// 仮の判定(あとでDAOの結果に置き換える)
		boolean result = true;

		if (result) {
			response.sendRedirect("/webapp/LoginServlet");
		} else {
			request.setAttribute("errorMsg","社員番号または生年月日が間違っています");

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/passwordReset.jsp");
		dispatcher.forward(request, response);
		}
	}
}