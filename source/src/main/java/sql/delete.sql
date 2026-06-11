-- 一時的に外部キー制約のチェックを無効にする
SET FOREIGN_KEY_CHECKS = 0;

-- すべてのテーブルを空にする（AUTO_INCREMENTも1にリセットされる）
TRUNCATE TABLE stock_movements;
TRUNCATE TABLE stocks;
TRUNCATE TABLE accounts;
TRUNCATE TABLE products;
TRUNCATE TABLE permissions;
TRUNCATE TABLE notifications;

-- 外部キー制約のチェックを有効に戻す（※絶対に忘れないように！）
SET FOREIGN_KEY_CHECKS = 1;