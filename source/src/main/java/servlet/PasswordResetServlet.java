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

	// パスワード再設定画面を表示
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/passwordReset.jsp");
        dispatcher.forward(request, response);
    }

	// パスワード再設定処理
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// 入力値を取得する
		String employeeNumber = request.getParameter("employeeNumber");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");

		// 生年月日をyyyymmdd形式に変換
		String birthday = year + String.format("%02d", Integer.parseInt(month)) + String.format("%02d", Integer.parseInt(day));
		String newPassword = request.getParameter("newPassword");
		String checkPassword = request.getParameter("checkPassword");
		
		// 社員番号の未入力チェック
		 if (employeeNumber == null || employeeNumber.isEmpty()) {
        request.setAttribute("errorMsg", "社員番号を入力してください。");

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/passwordReset.jsp");
        dispatcher.forward(request, response);
        return;
    }

		// 動作確認用ログ出力
		System.out.println("社員番号 : " + employeeNumber);
		System.out.println("生年月日 : " + year + "/" + month + "/" + day);
		System.out.println("新規パスワード : " + newPassword);
		System.out.println("確認用パスワード : " + checkPassword);


		// パスワード更新結果
		boolean result = false;

		try {

            int id = Integer.parseInt(employeeNumber);
            AccountDAO dao = new AccountDAO();

						// 社員番号と生年月日を照合し、一致した場合パスワードを更新
            result = dao.updatePassword(
                    id,
                    birthday,
                    newPassword);

        } catch (NumberFormatException e) {
					
					// 社員番号が数字でない場合
            request.setAttribute(
                    "errorMsg",
                    "社員番号は数字で入力してください");

            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("/WEB-INF/jsp/passwordReset.jsp");
            dispatcher.forward(request, response);
            return;
        }

				// パスワード更新が成功した場合ログイン画面に遷移
        if (result) {
            response.sendRedirect(
                    request.getContextPath() + "/login");
        } else {
					// 本人確認失敗
            request.setAttribute(
                    "errorMsg",
                    "社員番号または生年月日に間違いがあります。");

            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("/WEB-INF/jsp/passwordReset.jsp");
            dispatcher.forward(request, response);
        }

		
	}
}