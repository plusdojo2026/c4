package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccountDAO;
import model.Account;

@WebServlet("/account/delete")
public class AccountDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // JSでhidden(id="delete-ids") に入っている文字列を取得
        String idsStr = request.getParameter("deleteIds");

        if (idsStr == null || idsStr.isEmpty()) {
            // 何も選択されていない場合は従業員一覧へ戻す
            response.sendRedirect("/account/list");
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

        // JSP に結果を渡す
        request.setAttribute("success", success);
        request.setAttribute("fail", fail);
        request.setAttribute("showDeleteResult", true);

        // 一覧ページに成功と失敗の結果を持っていく（ポップアップ用）
        response.sendRedirect("/c4/product?success=" + success + "&fail=" + fail);
    }
}
