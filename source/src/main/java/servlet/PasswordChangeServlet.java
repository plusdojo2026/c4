package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccountDAO;
import model.Account;

@WebServlet("/changePassword")
public class PasswordChangeServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		Integer id = null;
		Object sessionObj = session.getAttribute("id");
		if (sessionObj != null) {
			id = Integer.parseInt((String) sessionObj);
		}
		
		if (id == null) {
			// JSON形式でエラー結果を返す
			out.print("{\"success\": false, \"message\": \"セッションが切れました。再度ログインしてください。\"}");
			return;
		}
		
		String currentPassword = request.getParameter("currentPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");
		
		if (newPassword == null || !newPassword.equals(confirmPassword)) {
			out.print("{\"success\": false, \"message\": \"確認用パスワードが一致しません。\"}");
			return;
		}
		
		AccountDAO dao = new AccountDAO();
		Account account = dao.loginCheck(id, currentPassword);
		
		if (account != null) {
			boolean isUpdated = dao.updatePasswordById(id, newPassword);
			if (isUpdated) {
				// 成功時：successをtrueにしてメッセージを返す
				out.print("{\"success\": true, \"message\": \"パスワードを変更しました。\"}");
			} else {
				out.print("{\"success\": false, \"message\": \"システムの不具合により更新に失敗しました。\"}");
			}
		} else {
			out.print("{\"success\": false, \"message\": \"現在のパスワードが間違っています。\"}");
		}
	}
}