package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccountDAO;

@WebServlet("/admin/delete")
public class AccountDeleteServlet extends HttpServlet {
     private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // JSでhidden(id="delete-ids") に入っている文字列を取得
        String idsStr = request.getParameter("deleteIds");

        if (idsStr == null || idsStr.isEmpty()) {
            // 何も選択されていない場合は従業員一覧へ戻す
            response.sendRedirect(request.getContextPath() + "/admin");
            return;
        }

        String[] accountIds = idsStr.split(",");

        AccountDAO dao = new AccountDAO();// 従業員用のDAOをインスタンス化

        int success = 0;
        int fail = 0;

        // 1件ずつ削除
        for (String idStr : accountIds) {
            try {
                // 文字列のIDを数値に変換
                int id = Integer.parseInt(idStr);

            if (dao.delete(id)) {
                success++;
            } else {
                fail++;
            }
                } catch (NumberFormatException e) {
                 fail++;// IDが文字列に変換できないエラーが出た場合は失敗にカウント
                }
        }

        // JSPに結果を渡すためにセッションを使用
        HttpSession session = request.getSession();
        session.setAttribute("success", success);
        session.setAttribute("fail", fail);
        session.setAttribute("showDeleteResult", true);



        // 一覧ページに成功と失敗の結果を持っていく（ポップアップ用）
        response.sendRedirect(request.getContextPath() + "/admin");
    }
}
