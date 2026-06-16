<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


    <link rel="stylesheet" href="/c4/css/common.css">
    <link rel="stylesheet" href="/c4/css/sidebar.css">

    <aside class="global-sidebar">
      <div class="sidebar-top">

        <a href="/c4/product" class="sidebar-item" title="商品管理">
          <img src="/c4/img/package_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.svg" class="sidebar-icon" alt="商品管理">
          <span class="sidebar-label">商品管理</span>
        </a>

        <a href="/c4/stock" class="sidebar-item" title="在庫管理">
          <img src="/c4/img/package_2_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.svg" class="sidebar-icon" alt="在庫管理">
          <span class="sidebar-label">在庫管理</span>
        </a>
      </div>

      <div class="sidebar-bottom">
        <div class="sidebar-item" id="account-button" title="アカウント">
          <img src="/c4/img/account_circle_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.svg" class="sidebar-icon" alt="アカウント">
          <span class="sidebar-label">アカウント</span>
        </div>

        <div id="account-modal" class="account-modal hidden">
          <div class="modal-user-info">
            <div class="modal-user-icon">
              <img src="/c4/img/attribution_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.svg" alt="アカウント">
            </div>
            <div class="modal-text-group">
              <span class="modal-emp-num">社員番号：
                <c:out value="${sessionScope.id}" />
              </span>
              <span class="modal-name">氏名：
                <c:out value="${sessionScope.name}" />
              </span>
            </div>
          </div>
          <button class="btn modal-logout-btn">ログアウト</button>
        </div>

      </div>
    </aside>

    <script src="/c4/js/sidebar.js"></script>