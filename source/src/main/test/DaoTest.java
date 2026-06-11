package test;

import java.time.LocalDate;
import java.util.List;

import dao.ProductDAO;
import dao.StockMovementDAO;
import model.Product;
import model.StockMovement;

public class DaoTest {
	
	public static void main(String[] args) {
		System.out.println("=== DAOのテストを開始します ===\n");
		
		// ---------------------------------------------------------
		// テスト1: ProductDAO のデータ取得（getObjectのテスト）
		// ---------------------------------------------------------
		System.out.println("【テスト1: 商品リストの取得】");
		ProductDAO productDao = new ProductDAO();
		List<Product> productList = productDao.selectAll();
		
		if (productList.isEmpty()) {
			System.out.println("商品はまだ登録されていません。");
		} else {
			for (Product p : productList) {
				System.out.println("JANコード: " + p.getJanCode());
				System.out.println("商品名: " + p.getProductName());
				// ★ここがエラーにならずに日時の形で表示されれば成功！
				System.out.println("作成日: " + p.getCreatedAt());
				System.out.println("更新日: " + p.getUpdatedAt());
				System.out.println("-------------------------");
			}
		}
		
		// ---------------------------------------------------------
		// テスト2: StockMovementDAO のデータ登録（setObjectのテスト）
		// ---------------------------------------------------------
		System.out.println("\n【テスト2: 入出庫履歴の登録】");
		
		// ※注意: テストを実行する前に、productsテーブルとstocksテーブルに
		// 該当のデータ（JAN: 1234567890123, stock_id: 1）が存在しないと
		// 外部キー制約エラーになります。適宜書き換えてください。
		
		StockMovementDAO movementDao = new StockMovementDAO();
		StockMovement movement = new StockMovement();
		movement.setJancode("1234567890123"); // 存在するJANコードにする
		movement.setStockId(1); // 存在する在庫IDにする
		movement.setReason("新規入荷");
		movement.setQuantity(24);
		
		// Java側で日付をセット
		LocalDate today = LocalDate.now();
		movement.setReceivedAt(today);
		movement.setNotifyAt(today.plusDays(30)); // 30日後を通知日とする
		
		// 登録実行
		boolean isSuccess = movementDao.insert(movement);
		
		if (isSuccess) {
			System.out.println("=> 入出庫データの登録に成功しました！");
			System.out.println("=> 入荷日: " + movement.getReceivedAt());
			System.out.println("=> 通知日: " + movement.getNotifyAt());
		} else {
			System.out.println("=> 登録に失敗しました（外部キーエラー等の可能性があります）");
		}
	}
}