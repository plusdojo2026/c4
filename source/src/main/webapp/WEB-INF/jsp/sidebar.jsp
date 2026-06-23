<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<link rel="stylesheet" href="/c4/css/common.css">
<link rel="stylesheet" href="/c4/css/sidebar.css">

<aside class="global-sidebar">
	<div class="sidebar-top">
		<a href="/c4/product" class="sidebar-item" title="商品管理"> <img
			src="/c4/img/package_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.svg"
			class="sidebar-icon" alt="商品管理"> <span class="sidebar-label">商品管理</span>
		</a> <a href="/c4/stock" class="sidebar-item" title="在庫管理"> <img
			src="/c4/img/package_2_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.svg"
			class="sidebar-icon" alt="在庫管理"> <span class="sidebar-label">在庫管理</span>
		</a> <a href="/c4/admin" class="sidebar-item" title="管理者"> <img
			src="/c4/img/group_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.svg"
			class="sidebar-icon" alt="管理者"> <span class="sidebar-label">管理者</span>
		</a>
	</div>

	<div class="sidebar-bottom">
		<div class="sidebar-item" id="account-button" title="アカウント">
			<img
				src="/c4/img/account_circle_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.svg"
				class="sidebar-icon" alt="アカウント"> <span class="sidebar-label">アカウント</span>
		</div>

		<div id="account-modal" class="account-modal hidden">
			<div class="modal-user-info">
				<div class="modal-user-icon">
					<img
						src="/c4/img/attribution_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.svg"
						alt="アカウント">
				</div>
				<div class="modal-text-group">
					<span class="modal-emp-num">社員番号： <c:out
							value="${sessionScope.id}" />
					</span> <span class="modal-name">氏名： <c:out
							value="${sessionScope.name}" />
					</span>
				</div>
			</div>

			<button type="button" class="btn modal-pwd-btn"
				id="open-pwd-modal-btn">パスワード変更</button>
			<button class="btn modal-logout-btn">ログアウト</button>
		</div>

		<div id="pwd-modal-overlay" class="pwd-modal-overlay hidden"></div>
		<div id="password-modal" class="password-modal hidden">
			<h3 class="pwd-modal-title">パスワード変更</h3>

			<form action="/c4/changePassword" method="post" class="pwd-form"
				id="pwd-change-form">
				<div class="form-group">
					<label>現在のパスワード</label> <input type="password"
						name="currentPassword" required>
				</div>
				<div class="form-group">
					<label>新しいパスワード</label> <input type="password" id="new-password"
						name="newPassword" required>
				</div>
				<div class="form-group">
					<label>新しいパスワード（確認用）</label> <input type="password"
						id="confirm-password" name="confirmPassword" required>
				</div>

				<div id="pwd-match-msg" class="pwd-match-msg"></div>

				<div class="pwd-modal-actions">
					<button type="button" class="btn cancel-pwd-btn"
						id="close-pwd-modal-btn">キャンセル</button>
					<button type="submit" class="btn submit-pwd-btn"
						id="submit-pwd-btn" disabled>変更を確定</button>
				</div>
			</form>
		</div>
	</div>
</aside>

<script src="/c4/js/sidebar.js"></script>