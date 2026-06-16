package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccountDAO;

@WebServlet("/passwordReset")
public class PasswordResetServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/passwordReset.jsp");
        dispatcher.forward(request, response);
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String employeeNumber = request.getParameter("employeeNumber");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		String newPassword = request.getParameter("newPassword");
		
		 if (employeeNumber == null || employeeNumber.isEmpty()) {
        request.setAttribute("errorMsg", "社員番号を入力してください");

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/passwordReset.jsp");
        dispatcher.forward(request, response);
        return;
    }

		System.out.println("社員番号 : " + employeeNumber);
		System.out.println("生年月日 : " + year + "/" + month + "/" + day);
		System.out.println("新規パスワード : " + newPassword);

		// 仮の判定(あとでDAOの結果に置き換える)
		boolean result = false;

		try {

            int id = Integer.parseInt(employeeNumber);

            // birthdayカラムが DATE型(例: 2000-01-15)の場合
            String birthday = year + "-" + month + "-" + day;

            AccountDAO dao = new AccountDAO();

            result = dao.updatePassword(
                    id,
                    birthday,
                    newPassword);

        } catch (NumberFormatException e) {

            request.setAttribute(
                    "errorMsg",
                    "社員番号は数字で入力してください");

            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("/WEB-INF/jsp/passwordReset.jsp");
            dispatcher.forward(request, response);
            return;
        }

        if (result) {
            response.sendRedirect(
                    request.getContextPath() + "/;login");
        } else {
            request.setAttribute(
                    "errorMsg",
                    "社員番号または生年月日が間違っています");

            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("/WEB-INF/jsp/passwordReset.jsp");

            dispatcher.forward(request, response);
        }

		
	}
}