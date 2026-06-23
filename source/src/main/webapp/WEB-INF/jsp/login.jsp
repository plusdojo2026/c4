<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>サカグラ｜ログイン</title>
		<link rel="stylesheet" href="/c4/css/login.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
	</head>

	<body>
		 <%-- ヘッダーの読み込み --%>
	<%@ include file="header.jsp"%>
	<div class="login-reset-page">
		<div class="login-reset">
			<h1>ログイン</h1>
			<form id="login-form" method="post" action="/c4/login">

				<div id="msg">
					<c:choose>
					<%-- errorMsgに何か値が入っている（空ではない）場合 --%>
					<c:when test="${not empty errorMsg}">
						<span style="color: red; font-weight: bold;">						
							${errorMsg}<!--サーバー側のエラー(DBの不一致等)があればそれを表示-->
						</span>
						</c:when>
						<c:otherwise>
							社員番号とパスワードを入力してください。<!--初期表示-->
						</c:otherwise>
					</c:choose>
				</div>
				<div class="wrapper">
					<fieldset class="input-box">
						<legend>社員番号</legend>
						<input type="text" id="employeeNumber" name="employeeNumber" value="${id}">
					</fieldset>

					<fieldset class="input-box">
						<legend>パスワード</legend>
						<input type="password" id="password" name="password">						
						<i id="togglePassword" class="fa fa-eye-slash" style="cursor: pointer;"></i>
					</fieldset>
				</div>

				<div class="remember-forgot">
					パスワードを忘れた方は<a href="/c4/passwordReset">こちら</a>
				</div>

				<div class="login-btn">
					<button type="submit" id="loginBtn" name="loginBtn" value="ログイン">ログイン</button>
				</div>

			</form>

		</div>
		<footer>

		</footer>
		<script src="/c4/js/login.js"></script>
		</div>
	</body>

	</html>