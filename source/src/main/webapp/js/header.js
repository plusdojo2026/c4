document.addEventListener('DOMContentLoaded', () => {
  const notificationBtn = document.getElementById('notificationBtn');
  const notificationModal = document.getElementById('notificationModal');
  const closeModalBtn = document.getElementById('closeModalBtn');

  // ページ初期読み込み時に未読アイテムがあればバッジを表示
  const initialUnreadItems = document.querySelectorAll('.notification-item.unread');
  if (initialUnreadItems.length > 0) {
    const badge = document.getElementById('badge');
    if (badge) {
      badge.classList.add('notification-badge');
    }
  }

  // ベルマークをクリックしたときの処理
  notificationBtn.addEventListener('click', (e) => {
    e.stopPropagation();

    // モーダルの開閉を切り替え
    notificationModal.classList.toggle('is-active');

    // 画面上の未読アイテムを取得
    const unreadItems = document.querySelectorAll('.notification-item.unread');
    if (unreadItems.length > 0) {
      // 未読アイテムが存在する場合、ベルマークの赤いバッジを追加
      const badge = document.getElementById('badge');
      if (badge) {
        badge.classList.add('notification-badge');
      }
    }

    // モーダルが「開いた」状態の場合のみ既読処理を実行
    if (notificationModal.classList.contains('is-active')) {

      // 画面上の未読アイテムを取得
      const unreadItems = document.querySelectorAll('.notification-item.unread');

      // 未読アイテムが存在する場合のみサーバーへ通信
      if (unreadItems.length > 0) {
        fetch('/c4/notification/readAll', {
          method: 'POST'
        })
          .then((response) => {
            if (response.ok) {
              // すべての未読アイテムから青背景を削除
              unreadItems.forEach((item) => {
                item.classList.remove('unread');
              });

              // ベルマークの赤いバッジを消去
              const badge = document.querySelector('.notification-badge');
              if (badge) {
                badge.remove();
              }
            }
          })
          .catch((error) => {
            console.error('一括既読処理で通信エラーが発生しました:', error);
          });
      }
    }
  });

  // モーダル内の「×」ボタンをクリックしたときの処理
  closeModalBtn.addEventListener('click', () => {
    notificationModal.classList.remove('is-active');
  });

  // モーダルの外側をクリックしたときに閉じる処理
  document.addEventListener('click', (e) => {
    // モーダルが開いていて、かつクリックされた場所がモーダル内部・ベルマーク内部「以外」の場合
    if (notificationModal.classList.contains('is-active')) {
      if (!notificationModal.contains(e.target) && !notificationBtn.contains(e.target)) {
        notificationModal.classList.remove('is-active');
      }
    }
  });

  notificationModal.addEventListener('click', (e) => {
    e.stopPropagation();
  });
});