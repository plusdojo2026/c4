<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

		<link rel="stylesheet" href="/c4/css/common.css">
		<link rel="stylesheet" href="/c4/css/header.css">

		<header class="global-header">
			<div class="header-logo">
				<span class="logo-text">サカグラ</span>
			</div>

			<!-- not empty sessionScope.id -->
			<c:if test="${true}">
				<form id="header-search" class="header-search">
					<input id="search-input" type="text" class="search-input" placeholder="検索バー" value="">
					<button class="btn">検索</button>
				</form>

				<div class="header-notification">
					<div class="notification-wrapper" id="notificationBtn">
						<img class="notification-icon" src="/c4/img/notifications_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.svg"
							alt="通知">
						<span class="notification-badge"></span>
					</div>

					<div class="notification-modal" id="notificationModal">
						<div class="modal-header">
							<h3>お知らせ</h3>
							<button class="close-btn" id="closeModalBtn">&times;</button>
						</div>
						<ul class="notification-list">
							<c:choose>
								<c:when test="${not empty headerNotifications}">
									<c:forEach var="notification" items="${headerNotifications}">
										<li class="notification-item ${notification.isRead == 0 ? 'unread' : ''}"
											data-id="${notification.id}">
											<p>
												<c:out value="${notification.message}" />
											</p>
											<span class="time">
												<c:out value="${notification.formattedCreatedAt}" />
											</span>
										</li>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<li class="notification-item">
										<p>現在、お知らせはありません。</p>
									</li>
								</c:otherwise>
							</c:choose>
						</ul>
					</div>
				</div>
			</c:if>
		</header>

		<c:if test="${true}">
			<script src="/c4/js/header.js"></script>
		</c:if>