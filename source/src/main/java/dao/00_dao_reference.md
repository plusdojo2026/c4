# DAOクラス 入出力データ一覧および使用ガイド

このドキュメントは、Controller（Servlet）側からデータベース処理（DAO）を呼び出す際の、メソッドの「入力データ（引数）」と「出力データ（戻り値）」の対応表と、その具体的な使い方をまとめたリファレンスです。

---

## 💡 前提知識：入力データ（引数）とは？

表の中に記載されている入力データの `account` や `product` とは、「ユーザーが入力した情報や、登録したい情報がすべて詰まったデータの箱（オブジェクト）」のことです。

システムでは、氏名やパスワード、商品名などの情報を「バラバラのデータ」としてDAOに渡すのではなく、事前に `model` パッケージに作成した「データの器（Account.java や Product.java など）」にすべての情報を詰め込み、**その箱を1つだけDAOに渡す**という設計になっています。

### 💻 Servlet（Controller）側での使い方のイメージ

**【例1：アカウントを新規登録する場合】**

```java
// 1. 画面からユーザーが入力したデータを受け取る
String inputName = request.getParameter("name");
String inputBirthday = request.getParameter("birthday");
String inputPassword = request.getParameter("password");
int inputPermission = Integer.parseInt(request.getParameter("permission"));

// 2. 空の Accountクラス（データの箱）を用意して、データを詰め込む
Account account = new Account();
account.setName(inputName);
account.setBirthday(inputBirthday);
account.setPassword(inputPassword);
account.setPermissionsId(inputPermission);

// 3. DAOを呼び出し、データが詰まった箱（account）を渡して登録！
AccountDAO dao = new AccountDAO();
boolean isSuccess = dao.insert(account); 

```

**【例2：在庫登録と入出庫履歴を同時に登録する場合（トランザクション）】**

```java
// 1. 在庫の基本情報をセット
Stock stock = new Stock();
stock.setJancode(jancode);
stock.setStockQuantity(quantity);
stock.setStores(1);

// 2. 入出庫の履歴情報をセット
StockMovement movement = new StockMovement();
movement.setReason("初期登録"); 
movement.setReceivedAt(LocalDate.now()); 
movement.setNotifyAt(calculatedDate);

// 3. DAOを呼び出し、2つの箱を渡して一括登録！
StockDAO dao = new StockDAO();
boolean isSuccess = dao.insert(stock, movement);

```

---

## 1. AccountDAO (アカウントデータ管理)

従業員情報の取得やログイン処理、アカウント作成、パスワード再設定などを行います。

| メソッド名 | 入力データ (引数) | 型 | 出力データ (戻り値) | 型 | 概要 |
| --- | --- | --- | --- | --- | --- |
| **`selectAll`** | なし | - | 全従業員のリスト (0件なら空のリスト) | `List<Account>` | 登録されている全従業員の一覧を降順で取得する |
| **`loginCheck`** | `id`, `password` | `int`, `String` | 該当アカウントの情報 (失敗時は `null`) | `Account` | 入力された社員番号とパスワードでログイン認証を行う |
| **`insert`** | `account` | `Account` | 登録の成功可否 | `boolean` | 新しいアカウントをデータベースに登録する |
| **`update`** | `account` | `Account` | 更新の成功可否 | `boolean` | 既存の従業員情報（氏名、権限）を更新する |
| **`updatePassword`** | `id`, `birthday`, `newPassword` | `int`, `String`, `String` | 更新の成功可否 | `boolean` | 社員番号と生年月日で本人確認し、新しいパスワードに変更する |
| **`updatePasswordById`** | `id`, `newPassword` | `int`, `String` | 更新の成功可否 | `boolean` | 社員番号を条件にパスワードを強制変更する |
| **`delete`** | `id` | `int` | 削除の成功可否 | `boolean` | 指定された社員番号の従業員を削除する |

---

## 2. ProductDAO (商品マスター管理)

商品の取得、追加、編集、削除（CRUD処理）、あいまい検索を行います。

| メソッド名 | 入力データ (引数) | 型 | 出力データ (戻り値) | 型 | 概要 |
| --- | --- | --- | --- | --- | --- |
| **`selectAll`** | なし | - | 全商品のリスト (0件なら空のリスト) | `List<Product>` | 登録されている全ての商品マスターを取得する |
| **`search`** | `keyword` | `String` | 検索結果のリスト (0件なら空のリスト) | `List<Product>` | 商品名またはJANコードの部分一致であいまい検索を行う |
| **`insert`** | `product` | `Product` | 登録の成功可否 | `boolean` | 新しい商品マスターを登録する |
| **`update`** | `product` | `Product` | 更新の成功可否 | `boolean` | 既存の商品情報（商品名、入数、期間など）を更新する |
| **`delete`** | `janCode` | `String` | 削除の成功可否 | `boolean` | 指定したJANコードの商品マスターを削除する |

---

## 3. StockDAO (在庫・入出庫管理)

現在の在庫状況の取得や、在庫数の更新、入出庫履歴の記録をトランザクションを用いて一括で行います。

| メソッド名 | 入力データ (引数) | 型 | 出力データ (戻り値) | 型 | 概要 |
| --- | --- | --- | --- | --- | --- |
| **`selectAll`** | なし | - | 全在庫のリスト (0件なら空のリスト) | `List<Stock>` | 現在の在庫一覧を取得する（最新登録順） |
| **`search`** | `keyword` | `String` | 検索結果のリスト (0件なら空のリスト) | `List<Stock>` | 商品名またはJANコードの部分一致で在庫データをあいまい検索する |
| **`insert`** | `stock`, `movement` | `Stock`, `StockMovement` | 登録の成功可否 | `boolean` | 新規商品の在庫枠作成と、初期入庫の履歴記録を同時に行う |
| **`update`** | `stock`, `movement` | `Stock`, `StockMovement` | 更新の成功可否 | `boolean` | 既存商品の総在庫数上書きと、今回の入出庫履歴記録を同時に行う |

---

## 4. NotificationDAO (通知ログ管理)

賞味期限や入荷等のシステム通知データを管理します。

| メソッド名 | 入力データ (引数) | 型 | 出力データ (戻り値) | 型 | 概要 |
| --- | --- | --- | --- | --- | --- |
| **`selectActiveNotifications`** | なし | - | 通知ログのリスト (0件なら空のリスト) | `List<Notification>` | 削除されていない（有効な）通知一覧を新しい順に取得する |
| **`insert`** | `notification` | `Notification` | 登録の成功可否 | `boolean` | 新しい通知メッセージをデータベースに作成・登録する |
| **`markAllAsRead`** | なし | - | 更新の成功可否 | `boolean` | すべての未読通知を一括で「既読」状態（`is_read = 1`）に更新する |
