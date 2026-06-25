package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccountDAO;
import model.Account;


@WebServlet("/admin/add")
public class AccountAddServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

 @Override
 protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

		// 1.新規追加モーダルの入力フォームから値を取得	
		
		String name = request.getParameter("name");
		String year = request.getParameter("year");
        String month = request.getParameter("month");
        String day = request.getParameter("day");
        String password = request.getParameter("password");// 管理者が決める最初のパスワード
		int permissionsId = Integer.parseInt(request.getParameter("permissionsId"));

        // 2.年月日を「yyyy-mm-dd」の表記に変更
        String birthday = String.format("%s%02d%02d", year, Integer.parseInt(month), Integer.parseInt(day));

        // 3.引数5つのコンストラクタを使ってオブジェクトを作成
        Account account = new Account(0, name, birthday, password, permissionsId);

        // 4. DAOを呼び出してデータベースに挿入(INSERT)
        AccountDAO dao = new AccountDAO();
        boolean isSuccess = dao.insert(account);

        if (isSuccess) {
            // セッションを用意する
            HttpSession session = request.getSession();

            // 登録したデータをそのままセッションに入れる
            session.setAttribute("showAddResult", true);
            session.setAttribute("addedName", name);
            session.setAttribute("addedPerm", permissionsId == 1 ? "管理者" : "従業員");
            session.setAttribute("addedPass", password);

             // 5. 処理終了後、AccountServlet(従業員一覧)へリダイレクト
            response.sendRedirect(request.getContextPath() + "/admin");
        } else {
            System.out.println("データベースへの登録に失敗しました。");
            response.sendRedirect(request.getContextPath() + "/admin");
        }
 }
}