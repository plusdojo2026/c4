<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <script src="https://unpkg.com/@zxing/library@latest"></script>
  <link rel="stylesheet" href="/c4/css/common.css">
  <link rel="stylesheet" href="/c4/css/permission.css">

  <title>サカグラ | 管理者</title>
</head>

<body>
  <!-- ここでheader.jspをinclude -->
  <!-- <%@ include file="/WEB-INF/jsp/header.jsp" %> -->

  <!-- sidebarと機能画面のラッパー用div -->
  <div class="wrapper">
    <!-- ここでsidebar.jspをinclude -->
    <%@ include file="/WEB-INF/jsp/sidebar.jsp" %>
    <main class="stock-content">
      <section class="stock-card">
        <div class="video-wrapper">
          <video id="video" autoplay playsinline disablePictureInPicture></video>
          <div class="barcode-frame">

          </div>
        </div>
        
        <table>
          <thead>
            <tr>
              <th>JANコード</th>
              <th>在庫数</th>
            </tr>
          </thead>
          <tbody id="stock-table-body">
            <c:forEach var="s" items="${stockList}">
              <tr
                class="stock-row"
                data-jan="${s.jancode}"
                data-stock-quantity="${s.stockQuantity}">
                <td>${s.jancode}</td>
                <td>${s.stockQuantity}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </section>
    </main>
    <dialog class="new-check-dialog">
      <p>商品マスタに登録がありません。</p>
      <p>新規商品を追加しますか？</p>
      <div class="dialog-btn-wrapper">
        <button type="button" class="cancel-btn btn">
          キャンセル
        </button>
        <button type="button" onclick="location.href=''" class="btn">
          追加する
        </button>
      </div>
    </dialog>
    <dialog class="stock-add-dialog">
      <p>JAN: <span class="dialog-jan"></span></p>
      <p>在庫数: <span class="dialog-stock"></span></p>
      <form id="stock-form" action="${pageContext.request.contextPath}/stock/edit" method="post">
        <input class="jancode" type="hidden" name="jancode" value="">
        <input class="new-quantity" type="hidden" name="newQuantity" value="">
        <div class="">
          <label>在庫変更数: <input class="change-quantity" type="number" name="changeQuantity" value="0"></label>
          <div class="">
            <button type="button" class="btn increment-btn">入庫 +</button>
            <button type="button" class="btn decrement-btn">出庫 -</button>
          </div>
        </div>
        <div class="dialog-btn-wrapper">
          <button type="button" class="cancel-btn btn">
            キャンセル
          </button>
          <button type="submit" class="btn">
            更新
          </button>
        </div>
      </form>
    </dialog>
  </div>
  <script src="/c4/js/stock.js"></script>
</body>

</html>