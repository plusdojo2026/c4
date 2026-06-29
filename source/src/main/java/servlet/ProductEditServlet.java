package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dao.ProductDAO;
import model.Product;


@WebServlet("/product/edit")
@MultipartConfig
public class ProductEditServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    	request.setCharacterEncoding("UTF-8");
        Product p = new Product();
        p.setJanCode(request.getParameter("janCode"));
        p.setProductName(request.getParameter("productName"));
        p.setBaseProductId(request.getParameter("baseProductId"));
        p.setCaseQuantity(Integer.parseInt(request.getParameter("caseQuantity")));
//        p.setPhotoPath(request.getParameter("photoPath"));
        p.setDurationDays(Integer.parseInt(request.getParameter("durationDays")));

     //  画像ファイル取得
        Part imagePart = request.getPart("imageFile");
        String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();

        //  新しい画像が選ばれていたら保存
        if (fileName != null && !fileName.isEmpty()) {

            String uploadPath = getServletContext().getRealPath("/img/");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            String savePath = uploadPath + File.separator + fileName;
            imagePart.write(savePath);

            // DB に保存するパス
            p.setPhotoPath("/c4/img/" + fileName);

        } else {
            //  新しい画像が選ばれていない → hidden の既存画像を使う
            p.setPhotoPath(request.getParameter("photoPath"));
        }
        
        ProductDAO dao = new ProductDAO();
        dao.update(p);

        response.sendRedirect("/c4/product");
    }
}

