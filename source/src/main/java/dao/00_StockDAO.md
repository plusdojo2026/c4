### 1. 新規登録時のメソッド

`insert(Stock stock, StockMovement movement)`

受け渡しする引数。

**`Stock stock`** にセットしておくべき値：

* `jancode` (JANコード)
* `stockQuantity` (初期在庫数)
* `stores` (店舗)

**`StockMovement movement`** にセットしておくべき値：

* `reason` (理由 ※空の場合は自動で"初期登録"になります)
* `receivedAt` (入庫日)
* `notifyAt` (通知日)

---

### 2. 更新時のメソッド

`update(Stock stock, StockMovement movement)`

受け渡しする引数。

**`Stock stock`** にセットしておくべき値：

* `id` (在庫テーブルのID / 主キー)
* `jancode` (JANコード)
* `stockQuantity` (変更後の **総在庫数**)

**`StockMovement movement`** にセットしておくべき値：

* `quantity` (今回の **増減数**)
* `reason` (理由 ※空の場合は自動で"入出庫"になります)
* `receivedAt` (入出庫日)
* `notifyAt` (通知日)