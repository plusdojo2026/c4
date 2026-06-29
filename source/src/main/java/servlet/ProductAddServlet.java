package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dao.ProductDAO;
import model.Product;

@WebServlet("/ProductAddServlet")
@MultipartConfig
public class ProductAddServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        ProductDAO dao = new ProductDAO();
        List<Product> list = dao.selectAll(); 
        
        int isCase = Integer.parseInt(request.getParameter("isCase"));
        //String photoPath = saveImage(request.getPart("add-photo"));
		 String photoPathCase = saveImage(request.getPart("add-photo"));
		String photoPathBara = saveImage(request.getPart("bara-photo"));
        //  単品
        if (isCase == 0) {
        	
        	if (isDuplicate(list, request.getParameter("jan"), request.getParameter("productname"))) {
                request.getSession().setAttribute("error", "JAN または商品名が既に登録されていたため登録できませんでした。");
                request.getSession().setAttribute("errorflag", true); 
                response.sendRedirect(request.getContextPath() + "/product");
                return;
            }
        	
            Product p = new Product(
                request.getParameter("jan"),
                request.getParameter("productname"),
                null,
                1,                
                photoPathCase,//photoPath,
                Integer.parseInt(request.getParameter("term")),
                null, null
            );
            dao.insert(p);
//            p.setBaseProductId(p.getJanCode());
//            dao.update(p);
        }

       
     // ▼ ケース
        else {

            if (isDuplicate(list, request.getParameter("jan"), request.getParameter("productname"))) {
                request.getSession().setAttribute("error", "JAN または商品名が既に登録されていたため登録出来ませんでした");
                request.getSession().setAttribute("errorflag", true); 
                response.sendRedirect(request.getContextPath() + "/product");
                return;
            }

            String selected = request.getParameter("singleSelect");
            String baraJan, baraName;
            int baraTerm;

            // baraJan を決める（既存 or 新規）
            if (selected != null && !selected.isEmpty()) {
                // 既存バラ
                baraJan = selected;
                baraName = request.getParameter("selectedName");
                baraTerm = Integer.parseInt(request.getParameter("selectedTerm"));
                
             //  既存バラの画像を取得して photoPathBara にセット
                Product existing = dao.findByJan(baraJan);
                if (existing != null) {
                    photoPathBara = existing.getPhotoPath();
                }
            } else {
                // 新規バラ
                baraJan = request.getParameter("baraJan");
                baraName = request.getParameter("baraName");
                baraTerm = Integer.parseInt(request.getParameter("baraTerm"));
            }

            //   ここでチェック（既存・新規どちらも）
            if (dao.isBaseUsed(baraJan)) {
                request.getSession().setAttribute("error", "このバラ商品はすでに別のケース商品に使われています。");
                request.getSession().setAttribute("errorflag", true);
                response.sendRedirect(request.getContextPath() + "/product");
                return;
            }

            //  新規バラなら登録
            if (selected == null || selected.isEmpty()) {
                Product bara = new Product(
                    baraJan, baraName, null, 1, photoPathBara, baraTerm, null, null
                );                             //photoPathBara
                dao.insert(bara);
            }

            //  ケース商品登録
            Product kase = new Product(
                request.getParameter("jan"),
                request.getParameter("productname"),
                baraJan,
                Integer.parseInt(request.getParameter("caseQty")),
                photoPathCase, //photoPathCase,
                Integer.parseInt(request.getParameter("term")),
                null, null
            );
            dao.insert(kase);
        }


        response.sendRedirect(request.getContextPath() +"/product");
        }
    

    private String saveImage(Part filePart) throws IOException {
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        if (fileName.isEmpty()) return "";

        String uploadPath = getServletContext().getRealPath("/img/");
        new File(uploadPath).mkdirs();
        filePart.write(uploadPath + File.separator + fileName);

        return "/c4/img/" + fileName;
    }
    private boolean isDuplicate(List<Product> list, String jan, String name) {
        for (Product p : list) {
            if (p.getJanCode().equals(jan) || p.getProductName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}

