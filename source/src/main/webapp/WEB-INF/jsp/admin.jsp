<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <link rel="stylesheet" href="/c4/css/common.css">
  <link rel="stylesheet" href="/c4/css/admin.css">

  <title>サカグラ | 管理者</title>
</head>

<body>
  <!-- ここでheader.jspをinclude -->
  <%@ include file="/WEB-INF/jsp/header.jsp" %>

  <!-- sidebarと機能画面のラッパー用div -->
  <div class="wrapper">
    <!-- ここでsidebar.jspをinclude -->
    <%@ include file="/WEB-INF/jsp/sidebar.jsp" %>
    <main class="account-content">
      <section class="account-card">
        
        <div class="button-wrapper">
          <button id="add-button" type="button">
            <img class="active" src="/c4/img/add_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.svg" alt="従業員新規追加">
          </button>
		  <button id="delete-button" class="button">
			<img class="" src="/c4/img/delete_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.svg" alt="削除">
		  </button>
        </div>
        <table>
          <thead>
            <tr>
              <th>社員番号</th>
              <th>名前</th>
              <th>生年月日</th>
              <th>権限（管理者/従業員）</th>
            </tr>
          </thead>
          <tbody id="account-table-body">
			<c:if test="${empty accountList}">
			  <tr>
				<td colspan="5" style="text-align: center;">アカウントデータがありません。</td>
			  </tr>
			</c:if>

            <c:forEach var="a" items="${accountList}">
              <tr
                class="account-row"
                data-id="${a.id}"
                data-name="${a.name}"
                data-birthday="${a.birthday}"
                data-permissiond-id="${a.permissionsId == 1 ? '管理者' : '従業員'}">
                <td><c:out value="${a.id}"></c:out></td>
                <td><c:out value="${a.name}"></c:out></td>
                <td><c:out value="${a.birthday}"></c:out></td>
                <td>
				  <c:choose>
					<c:when test="${a.permissionsId == 1}">管理者</c:when>
					<c:otherwise>従業員</c:otherwise>
				  </c:choose>
				</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </section>
    </main>
    
<!-- 削除ダイアログ -->
    <dialog class="account-delete-dialog">
      <form id="account-delete-form" class="account-form" action="${pageContext.request.contextPath}/account/add" method="post">
      <div class="">
          <p>〇件選択されています。<br>選択した情報を削除しますか？</p>
          <div class="dialog-btn-wrapper">
            <button type="button" class="cancel-btn btn">
              キャンセル
            </button>
            <button type="submit" class="btn">
              追加する
            </button>
          </div>
      </div>
      </form>
    </dialog>

<!-- 従業員新規追加 -->
    <dialog class="account-add-dialog">
      <form id="account-add-form" class="account-form" action="${pageContext.request.contextPath}/account/add" method="post">  
        <div class="">
          <fieldset>
            <legend>名前</legend>
            <input type="text" id="add-name" name="name" placeholder="氏名を入力してください。" required>
          </fieldset>
 
            <legend>生年月日</legend>
           <div id="birthday" class="birthday">
			<select id="year" name="year">
			<option value="1926">1926</option>
			<option value="1927">1927</option>
			<option value="1928">1928</option>
			<option value="1929">1929</option>
			<option value="1930">1930</option>
			<option value="1931">1931</option>
			<option value="1932">1932</option>
			<option value="1933">1933</option>
			<option value="1934">1934</option>
			<option value="1935">1935</option>
			<option value="1936">1936</option>
			<option value="1937">1937</option>
			<option value="1938">1938</option>
			<option value="1939">1939</option>
			<option value="1940">1940</option>
			<option value="1941">1941</option>
			<option value="1942">1942</option>
			<option value="1943">1943</option>
			<option value="1944">1944</option>
			<option value="1945">1945</option>
			<option value="1946">1946</option>
			<option value="1947">1947</option>
			<option value="1948">1948</option>
			<option value="1949">1949</option>
			<option value="1950">1950</option>
			<option value="1951">1951</option>
			<option value="1952">1952</option>
			<option value="1953">1953</option>
			<option value="1954">1954</option>
			<option value="1955">1955</option>
			<option value="1956">1956</option>
			<option value="1957">1957</option>
			<option value="1958">1958</option>
			<option value="1959">1959</option>
			<option value="1960">1960</option>
			<option value="1961">1961</option>
			<option value="1962">1962</option>
			<option value="1963">1963</option>
			<option value="1964">1964</option>
			<option value="1965">1965</option>
			<option value="1966">1966</option>
			<option value="1967">1967</option>
			<option value="1968">1968</option>
			<option value="1969">1969</option>
			<option value="1970">1970</option>
			<option value="1971">1971</option>
			<option value="1972">1972</option>
			<option value="1973">1973</option>
			<option value="1974">1974</option>
			<option value="1975">1975</option>
			<option value="1976">1976</option>
			<option value="1977">1977</option>
			<option value="1978">1978</option>
			<option value="1979">1979</option>
			<option value="1980" selected>1980</option>
			<option value="1981">1981</option>
			<option value="1982">1982</option>
			<option value="1983">1983</option>
			<option value="1984">1984</option>
			<option value="1985">1985</option>
			<option value="1986">1986</option>
			<option value="1987">1987</option>
			<option value="1988">1988</option>
			<option value="1989">1989</option>
			<option value="1990">1990</option>
			<option value="1991">1991</option>
			<option value="1992">1992</option>
			<option value="1993">1993</option>
			<option value="1994">1994</option>
			<option value="1995">1995</option>
			<option value="1996">1996</option>
			<option value="1997">1997</option>
			<option value="1998">1998</option>
			<option value="1999">1999</option>
			<option value="2000">2000</option>
			<option value="2001">2001</option>
			<option value="2002">2002</option>
			<option value="2003">2003</option>
			<option value="2004">2004</option>
			<option value="2005">2005</option>
			<option value="2006">2006</option>
			<option value="2007">2007</option>
			<option value="2008">2008</option>
			<option value="2009">2009</option>
			<option value="2010">2010</option>
			<option value="2011">2011</option>
			<option value="2012">2012</option>
			<option value="2013">2013</option>
			<option value="2014">2014</option>
			<option value="2015">2015</option>
			<option value="2016">2016</option>
			<option value="2017">2017</option>
			<option value="2018">2018</option>
			<option value="2019">2019</option>
			<option value="2020">2020</option>
			<option value="2021">2021</option>
			<option value="2022">2022</option>
			<option value="2023">2023</option>
			<option value="2024">2024</option>
			<option value="2025">2025</option>
			<option value="2026">2026</option>
			
			</select>
			
			<select id="month" name="month">
			<option value="1" selected>1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
			<option value="6">6</option>
			<option value="7">7</option>
			<option value="8">8</option>
			<option value="9">9</option>
			<option value="10">10</option>
			<option value="11">11</option>
			<option value="12">12</option>
			</select>
			
			<select id="day" name="day">
			<option value="1" selected>1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
			<option value="6">6</option>
			<option value="7">7</option>
			<option value="8">8</option>
			<option value="9">9</option>
			<option value="10">10</option>
			<option value="11">11</option>
			<option value="12">12</option>
			<option value="13">13</option>
			<option value="14">14</option>
			<option value="15">15</option>
			<option value="16">16</option>
			<option value="17">17</option>
			<option value="18">18</option>
			<option value="19">19</option>
			<option value="20">20</option>
			<option value="21">21</option>
			<option value="22">22</option>
			<option value="23">23</option>
			<option value="24">24</option>
			<option value="25">25</option>
			<option value="26">26</option>
			<option value="27">27</option>
			<option value="28">28</option>
			<option value="29">29</option>
			<option value="30">30</option>
			<option value="31">31</option>
			</select>
			</div>

          <div class="">
              <p>権限</p>
			  <select id="add-permissions" name="permissionsId">
              <option value="1" required>管理者</option>
			  <option value="2" required>従業員</option>
			  </select>
          </div>

		   <fieldset>
            <legend>初期パスワード</legend>
            <input id="add-default-pw" class="default-password" type="text" name="default-password" required>
          </fieldset>
        </div>
        <div class="dialog-btn-wrapper">
          <button type="button" id="account-add-dialog-cancel-btn" class="cancel-btn btn">
            キャンセル
          </button>
          <button type="button" id="open-next-account-add-dialog" class="btn">
            入力内容を確認する
          </button>
        </div>
      </form>
    </dialog>
    
	<!-- 入力確認ダイアログ -->
    <dialog id="account-add-check-dialog" class="account-check-dialog">
		<h3>入力内容の確認</h3>
        <div class="check-add-content">
		  <p><strong>名前：</strong><span id="check-add-name"></span></p>
		  <p><strong>生年月日：</strong><span id="check-add-birthday"></span></p>
		  <p><strong>権限：</strong><span id="check-add-permissions"></span></p>
		  <p><strong>初期パスワード：</strong><span id="check-add-default-password"></span></p>
		</div>
		  <p>この内容で追加しますか？</p>
		    <div class="dialog-btn-wrapper">
        	<!-- キャンセルボタン -->
            <button type="button" class="btn cancel-btn">キャンセル</button>
            <!-- 実際の追加実行ボタン(DBへ送信) -->
            <button type="button" id="account-add-confirm-btn" class="btn add-btn">追加</button>
			</div>
        </div>
    </dialog>
    
    
    <dialog class="account-edit-dialog">
      <p>名前: <span class="dialog-name"></span></p>
      <p>権限: <span class="dialog-permissions"></span></p>
      <form id="account-edit-form" class="account-form" action="${pageContext.request.contextPath}/admin/edit" method="post">
        <input class="id" type="hidden" name="id" value="">
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
  
  <script src="/c4/js/admin.js"></script>
</body>

</html>