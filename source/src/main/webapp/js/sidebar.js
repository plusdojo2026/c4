// サイドバーのナビゲーション制御
const setupSidebarNavigation = () => {
  const currentPath = window.location.pathname;
  const sidebarLinks = document.querySelectorAll('a.sidebar-item');

  sidebarLinks.forEach(link => {
    if (currentPath.includes(link.getAttribute('href'))) {
      link.classList.add('active');
    }
  });
};

// アカウントモーダルとログアウトの制御
const setupAccountModal = () => {
  const accountBtn = document.getElementById('account-button');
  const accountModal = document.getElementById('account-modal');
  const logoutBtn = document.querySelector('.modal-logout-btn');

  // モーダルの開閉
  if (accountBtn && accountModal) {
    accountBtn.addEventListener('click', (event) => {
      accountModal.classList.toggle('hidden');
      event.stopPropagation();
    });

    // 画面外クリックで閉じる処理
    document.addEventListener('click', (event) => {
      if (!accountModal.classList.contains('hidden') &&
        !accountModal.contains(event.target) &&
        !accountBtn.contains(event.target)) {
        accountModal.classList.add('hidden');
      }
    });
  }

  // ログアウト処理
  if (logoutBtn) {
    logoutBtn.addEventListener('click', () => {
      window.location.href = '/c4/logout';
    });
  }
};

// パスワード変更機能の制御（バリデーション・通信含む）
const setupPasswordChange = () => {
  // 要素の取得
  const openPwdModalBtn = document.getElementById('open-pwd-modal-btn');
  const closePwdModalBtn = document.getElementById('close-pwd-modal-btn');
  const pwdModal = document.getElementById('password-modal');
  const pwdModalOverlay = document.getElementById('pwd-modal-overlay');
  const accountModal = document.getElementById('account-modal');

  const pwdChangeForm = document.getElementById('pwd-change-form');
  const newPwdInput = document.getElementById('new-password');
  const confirmPwdInput = document.getElementById('confirm-password');
  const pwdMatchMsg = document.getElementById('pwd-match-msg');
  const submitPwdBtn = document.getElementById('submit-pwd-btn');

  // 該当する画面がなければここで処理を終了する
  if (!openPwdModalBtn || !pwdModal) return;

  // サーバーメッセージを削除する
  const removeServerMessages = () => {
    document.querySelector('.server-error-msg')?.remove();
    document.querySelector('.server-success-msg')?.remove();
  };

  // フォームのリセット
  const resetPwdForm = () => {
    pwdChangeForm?.reset();
    if (pwdMatchMsg) {
      pwdMatchMsg.textContent = '';
      pwdMatchMsg.className = 'pwd-match-msg';
    }
    if (submitPwdBtn) submitPwdBtn.disabled = true;
    removeServerMessages();
  };

  // モーダルを閉じる処理
  const closePwdModal = () => {
    pwdModal.classList.add('hidden');
    pwdModalOverlay?.classList.add('hidden');
    resetPwdForm();
  };

  // イベントリスナーの登録
  // モーダルを開く
  openPwdModalBtn.addEventListener('click', (event) => {
    pwdModal.classList.remove('hidden');
    pwdModalOverlay?.classList.remove('hidden');
    accountModal?.classList.add('hidden');
    event.stopPropagation();
  });

  // モーダルを閉じる
  closePwdModalBtn?.addEventListener('click', closePwdModal);
  pwdModalOverlay?.addEventListener('click', closePwdModal);

  // リアルタイムバリデーション
  const validatePasswords = () => {
    const newPwd = newPwdInput?.value || '';
    const confirmPwd = confirmPwdInput?.value || '';

    if (!newPwd || !confirmPwd) {
      if (pwdMatchMsg) pwdMatchMsg.textContent = '';
      if (pwdMatchMsg) pwdMatchMsg.className = 'pwd-match-msg';
      if (submitPwdBtn) submitPwdBtn.disabled = true;
      return;
    }

    if (newPwd === confirmPwd) {
      if (pwdMatchMsg) pwdMatchMsg.textContent = '';
      if (pwdMatchMsg) pwdMatchMsg.className = 'pwd-match-msg';
      if (submitPwdBtn) submitPwdBtn.disabled = false;
    } else {
      if (pwdMatchMsg) pwdMatchMsg.textContent = 'パスワードが一致しません';
      if (pwdMatchMsg) pwdMatchMsg.className = 'pwd-match-msg error';
      if (submitPwdBtn) submitPwdBtn.disabled = true;
    }
  };

  newPwdInput?.addEventListener('input', validatePasswords);
  confirmPwdInput?.addEventListener('input', validatePasswords);

  // 非同期通信でのフォーム送信
  pwdChangeForm?.addEventListener('submit', async (event) => {
    event.preventDefault();
    if (submitPwdBtn) submitPwdBtn.disabled = true;

    const formData = new FormData(pwdChangeForm);
    const params = new URLSearchParams(formData);

    try {
      const response = await fetch('/c4/changePassword', {
        method: 'POST',
        body: params,
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
      });

      const result = await response.json();
      removeServerMessages();

      const msgElement = document.createElement('div');
      msgElement.textContent = result.message;

      if (result.success) {
        msgElement.className = 'server-success-msg';
        closePwdModal();
        accountModal?.classList.remove('hidden');
        document.querySelector('.modal-user-info')?.insertAdjacentElement('afterend', msgElement);
      } else {
        msgElement.className = 'server-error-msg';
        pwdChangeForm.prepend(msgElement);
        if (submitPwdBtn) submitPwdBtn.disabled = false;
      }

    } catch (error) {
      console.error('通信エラー:', error);
      alert('通信エラーが発生しました。時間を置いて再度お試しください。');
      if (submitPwdBtn) submitPwdBtn.disabled = false;
    }
  });
};

// メイン処理
document.addEventListener('DOMContentLoaded', () => {
  setupSidebarNavigation();
  setupAccountModal();
  setupPasswordChange();
});