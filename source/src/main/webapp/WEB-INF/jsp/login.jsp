<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="UTF-8">
		<title>サカグラ｜ログイン</title>
		<link rel="stylesheet" href="/c4/css/login.css">
	</head>

	<body>
	<div class="login-reset-page">
		<header>

		</header>
		<div class="login-reset">
			<h1>ログイン</h1>
			<form id="form" method="post" action="/c4/LoginServlet">

				<div id="msg">
					<c:choose>
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
					</fieldset>
				</div>

				<div class="remember-forgot">
					パスワードを忘れた方は<a href="passwordReset.jsp">こちら</a>
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