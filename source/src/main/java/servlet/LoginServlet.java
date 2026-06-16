package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccountDAO;
import model.Account;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ログインページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストパラメータを取得する
		request.setCharacterEncoding("UTF-8");
		String idStr = request.getParameter("employeeNumber");
		String password = request.getParameter("password");

		// 1.社員番号を数値(int)に変換する
		int id = 0;
		if (idStr != null && !idStr.isEmpty()) {
			id = Integer.parseInt(idStr);
		}

		// 2.AccountDAOを指定してインスタンス化する
		AccountDAO accountDao = new AccountDAO();

		// 3.DAOのloginCheckメソッドを呼び出し、アカウント情報を取得する
		Account loginUserAccount = accountDao.loginCheck(id, password);

		// 4.nullでなければログイン成功
		if (loginUserAccount != null) { 

			// ログイン成功：セッションスコープに社員番号（文字列）をそのまま格納する
			HttpSession session = request.getSession();
			session.setAttribute("id", idStr); //文字列として保存

			// 取得したアカウント情報から権限IDを取り出してセッションに格納
			session.setAttribute("permissionsId", loginUserAccount.getPermissionsId());

			// プロダクトサーブレットにリダイレクトする
			response.sendRedirect("/c4/product");
		} else {
			// ログイン失敗
			// リクエストスコープに入力された社員番号を戻す
			request.setAttribute("id", idStr);
			
			//文字列としてエラーメッセージを格納する
			request.setAttribute("errorMsg", "社員番号またはパスワードに間違いがあります。");

			// ログインページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
		}
	}
}