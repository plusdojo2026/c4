package filter;

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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.NotificationDAO;
import model.Notification;

@WebFilter("/*")
public class PermissionFilter implements Filter {
	public void init(FilterConfig fConfig) throws ServletException {
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String path = httpRequest.getRequestURI();
		String contextPath = httpRequest.getContextPath();
		
		// 静的リソース（CSS、JS、画像）はチェック不要
		if (isStaticResource(path)) {
			chain.doFilter(request, response);
			return;
		}
		
		// ログイン・パスワードリセットはチェック不要
		if (isLoginPage(path)) {
			chain.doFilter(request, response);
			return;
		}
		
		
		/* 
		// ログイン状態を確認
		HttpSession session = httpRequest.getSession(false);
		if (session == null || session.getAttribute("id") == null) {
			// ログインしていない場合はログインページへ
			httpResponse.sendRedirect(contextPath + "/login");
			return;
		}
		*/
		
		
		// 管理者ページのアクセス制御
		Integer permissionsId = null;
		HttpSession session1 = httpRequest.getSession(false);
		if (session1 != null) {
			permissionsId = (Integer) session1.getAttribute("permissionsId");
		}
		if (isAdminPage(path) && (permissionsId == null || permissionsId != 1)) {
			// 管理者ではない場合は商品ページへ
			httpResponse.sendRedirect(contextPath + "/product");
			return;
		}
		
		// 通知を取得
		NotificationDAO dao = new NotificationDAO();
		List<Notification> notifications = dao.selectActiveNotifications();
		
		// 未読の通知数をカウント
		long unreadCount = notifications.stream()
			.filter(n -> n.getIsRead() == 0)
			.count();
		
		request.setAttribute("headerNotifications", notifications);
		request.setAttribute("unreadCount", unreadCount);
		
		chain.doFilter(request, response);
	}
	
	// 静的リソースかどうかを判定
	private boolean isStaticResource(String path) {
		return path.contains("/css/")
				|| path.contains("/js/") 
				|| path.contains("/img/")
				|| path.contains("/lib/");
	}
	
	// ログイン関連ページかどうかを判定
	private boolean isLoginPage(String path) {
		return path.contains("/login")
				|| path.contains("/passwordReset")
				|| path.contains("/logout");
	}
	
	// 管理者ページかどうかを判定
	private boolean isAdminPage(String path) {
		return path.contains("/admin/*");
	}
	
	public void destroy() {
	}
}
