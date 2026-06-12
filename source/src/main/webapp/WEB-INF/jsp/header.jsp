<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

	<link rel="stylesheet" href="/c4/css/common.css">
	<link rel="stylesheet" href="/c4/css/header.css">

	<header class="global-header">
		<div class="header-logo">
			<span class="logo-text">サカグラ</span>
		</div>

		<form id="header-search" class="header-search">
			<input id="search-input" type="text" class="search-input" placeholder="検索バー">
			<button class="btn">検索</button>
		</form>

		<div class="header-notification">
			<div class="notification-wrapper" id="notificationBtn">
				<img class="notification-icon" src="/c4/img/notifications_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.svg" alt="通知">
				<span class="notification-badge"></span>
			</div>

			<div class="notification-modal" id="notificationModal">
				<div class="modal-header">
					<h3>お知らせ</h3>
					<button class="close-btn" id="closeModalBtn">&times;</button>
				</div>
				<ul class="notification-list">
					<li class="notification-item unread">
						<p>ビールの期限が近づいています。</p> <span class="time">10分前</span>
					</li>
					<li class="notification-item">
						<p>ワインの期限が近づいています。</p> <span class="time">1時間前</span>
					</li>
					<li class="notification-item">
						<p>お酒の期限が近づいています。</p> <span class="time">昨日</span>
					</li>
					<li class="notification-item">
						<p>お酒の期限が近づいています。</p> <span class="time">昨日</span>
					</li>
					<li class="notification-item">
						<p>お酒の期限が近づいています。</p> <span class="time">昨日</span>
					</li>
					<li class="notification-item">
						<p>お酒の期限が近づいています。</p> <span class="time">昨日</span>
					</li>
					<li class="notification-item">
						<p>お酒の期限が近づいています。</p> <span class="time">昨日</span>
					</li>
				</ul>
			</div>

		</div>
	</header>

	<script src="/c4/js/header.js"></script>