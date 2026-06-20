<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<!DOCTYPE html>
		<html>

		<head>
			<meta charset="UTF-8">
			<title>商品一覧</title>
			<!-- /webappつける -->
			<link rel="stylesheet" href="/c4/css/common.css">
			<link rel="stylesheet" href="/c4/css/stock.css">
			<link rel="stylesheet" href="/c4/css/product.css">

		</head>

		<body>

			<div class="product">
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
										<button id="edit-button">
											<img class="active" src="/c4/img/edit_square_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.svg" alt="編集">
										</button>
										<button id="camera-button">
											<img id="camera-on" class="active"
												src="/c4/img/videocam_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.svg" alt="カメラON">
											<img id="camera-off" class=""
												src="/c4/img/videocam_off_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.svg" alt="カメラOFF">
										</button>
										<button id="add-button" type="button">
											<img class="active" src="/c4/img/add_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.svg" alt="在庫新規追加">
										</button>
										<!-- 削除ボタン -->
										<button id="delete-check" class="option">削除</button>
									</div>





									<table>
										<thead>
											<tr>
												<!-- 全選択チェックボックス -->
												<th><input type="checkbox" class="option" id="check-all"></th>
												<th>商品画像</th>
												<th>JANコード</th>
												<th>商品名</th>
												<th>期間</th>
											</tr>
										</thead>
										<tbody id="product-table-body">
											<!-- サーブレット完成次第 -->
											<c:forEach var="p" items="${productList}">
												<tr data-base-product-id="${p.baseProductId}" data-case-quantity="${p.caseQuantity}">
													<td><input type="checkbox" class="edit-check" name="checks" value="${p.janCode}"></td>
													<td><img src="${p.photoPath}"></td>
													<td class="td-jan">${p.janCode}</td>
													<td class="td-name">${p.productName}</td>
													<td class="td-term">${p.durationDays}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</section>
							</main>
					</div>




					<!-- 新規追加ポップアップ（確認） -->
					<dialog id="dialog-new-confirm" class="newcheck">
						<p>新規商品を追加しますか？</p>
						<button id="new-dialog-closeButton" class="btn">キャンセル</button>
						<button id="new-dialog-addButton" class="btn">追加する</button>
					</dialog>


					<!-- 新規追加モーダル -->
					<dialog id="dialog-new-modal" class="newmodal">
						<button id="dialog-new-modal-closeButton" class="btn">閉じる</button>
						<img src="#" class="product-photo" id="preview">

						<form action="/c4/ProductAddServlet" method="post" enctype="multipart/form-data">
							<div class="newform">

								<input type="file" id="add-photo" name="add-photo" accept="image/*">
								<!-- ケース選択 -->
								<div class="case-block">
									<label>
										<input type="radio" name="isCase" value="0" id="case-no" checked>
										単品
									</label>

									<label>
										<input type="radio" name="isCase" value="1" id="case-yes">
										ケース商品
									</label>
								</div>

								<input type="number" id="JAN" name="jan" placeholder="JANを入力" required>
								<input type="text" id="pname" name="productname" placeholder="商品名を入力" required>
								<input type="number" id="term" name="term" placeholder="期間を入力" required>

								<!-- 入数入力 -->
								<div id="case-options">
									<label>入数</label>
									<input type="number" id="case-qty" name="caseQty" value="1" min="1">
								</div>



								<!-- 単品プルダウン -->
								<select id="single-select" name="singleSelect">
									<option value="">選択してください</option>

								</select>
								<!-- 既存単品の情報をサーブレットへ渡す -->
								<input type="hidden" id="selectedName" name="selectedName">
								<input type="hidden" id="selectedTerm" name="selectedTerm">



								<!-- バラフォーム -->
								<div id="bara-form">
									<label>バラJAN</label>
									<input type="text" id="bara-jan" name="baraJan" required>

									<label>バラ商品名</label>
									<input type="text" id="bara-name" name="baraName" required>

									<label>バラ期間</label>
									<input type="number" id="bara-term" name="baraTerm" required>
								</div>

								<!-- メッセージ -->
								<p id="single-message">
									既存の単品を選択しています。
									バラ商品の入力内容を変更すると、既存単品の情報が更新されます。
								</p>

							</div>

							<button type="submit" class="btn regist">登録</button>
						</form>
					</dialog>


					<!-- 削除フォーム -->
					<form id="delete-form" action="/c4/product/delete" method="post">
						<input type="hidden" name="deleteIds" id="delete-ids">
					</form>


					<!-- 削除確認ポップアップ -->
					<dialog class="deletecheck">
						<p>〇件選択されています。<br>選択商品を削除しますか？</p>
						<button id="cancel2" class="btn">キャンセル</button>
						<button id="delete" class="btn">削除する</button>
					</dialog>


					<!-- 削除結果通知ポップアップ -->
					<dialog class="deleteresult">
						<p>成功: ${success} 件<br>失敗: ${fail} 件</p>
						<c:if test="${fail > 0}">
							<p>「JANコード」は在庫があるため削除できませんでした。</p>
						</c:if>
						<button id="cancel3" class="btn">キャンセル</button>
					</dialog>


					<!-- 削除通知 -->
					<c:if test="${showDeleteResult}">
						<script>
							document.addEventListener("DOMContentLoaded", () => {
								const dialog4 = document.querySelector(".deleteresult");
								dialog4.showModal();
								requestAnimationFrame(() => {
									dialog4.classList.add("show");
								});
							});
						</script>
					</c:if>


					<!-- 編集ポップアップ -->
					<dialog class="edit">

						<button id="cancel4" class="btn">閉じる</button>

						<form action="/c4/product/edit" method="post">
							<input type="hidden" name="id" id="edit-id">
							<!-- 表示項目 -->
							<input type="hidden" id="edit-photo" name="photoPath">
							<div>
								<input type="number" id="edit-jan" name="janCode" placeholder="JANを入力" readonly>
							</div>
							<div class="edit-comment-jan">※JANコードの編集は出来ません</div>
							<input type="text" id="edit-name" name="productName" placeholder="商品名を入力" required>
							<input type="number" id="edit-term" name="durationDays" placeholder="期間を入力" required>
							<!-- 非表示項目 -->
							<input type="hidden" id="edit-base" name="baseProductId">
							<input type="hidden" id="edit-case" name="caseQuantity">

							<button type="submit" class="btn edit">保存</button>
							<button type="button" id="delete-check2" class="btn delete">削除</button>
						</form>

					</dialog>

					
					<script src="/c4/js/product.js"></script>
			</div>
		</body>

		</html>