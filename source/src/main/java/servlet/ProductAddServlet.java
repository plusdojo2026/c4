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

@MultipartConfig 
@WebServlet("/ProductAddServlet")
public class ProductAddServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		//jspから受け取る		
		String jan = request.getParameter("jan");
		String name = request.getParameter("productname");
		String termStr = request.getParameter("term");
		
		int term = Integer.parseInt(termStr);
		
		//プロダクトオブジェクトにセット		
		Product p = new Product();
        p.setJanCode(jan);
        p.setProductName(name);
        p.setDurationDays(term);

        //  画像ファイルを受け取る
        Part filePart = request.getPart("add-photo");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        if (fileName != null && !fileName.isEmpty()) {

            // 保存先フォルダ（/images/）
            String uploadPath = getServletContext().getRealPath("/c4/img/");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            // 画像を保存
            filePart.write(uploadPath + File.separator + fileName);

            // DB に保存するパス
            p.setPhotoPath("/c4/img/" + fileName);

        } else {
            p.setPhotoPath("");
        }
        
        p.setBaseProductId(jan);
        p.setCaseQuantity(0);
       
        
        //DAOに登録        
        ProductDAO dao = new ProductDAO();
        dao.insert(p);
        
        //一覧へ
        response.sendRedirect("/c4/ProductServlet");
		
	}
}
	
