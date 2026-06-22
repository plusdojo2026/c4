<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <script src="https://unpkg.com/@zxing/library@latest"></script>
  <link rel="stylesheet" href="/c4/css/common.css">
  <link rel="stylesheet" href="/c4/css/stock.css">

  <title>サカグラ | 在庫一覧</title>
</head>

<body>
  <!-- ここでheader.jspをinclude -->
  <%@ include file="/WEB-INF/jsp/header.jsp" %>

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
        <div class="button-wrapper">
          <button id="add-button" type="button">
            <img class="active" src="/c4/img/add_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.svg" alt="在庫新規追加">
          </button>
          <button id="camera-button" type="button">
            <img id="camera-on" class="active" src="/c4/img/videocam_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.svg"
              alt="カメラON">
            <img id="camera-off" class="" src="/c4/img/videocam_off_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.svg"
              alt="カメラOFF">
          </button>
        </div>
        <table>
          <thead>
            <tr>
              <th>JANコード</th>
              <th>商品名</th>
              <th>在庫数</th>
            </tr>
          </thead>
          <tbody id="stock-table-body">
            <c:forEach var="s" items="${stockList}">
              <tr
                class="stock-row"
                data-id="${s.id}"
                data-jan="${s.jancode}"
                data-product-name="${s.productName}"
                data-stock-quantity="${s.stockQuantity}"
                data-duration-days="${s.durationDays}">
                <td>${s.jancode}</td>
                <td>${s.productName}</td>
                <td>${s.stockQuantity}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </section>
    </main>
    <c:if test="${isSearched and empty productList or isInserted}">
      <dialog class="new-check-dialog">
        <p>
          <c:if test="${not empty jancode}">
            <span>JANコード: ${jancode}</span>
          </c:if>
          <c:if test="${not empty productName}">
            <span>商品名: ${productName}</span>
          </c:if>
          は商品マスタに登録がありません。
        </p>
        <p>新規商品を追加しますか？</p>
        <form action="${pageContext.request.contextPath}/product" method="get">
          <input type="hidden" name="jancode" value="${jancode}">
          <input type="hidden" name="productName" value="${productName}">
          <div class="dialog-btn-wrapper">
            <button type="button" class="cancel-btn btn">
              キャンセル
            </button>
            <button type="submit" class="btn">
              追加する
            </button>
          </div>
        </form>
      </dialog>
    </c:if>
    <dialog class="stock-add-dialog">
      <form id="stock-add-form" class="stock-form" action="${pageContext.request.contextPath}/stock/add" method="post">  
        <div class="">
          <fieldset>
            <legend>JANコード</legend>
            <input type="text" name="jancode" placeholder="JANコードを入力してください。" pattern="\d{13}" maxlength="13" inputmode="numeric" required>
          </fieldset>
          <fieldset>
            <legend>商品名</legend>
            <input type="text" name="productName" placeholder="商品名を入力してください。" required>
          </fieldset>
          <fieldset>
            <legend>在庫追加数</legend>
            <input class="add-quantity" type="number" name="quantity" value="1" min="1" required>
          </fieldset>
          <div class="">
            <button type="button" class="btn increment-btn">増 +</button>
            <button type="button" class="btn decrement-btn">減 -</button>
          </div>
          <div class="">
            <fieldset>
              <legend>入庫日</legend>
              <input class="receivedAt" type="date" name="receivedAt" value="" required>
            </fieldset>
            <fieldset>
              <legend>通知日</legend>
              <input class="notifyAt" type="date" name="notifyAt" value="" required>
            </fieldset>
          </div>
        </div>
        <div class="dialog-btn-wrapper">
          <button type="button" class="cancel-btn btn">
            キャンセル
          </button>
          <button type="submit" class="btn">
            追加
          </button>
        </div>
      </form>
    </dialog>
    <dialog class="stock-edit-dialog">
      <p>JANコード: <span class="dialog-jan"></span></p>
      <p>商品名: <span class="dialog-product-name"></span></p>
      <p>在庫数: <span class="dialog-stock"></span></p>
      <form id="stock-edit-form" class="stock-form" action="${pageContext.request.contextPath}/stock/edit" method="post">
        <input class="id" type="hidden" name="id" value="">
        <input class="jancode" type="hidden" name="jancode" value="">
        <input class="product-name" type="hidden" name="productName" value="">
        <input class="new-quantity" type="hidden" name="newQuantity" value="">
        <div class="">
          <fieldset>
            <legend>在庫変更数</legend>
            <input class="change-quantity" type="number" name="changeQuantity" value="0" required>
          </fieldset>
          <div class="">
            <button type="button" class="btn increment-btn">入庫 +</button>
            <button type="button" class="btn decrement-btn">出庫 -</button>
          </div>
          <fieldset>
            <legend>理由</legend>
            <textarea class="reason" name="reason"></textarea>
          </fieldset>
          <!-- <div class="">
            <fieldset>
              <legend>入庫日</legend>
              <input class="receivedAt" type="date" name="receivedAt" value="">
            </fieldset>
            <fieldset>
              <legend>通知日</legend>
              <input class="" type="date" name="notifyAt">
            </fieldset>
          </div> -->
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