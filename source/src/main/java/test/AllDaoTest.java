package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import dao.AccountDAO;
import dao.NotificationDAO;
import dao.ProductDAO;
import dao.StockDAO;
import dao.StockMovementDAO;
import model.Account;
import model.Notification;
import model.Product;
import model.Stock;
import model.StockMovement;

public class AllDaoTest {
	// データベース接続情報
	private static final String URL = "jdbc:mysql://localhost:3306/c4?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true";
	private static final String USER = "root";
	private static final String PASS = "password";
	
	public static void main(String[] args) {
		System.out.println("=== サカグラ 統合DAOテストを開始します ===\n");
		
		// 0. 事前準備：権限データの挿入（アカウント登録時の外部キーエラーを防ぐため）
		setupPermissions();
		
		// 過去のテストデータのお掃除
		cleanupTestData();
		
		// 1. AccountDAO のテスト
		testAccountDao();
		
		// 2. ProductDAO のテスト
		testProductDao();
		
		// 3. StockDAO のテスト（※ProductDAOのテストで作成した商品に対する在庫）
		int newStockId = testStockDao();
		
		// 4. StockMovementDAO のテスト（※StockDAOのテストで作成した在庫IDを使用）
		if (newStockId != -1) {
			testStockMovementDao(newStockId);
		} else {
			System.out.println("[スキップ] StockMovementDAOのテスト（有効な在庫IDが取得できなかったため）");
		}
		
		// 5. NotificationDAO のテスト
		testNotificationDao();
		
		System.out.println("\n=== すべてのDAOテストが完了しました ===");
	}
	
	/* ---------------------------------------------------------
	 * 0. 事前準備 (JDBCを直接使って権限データを登録)
	 * --------------------------------------------------------- */
	private static void setupPermissions() {
		System.out.println("【事前準備】権限マスターの確認・登録");
		String sql = "INSERT IGNORE INTO permissions (id, permission) VALUES (1, '管理者'), (2, '一般ユーザー')";
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement pStmt = conn.prepareStatement(sql)) {
			pStmt.executeUpdate();
			System.out.println(" => OK\n");
		} catch (SQLException e) {
			System.out.println(" => 失敗: " + e.getMessage() + "\n");
		}
	}
	
	/* ---------------------------------------------------------
	 * 1. AccountDAO のテスト
	 * --------------------------------------------------------- */
	private static void testAccountDao() {
		System.out.println("【テスト1: AccountDAO】");
		AccountDAO dao = new AccountDAO();
		
		// ① 登録テスト
		Account newAccount = new Account();
		newAccount.setName("テスト 太郎");
		newAccount.setBirthday("20000101");
		newAccount.setPassword("pass123");
		newAccount.setPermissionsId(1); // 権限: 管理者
		
		boolean isInserted = dao.insert(newAccount);
		System.out.println(" 1-1. アカウントの登録: " + (isInserted ? "成功" : "失敗（既に同名データがある等の可能性）"));
		
		// ② ログインチェックテスト
		Account loginResult = dao.loginCheck(9999, "pass123");
		System.out.println(" 1-2. ログインチェック(存在しないID): " + (loginResult == null ? "正常(null)" : "異常"));

		// ③ パスワード再設定テスト（新規追加分）
		boolean isPwUpdated = dao.updatePassword(9999, "20000101", "newpass123");
		System.out.println(" 1-3. パスワード再設定(存在しないID): " + (!isPwUpdated ? "正常に失敗(対象なし)" : "異常"));
		
		System.out.println("----------------------------------------");
	}
	
	/* ---------------------------------------------------------
	 * 2. ProductDAO のテスト
	 * --------------------------------------------------------- */
	private static void testProductDao() {
		System.out.println("【テスト2: ProductDAO】");
		ProductDAO dao = new ProductDAO();
		String testJan = "9999999999999";
		
		// ② 登録テスト（INSERT）
		Product p = new Product();
		p.setJanCode(testJan);
		p.setProductName("【テスト用】幻の酒");
		p.setBaseProductId(null);
		p.setCaseQuantity(1);
		p.setPhotoPath("/img/test.jpg");
		p.setDurationDays(30);
		
		boolean isInserted = dao.insert(p);
		System.out.println(" 2-1. 商品の登録: " + (isInserted ? "成功" : "失敗"));
		
		// ③ 更新テスト（UPDATE）
		p.setProductName("【テスト用】幻の酒(更新済)");
		boolean isUpdated = dao.update(p);
		System.out.println(" 2-2. 商品の更新: " + (isUpdated ? "成功" : "失敗"));
		
		// ④ 取得テスト（SELECT）
		List<Product> list = dao.selectAll();
		boolean found = list.stream().anyMatch(item -> item.getJanCode().equals(testJan));
		System.out.println(" 2-3. 商品一覧の取得: " + (list.size() > 0 ? "成功(" + list.size() + "件)" : "失敗"));
		System.out.println(" 2-4. 登録した商品の確認: " + (found ? "見つかりました" : "見つかりません"));

		// ⑤ あいまい検索テスト（新規追加分）
		List<Product> searchResult = dao.search("幻の酒");
		System.out.println(" 2-5. 商品のあいまい検索: " + (searchResult.size() > 0 ? "成功(" + searchResult.size() + "件ヒット)" : "失敗"));

		System.out.println("----------------------------------------");
	}
	
	/* ---------------------------------------------------------
	 * 3. StockDAO のテスト
	 * --------------------------------------------------------- */
	private static int testStockDao() {
		System.out.println("【テスト3: StockDAO】");
		StockDAO dao = new StockDAO();
		String testJan = "9999999999999"; // ProductDAOで登録したJAN
		
		// ① 登録テスト（INSERT）
		Stock s = new Stock();
		s.setJancode(testJan);
		s.setStockQuantity(50);
		s.setStores(1);
		
		boolean isInserted = dao.insert(s);
		System.out.println(" 3-1. 在庫枠の登録: " + (isInserted ? "成功" : "失敗"));
		
		// ② 更新テスト（UPDATE）
		boolean isUpdated = dao.updateQuantity(testJan, 80);
		System.out.println(" 3-2. 在庫数の更新: " + (isUpdated ? "成功" : "失敗"));
		
		// ③ 取得テスト（SELECT）と最新のStockIDの取得
		List<Stock> list = dao.selectAll();
		System.out.println(" 3-3. 在庫一覧の取得: " + (list.size() > 0 ? "成功(" + list.size() + "件)" : "失敗"));
		
		int latestStockId = -1;
		for (Stock stock : list) {
			if (stock.getJancode().equals(testJan)) {
				latestStockId = stock.getId();
				System.out.println("   => 作成された在庫のID: " + latestStockId);
				break; // 最新のもの（降順なら最初のもの）を取得
			}
		}

		// ④ あいまい検索テスト（新規追加分）
		List<Stock> searchResult = dao.search("幻の酒");
		System.out.println(" 3-4. 在庫のあいまい検索: " + (searchResult.size() > 0 ? "成功(" + searchResult.size() + "件ヒット)" : "失敗"));

		System.out.println("----------------------------------------");
		return latestStockId;
	}
	
	/* ---------------------------------------------------------
	 * 4. StockMovementDAO のテスト
	 * --------------------------------------------------------- */
	private static void testStockMovementDao(int stockId) {
		System.out.println("【テスト4: StockMovementDAO】");
		StockMovementDAO dao = new StockMovementDAO();
		String testJan = "9999999999999";
		
		// ① 登録テスト（INSERT）
		StockMovement sm = new StockMovement();
		sm.setJancode(testJan);
		sm.setStockId(stockId);
		sm.setReason("テスト入荷");
		sm.setQuantity(30);
		
		LocalDate today = LocalDate.now();
		sm.setReceivedAt(today);
		sm.setNotifyAt(today.plusDays(30)); // 通知日は30日後
		
		boolean isInserted = dao.insert(sm);
		System.out.println(" 4-1. 入出庫履歴の登録: " + (isInserted ? "成功" : "失敗"));
		
		// ② 通知日検索テスト（SELECT）
		List<StockMovement> targets = dao.selectByNotifyDate(today.plusDays(30));
		System.out.println(" 4-2. 指定通知日の検索: " + (targets.size() > 0 ? "成功(" + targets.size() + "件ヒット)" : "失敗(0件)"));
		System.out.println("----------------------------------------");
	}
	
	/* ---------------------------------------------------------
	 * 5. NotificationDAO のテスト
	 * --------------------------------------------------------- */
	private static void testNotificationDao() {
		System.out.println("【テスト5: NotificationDAO】");
		NotificationDAO dao = new NotificationDAO();
		
		// ① 登録テスト（INSERT）
		Notification n = new Notification();
		n.setMessage("【テスト】新しい商品が入荷されました。");
		boolean isInserted = dao.insert(n);
		System.out.println(" 5-1. 通知ログの登録: " + (isInserted ? "成功" : "失敗"));
		
		// ② 取得テスト（SELECT）
		List<Notification> list = dao.selectActiveNotifications();
		System.out.println(" 5-2. 有効な通知の取得: " + (list.size() > 0 ? "成功(" + list.size() + "件)" : "失敗"));
		
		// ③ 既読化テスト（UPDATE） - 新しい markAllAsRead に変更
		boolean isUpdated = dao.markAllAsRead();
		System.out.println(" 5-3. 未読通知の一括既読化: " + (isUpdated ? "成功" : "失敗"));
		
		System.out.println("----------------------------------------");
	}
	
	/* ---------------------------------------------------------
	 * ★ 過去のテストデータのお掃除
	 * --------------------------------------------------------- */
	private static void cleanupTestData() {
		System.out.println("【事前準備】過去のテストデータのお掃除");
		String testJan = "9999999999999";
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
			// 親（商品）を消すために、必ず子（履歴）→ 子（在庫）の順番で消す！
			conn.prepareStatement("DELETE FROM stock_movements WHERE jancode = '" + testJan + "'").executeUpdate();
			conn.prepareStatement("DELETE FROM stocks WHERE jancode = '" + testJan + "'").executeUpdate();
			conn.prepareStatement("DELETE FROM products WHERE jan_code = '" + testJan + "'").executeUpdate();
			System.out.println(" => お掃除完了\n");
		} catch (SQLException e) {
			System.out.println(" => お掃除失敗（初回なら問題なし）: " + e.getMessage() + "\n");
		}
	}
}