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
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ログインページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストパラメータを取得する
		request.setCharacterEncoding("UTF-8");
		String idStr = request.getParameter("employeeNumber");//クライアントが送ってきたデータの名前
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

			// ログイン成功：セッションスコープに社員番号を格納する
			HttpSession session = request.getSession();
			session.setAttribute("id", new model.LoginUser(idStr));

      //取得したアカウント情報から権限IDを取り出してセッションに格納
      session.setAttribute("permissionsId", loginUserAccount.getPermissionsId());

			// プロダクトサーブレットにリダイレクトする
			response.sendRedirect("/webapp/ProductServlet");
		} else {
      // ログイン失敗
			// リクエストスコープに入力された社員番号を戻す
      request.setAttribute("id", idStr);
			request.setAttribute("result", new model.Result("ログインできませんでした。", "社員番号またはパスワードに間違いがあります。", "/webapp/LoginServlet"));

			// ログインページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
		}
	}
}