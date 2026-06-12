# DAOクラス 入出力データ一覧および使用ガイド

このドキュメントは、Controller（Servlet）側からデータベース処理（DAO）を呼び出す際の、メソッドの「入力データ（引数）」と「出力データ（戻り値）」の対応表と、その具体的な使い方をまとめたリファレンスです。

---

## 💡 前提知識：入力データ（引数）とは？
表の中に記載されている入力データの `account` や `product` とは、<b>「ユーザーが入力した情報や、登録したい情報がすべて詰まったデータの箱（オブジェクト）」</b>のことです。

システムでは、氏名やパスワード、商品名などの情報を「バラバラのデータ」としてDAOに渡すのではなく、事前に `model` パッケージに作成した「データの器（Account.java や Product.java など）」にすべての情報を詰め込み、<b>その箱を1つだけDAOに渡す</b>という設計になっています。

### 💻 Servlet（Controller）側での使い方のイメージ

**【例1：アカウントを新規登録する場合】**

```java
// 1. 画面からユーザーが入力したデータを受け取る
String inputName = request.getParameter("name");
String inputBirthday = request.getParameter("birthday");
String inputPassword = request.getParameter("password");
int inputPermission = Integer.parseInt(request.getParameter("permission"));

// 2. 空の Accountクラス（データの箱）を用意して、データを詰め込Sむ
Account account = new Account();
account.setName(inputName);
account.setBirthday(inputBirthday);
account.setPassword(inputPassword);
account.setPermissionsId(inputPermission);

// 3. DAOを呼び出し、データが詰まった箱（account）を渡して登録！
AccountDAO dao = new AccountDAO();
boolean isSuccess = dao.insert(account); 
```

**【例2：今日の通知を作成する場合（バッチ処理・定期実行など）】**

```java
LocalDate today = LocalDate.now();

// 1. 今日が「通知日」になっている入荷データをすべて取得
StockMovementDAO movementDao = new StockMovementDAO();
List<StockMovement> targets = movementDao.selectByNotifyDate(today);

// 2. 見つかったデータがあれば、通知を作成してDBに保存
NotificationDAO notifyDao = new NotificationDAO();
for (StockMovement target : targets) {
    Notification notification = new Notification();
    notification.setMessage("JAN[" + target.getJancode() + "] が通知日を迎えました。");
    notifyDao.insert(notification);
}
```

---

## 1. AccountDAO (アカウントデータ管理)
ログイン処理やアカウント作成、パスワード再設定などを行います。

| メソッド名 | 入力データ (引数) | 型 | 出力データ (戻り値) | 型 | 概要 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **`loginCheck`** | `id`<br>`password` | `int`<br>`String` | 該当アカウントの情報<br>（失敗時は `null`） | `Account` | 入力されたIDとパスワードでログイン認証を行う |
| **`insert`** | `account` | `Account` | 登録の成功可否 | `boolean` | 新しいアカウントをデータベースに登録する |
| **`updatePassword`** | `id`<br>`birthday`<br>`newPassword` | `int`<br>`String`<br>`String` | 更新の成功可否 | `boolean` | IDと生年月日で本人確認し、新しいパスワードに変更する |

---

## 2. ProductDAO (商品マスター管理)
商品の取得、追加、編集、削除（CRUD処理）を行います。

| メソッド名 | 入力データ (引数) | 型 | 出力データ (戻り値) | 型 | 概要 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **`selectAll`** | なし | - | 全商品のリスト<br>（0件なら空のリスト） | `List<Product>` | 登録されている全ての商品マスターを取得する |
| **`insert`** | `product` | `Product` | 登録の成功可否 | `boolean` | 新しい商品マスターを登録する |
| **`update`** | `product` | `Product` | 更新の成功可否 | `boolean` | 既存の商品情報（商品名、入数、期間など）を更新する |
| **`delete`** | `janCode` | `String` | 削除の成功可否 | `boolean` | 指定したJANコードの商品マスターを削除する |

---

## 3. StockDAO (在庫管理)
現在の在庫状況の取得や、在庫数の更新を行います。

| メソッド名 | 入力データ (引数) | 型 | 出力データ (戻り値) | 型 | 概要 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **`selectAll`** | なし | - | 全在庫のリスト<br>（0件なら空のリスト） | `List<Stock>` | 現在の在庫一覧を取得する（最新登録順） |
| **`insert`** | `stock` | `Stock` | 登録の成功可否 | `boolean` | 商品マスター追加時などに、新規の在庫枠を作成する |
| **`updateQuantity`** | `janCode`<br>`newQuantity` | `String`<br>`int` | 更新の成功可否 | `boolean` | 特定の商品の総在庫数を新しい値に上書きする |

---

## 4. StockMovementDAO (入出庫履歴管理)
商品の入出庫が発生した際の履歴データを記録します。また、通知日の検索も行います。

| メソッド名 | 入力データ (引数) | 型 | 出力データ (戻り値) | 型 | 概要 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **`insert`** | `movement` | `StockMovement` | 登録の成功可否 | `boolean` | 入出庫履歴（入荷日、通知日、理由、個数など）を記録する |
| **`selectByNotifyDate`** | `targetDate` | `LocalDate` | 指定日の入出庫履歴リスト<br>（0件なら空のリスト） | `List<StockMovement>` | 指定された日付が「通知日」に設定されている入出庫履歴を取得する |

---

## 5. NotificationDAO (通知ログ管理)
賞味期限や入荷等のシステム通知データを管理します。

| メソッド名 | 入力データ (引数) | 型 | 出力データ (戻り値) | 型 | 概要 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **`selectActiveNotifications`** | なし | - | 通知ログのリスト<br>（0件なら空のリスト） | `List<Notification>` | 削除されていない（有効な）通知一覧を新しい順に取得する |
| **`insert`** | `notification` | `Notification` | 登録の成功可否 | `boolean` | 期限切れ間近などの新しい通知をデータベースに作成・登録する |
| **`markAsRead`** | `id` | `int` | 更新の成功可否 | `boolean` | 指定したIDの通知を既読状態（`is_read = 1`）に変更する |