package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import dao.NotificationDAO;
import model.Notification;

@WebFilter("/*")
public class NotificationFilter implements Filter {
	public void init(FilterConfig fConfig) throws ServletException {
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String path = httpRequest.getRequestURI();
		
		// 通知処理を除外するページ
		boolean isExcludedPage = path.endsWith("/login.jsp")
				|| path.endsWith("/LoginServlet")
				|| path.endsWith("/passwordReset.jsp")
				|| path.endsWith("/PasswordResetServlet");
		
		// CSS, JS, 画像などの静的ファイルも除外
		boolean isStaticResource = path.contains("/css/")
				|| path.contains("/js/")
				|| path.contains("/img/");
		
		// 除外対象のページ、または静的ファイルの場合はDBアクセスをスキップしてそのまま通す
		if (isExcludedPage || isStaticResource) {
			chain.doFilter(request, response);
			return; // 処理をここで終了し、下のDAO処理には進まない
		}

		NotificationDAO dao = new NotificationDAO();
		List<Notification> notifications = dao.selectActiveNotifications();
		
		long unreadCount = notifications.stream().filter(n -> n.getIsRead() == 0).count();
		
		request.setAttribute("headerNotifications", notifications);
		request.setAttribute("unreadCount", unreadCount);
		
		chain.doFilter(request, response);
	}
	
	public void destroy() {
	}
}