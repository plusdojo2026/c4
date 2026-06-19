document.addEventListener('DOMContentLoaded', () => {
  const notificationBtn = document.getElementById('notificationBtn');
  const notificationModal = document.getElementById('notificationModal');
  const closeModalBtn = document.getElementById('closeModalBtn');
  const badge = document.getElementById('badge');

  // ページ初期読み込み時に未読アイテムがあればバッジを表示
  const initialUnreadItems = document.querySelectorAll('.notification-item.unread');
  if (initialUnreadItems.length > 0) {
    badge?.classList.add('notification-badge');
  }

  // ベルマークをクリックしたときの処理
  notificationBtn.addEventListener('click', async (e) => {
    e.stopPropagation();

    // モーダルの開閉を切り替え
    notificationModal.classList.toggle('is-active');

    // 画面上の未読アイテムを取得
    const unreadItems = document.querySelectorAll('.notification-item.unread');
    // 未読アイテムが存在する場合、ベルマークの赤いバッジを追加
    if (unreadItems.length > 0) {
      badge?.classList.add('notification-badge');
    }

    // モーダルが開いており、かつ未読がある場合のみ通信
    if (notificationModal?.classList.contains('is-active') && unreadItems.length > 0) {
      try {
        const response = await fetch('/c4/notification/readAll', { method: 'POST' });
        if (response.ok) {
          // すべての未読アイテムから青背景を削除
          unreadItems.forEach(item => item.classList.remove('unread'));
          badge?.classList.remove('notification-badge');
        } else {
          console.error('サーバー側でエラーが発生しました:', response.statusText);
        }
      } catch (error) {
        console.error('一括既読処理で通信エラーが発生しました:', error);
      }
    }
  });

  // モーダル内の「×」ボタンをクリックしたときの処理
  closeModalBtn.addEventListener('click', () => {
    notificationModal.classList.remove('is-active');
  });

  // モーダルの外側をクリックしたときに閉じる処理
  document.addEventListener('click', (e) => {
    const isClickInsideModal = notificationModal?.contains(e.target); // モーダル内がクリックされたかどうかを判定
    const isClickOnBtn = notificationBtn?.contains(e.target); // ベルマークがクリックされたかどうかを判定
    // モーダルが開いている場合、モーダル外のクリックで閉じる
    if (notificationModal.classList.contains('is-active')) {
      if (!isClickInsideModal && !isClickOnBtn) {
        notificationModal.classList.remove('is-active');
      }
    }
  });

  // モーダル内のクリックイベントが伝播しないようにする
  notificationModal.addEventListener('click', (e) => {
    e.stopPropagation();
  });
});