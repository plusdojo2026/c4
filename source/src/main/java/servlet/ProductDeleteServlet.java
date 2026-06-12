package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/product/delete")
public class ProductDeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        // ① JSP から受け取る（カンマ区切りの ID）
        String idsParam = request.getParameter("deleteIds");

        // ② null チェック（何も選択されていない場合）
        if (idsParam == null || idsParam.isEmpty()) {
            request.setAttribute("success", 0);
            request.setAttribute("fail", 0);
            request.setAttribute("showDeleteResult", true);

            request.getRequestDispatcher("/WEB-INF/jsp/product.jsp")
                   .forward(request, response);
            return;
        }

        // ③ 配列に変換
        String[] ids = idsParam.split(",");

        // ④ 成功・失敗の件数
        int success = ids.length; // 全部成功したと仮定
        int fail = 0;

        // ⑤ JSP に渡す
        request.setAttribute("success", success);
        request.setAttribute("fail", fail);

        // ⑥ 削除結果ポップアップを開くためのフラグ
        request.setAttribute("showDeleteResult", true);

        // ⑦ product.jsp に戻す
        request.getRequestDispatcher("/WEB-INF/jsp/product.jsp")
               .forward(request, response);
    }
}
