-- 1. 権限テーブルのデータ
INSERT INTO permissions (permission) VALUES ('管理者'), ('一般ユーザー');

-- 2. アカウントテーブルのデータ (パスワードは仮で 'password123' などとしています)
INSERT INTO accounts (name, birthday, password, permissions_id) VALUES 
('山田 太郎', '19900101', 'password123', 1),
('佐藤 花子', '19951231', 'password123', 2);

-- 3. 商品マスターのデータ
INSERT INTO products (jan_code, product_name, base_product_id, case_quantity, photo_path, duration_days) VALUES 
('4901004008451', 'スーパードライ 350ml 缶', NULL, 1, '/images/superdry.jpg', 30),
('4901004008468', 'スーパードライ 350ml 24本ケース', '4901004008451', 24, '/images/superdry_case.jpg', 30),
('4901411011523', '一番搾り 350ml 缶', NULL, 1, '/images/ichiban.jpg', 30);

-- 4. 在庫テーブルのデータ
-- ※id=1がスーパードライ缶、id=2が一番搾り缶の在庫枠とします
INSERT INTO stocks (jancode, stock_quantity, stores) VALUES 
('4901004008451', 50, 1),
('4901411011523', 100, 1);

-- 5. 入出庫テーブルのデータ
INSERT INTO stock_movements (jancode, stock_id, reason, quantity, received_at, notify_at) VALUES 
('4901004008451', 1, '初回納品', 50, '2026-06-01', '2026-07-01'),
('4901411011523', 2, '初回納品', 100, '2026-06-10', '2026-07-10');

-- 6. 通知ログのデータ
INSERT INTO notifications (message, is_read, is_deleted) VALUES 
('JAN[4901004008451] の賞味期限確認日が近づいています。', 0, 0),
('新しい商品「一番搾り」がマスターに登録されました。', 1, 0);

-- 各テーブルのすべてのデータを見るためのコマンド
SELECT * FROM permissions;       -- 権限一覧
SELECT * FROM accounts;          -- アカウント一覧
SELECT * FROM products;          -- 商品マスター一覧
SELECT * FROM stocks;            -- 在庫一覧
SELECT * FROM stock_movements;   -- 入出庫履歴
SELECT * FROM notifications;     -- 通知一覧