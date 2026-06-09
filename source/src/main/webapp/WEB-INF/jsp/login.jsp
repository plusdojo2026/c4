<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="UTF-8">
		<title>サカグラ｜ログイン</title>
		<link rel="stylesheet" href="/webapp/css/login.css">
	</head>

	<body>

		<header>

		</header>
		<div>
			<h1>ログイン</h1>
			<form action="/webapp/LoginServlet" id="form" method="POST">

				<p id="msg"></p>

				<div>
					社員番号<br>
					<input type="text" name="employeeNumber">
				</div>

				<div>
					パスワード<br>
					<input type="password" name="password">
				</div>

				<div>
					<a href="#">パスワードを忘れた方はこちら</a>
				</div>

				<div>
					<button type="button" name="loginBtn" value="ログイン">ログイン</button>
				</div>

			</form>
		</div>
		<footer>

		</footer>
	</body>

	</html>