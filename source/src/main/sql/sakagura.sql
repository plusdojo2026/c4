-- 1. 権限テーブル
CREATE TABLE `permissions` (
    `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '権限ID',
    `permission` VARCHAR(100) NOT NULL COMMENT '管理者:1一般ユーザー:2'
) COMMENT='権限';

-- 2. アカウントテーブル
CREATE TABLE `accounts` (
    `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '社員番号',
    `name` VARCHAR(124) NOT NULL COMMENT '氏名',
    `birthday` VARCHAR(8) NOT NULL COMMENT '生年月日',
    `password` VARCHAR(255) NOT NULL COMMENT 'パスワード',
    `permissions_id` INT NOT NULL COMMENT '権限テーブルのID',
    FOREIGN KEY (`permissions_id`) REFERENCES `permissions`(`id`)
) COMMENT='アカウントデータ';

-- 3. 商品マスターテーブル
CREATE TABLE `products` (
    `jan_code` VARCHAR(13) PRIMARY KEY COMMENT 'JAN',
    `product_name` VARCHAR(255) NOT NULL COMMENT '商品名',
    `base_product_id` VARCHAR(13) NULL COMMENT '紐づけ先（ケースのみバラを紐づけ）',
    `case_quantity` INT DEFAULT 1 COMMENT '入数（ケース24本、バラ1本。デフォルト: 1）',
    `photo_path` VARCHAR(255) NULL COMMENT '商品写真のパスを格納',
    `duration_days` INT NULL COMMENT '入荷日から通知日までの期間',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '初回作成日',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最終更新日'
) COMMENT='商品マスター';

-- 4. 在庫テーブル
CREATE TABLE `stocks` (
    `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
    `jancode` VARCHAR(13) NOT NULL COMMENT 'JANコード',
    `stock_quantity` INT NULL COMMENT 'ケースは在庫数を持たない',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '初回作成日',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最終更新日',
    `stores` INT DEFAULT 1 COMMENT '店舗 (デフォルト: 1)',
    FOREIGN KEY (`jancode`) REFERENCES `products`(`jan_code`)
) COMMENT='在庫';

-- 5. 入出庫テーブル
CREATE TABLE `stock_movements` (
    `jancode` VARCHAR(13) NOT NULL COMMENT 'JAN',
    `stock_id` INT NOT NULL COMMENT '在庫テーブルのID',
    `reason` VARCHAR(100) NOT NULL COMMENT '理由',
    `quantity` INT NOT NULL COMMENT '増減数',
    `received_at` DATE DEFAULT (CURRENT_DATE) COMMENT '現在日時（入荷日）',
    `notify_at` DATE NULL COMMENT '入荷日＋商品マスタの期間（通知日）',
    FOREIGN KEY (`stock_id`) REFERENCES `stocks`(`id`),
    FOREIGN KEY (`jancode`) REFERENCES `products`(`jan_code`)
) COMMENT='入出庫';

-- 6. 通知ログテーブル
CREATE TABLE `notifications` (
    `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    `message` VARCHAR(255) NOT NULL COMMENT '通知内容を記録する',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '初回作成日',
    `is_read` INT DEFAULT 0 COMMENT '既読フラグ (0: 未読)',
    `is_deleted` INT DEFAULT 0 COMMENT '削除フラグ (0: 未削除)'
) COMMENT='通知ログ';