package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccountDAO;
import model.Account;


@WebServlet("/admin/edit")
public class AccountEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	
    	request.setCharacterEncoding("UTF-8");
    	
    	// 1.編集モーダル内のhiddenフィールドから従業員IDを取得して数値に変換
    	int id = Integer.parseInt(request.getParameter("id"));
    	
    	// 2.編集モーダル内の入力項目を取得
    	String newName = request.getParameter("newName");
    	int newPermissionsId = Integer.parseInt(request.getParameter("newPermissionsId"));

    	// 3.従業員(Account)オブジェクトに値をセット
        Account account = new Account();
        account.setId(id);
        account.setName(newName);
        account.setPermissionsId(newPermissionsId);
        
        // 4.DAOを呼び出してデータベースを更新(UPDATE)
        AccountDAO dao = new AccountDAO();
        dao.update(account);

        // 5.処理完了後、Accountサーブレットにリダイレクト
        response.sendRedirect(request.getContextPath() + "/admin");
    }
}