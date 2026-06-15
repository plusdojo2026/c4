document.addEventListener('DOMContentLoaded', () => {
  const accountBtn = document.getElementById('account-button');
  const accountModal = document.getElementById('account-modal');

  // アカウントアイコンをクリックしたとき
  accountBtn.addEventListener('click', (event) => {
    accountModal.classList.toggle('hidden'); // hiddenクラスをつけ外し
    event.stopPropagation(); // ドキュメント側へのクリックイベント伝播を防ぐ
  });

  // モーダルの外側をクリックしたときにモーダルを閉じる
  document.addEventListener('click', (event) => {
    // モーダルが表示中、かつクリックされた場所がモーダル自身でもボタンでもない場合
    if (!accountModal.classList.contains('hidden') &&
      !accountModal.contains(event.target) &&
      !accountBtn.contains(event.target)) {
      accountModal.classList.add('hidden');
    }
  });

  // 現在のURLのパスを取得
  const currentPath = window.location.pathname;

  // すべてのサイドバーのリンク処理
  const sidebarLinks = document.querySelectorAll('a.sidebar-item');
  sidebarLinks.forEach(link => {
    // リンクのhref属性の値が、現在のURLに含まれているかチェック
    if (currentPath.includes(link.getAttribute('href'))) {
      link.classList.add('active');
    }
  });
});
