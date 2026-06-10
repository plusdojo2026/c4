document.addEventListener('DOMContentLoaded', () => {
  const notificationBtn = document.getElementById('notificationBtn');
  const notificationModal = document.getElementById('notificationModal');
  const closeModalBtn = document.getElementById('closeModalBtn');

  // ベルマークをクリックしたときの処理
  notificationBtn.addEventListener('click', (e) => {
    e.stopPropagation();
    notificationModal.classList.toggle('is-active');
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