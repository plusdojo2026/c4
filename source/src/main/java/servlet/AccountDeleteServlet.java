package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProductDAO;

@WebServlet("/product/delete")
public class ProductDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // hidden に入っている 文字列を取得
        String ids = request.getParameter("deleteIds");

        if (ids == null || ids.isEmpty()) {
            // 何も選択されていない場合は一覧へ戻す
            response.sendRedirect("/c4/product");
            return;
        }

        String[] janCodes = ids.split(",");

        ProductDAO dao = new ProductDAO();

        int success = 0;
        int fail = 0;

        // JANコードごとに削除
        for (String jan : janCodes) {
            if (dao.delete(jan)) {
                success++;
            } else {
                fail++;
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
