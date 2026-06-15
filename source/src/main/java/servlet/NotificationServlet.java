package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.NotificationDAO;

@WebServlet("/notification/readAll")
public class NotificationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // すべての通知を既読にする処理
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        NotificationDAO dao = new NotificationDAO();
        boolean success = dao.markAllAsRead();
        
        if (success) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}