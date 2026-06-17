package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProductDAO;
import dao.StockDAO;
import model.Product;
import model.Stock;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
                String search = request.getParameter("search");
        //検索結果が空の場合は空文字にする
        if (search == null) {
            search = "";
        }
        String keyword = search.trim();
        
        // 検索元のページURLを取得
        String sourcePage = request.getParameter("sourcePage");
        
        // DAOを使ってデータを取得
        ProductDAO productDao = new ProductDAO();
        StockDAO stockDao = new StockDAO();
        
        List<Product> productList = productDao.search(keyword);
        List<Stock> stockList = stockDao.search(keyword);
        
        // リクエストスコープにセット
        request.setAttribute("productList", productList);
        request.setAttribute("stockList", stockList);
        request.setAttribute("keyword", keyword);

        // 検索フラグ
        request.setAttribute("isSearched", true);
        
        // 検索元のURLを判定して、戻るべきJSPファイルのパスを決定する
        String forwardPath = "/WEB-INF/jsp/product.jsp"; // デフォルトは商品一覧にしておく
        
        if (sourcePage != null) {
            if (sourcePage.contains("stock")) {
                forwardPath = "/WEB-INF/jsp/stock.jsp"; // 在庫画面から検索された場合
            } else if (sourcePage.contains("product")) {
                forwardPath = "/WEB-INF/jsp/product.jsp"; // 商品画面から検索された場合
            }
        }
        
        // 元の画面にフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
        dispatcher.forward(request, response);
    }
}