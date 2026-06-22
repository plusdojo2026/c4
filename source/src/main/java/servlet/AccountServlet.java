package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccountDAO;
import model.Account;

@WebServlet("/admin")
public class AccountServlet extends HttpServlet {
private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    	// 削除結果パラメータを受け取る
    	String success = request.getParameter("success");
    	String fail = request.getParameter("fail");

    	if (success != null || fail != null) {
    	    request.setAttribute("showDeleteResult", true);
    	    request.setAttribute("success", success);
    	    request.setAttribute("fail", fail);
    	}

        //  DAO から従業員一覧を取得
        AccountDAO dao = new AccountDAO();
        List<Account> accountList = dao.selectAll();

        //  JSP に渡す
        request.setAttribute("accountList", accountList);

        //  admin.jsp に forward
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
        dispatcher.forward(request, response);
    }
}
