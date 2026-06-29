// 1.追加ダイアログの処理
const addButton = document.getElementById('add-button');//モーダル開くボタン
const accountAddForm = document.getElementById('account-add-form');//新規追加のフォーム
const accountAddDialog = document.querySelector('.account-add-dialog');// 新規追加入力用ダイアログ
const addDialogCancelBtn = document.getElementById('account-add-dialog-cancel-btn');//入力ダイアログのキャンセルボタン
const openCheckDialogBtn = document.getElementById('open-next-account-add-dialog');//「入力内容を確認する」ボタン

const accountAddCheckDialog = document.getElementById('account-add-check-dialog');//入力確認ダイアログ
const checkDialogCancelBtn = accountAddCheckDialog.querySelector('.cancel-btn');//確認ダイアログのキャンセルボタン
const addConfirmBtn = document.getElementById('account-add-confirm-btn');//確認ダイアログの追加ボタン

//+ボタンが押されたときの処理（モーダルを開く）
addButton.addEventListener('click', function(){
  // フォームの入力をリセットする
  accountAddForm.reset();
  document.getElementById('add-name').style.backgroundColor = '';
  document.getElementById('add-default-pw').style.backgroundColor = '';
  const errorMsg = document.getElementById('add-form-error-msg');
  errorMsg.style.display = 'none';

  // モーダルを開く
  accountAddDialog.showModal();
  console.log("openmodel");
  requestAnimationFrame(function() {
    accountAddDialog.classList.add("show");
  });
});

//閉じるボタンが押されたときの処理（モーダルを閉じる）
addDialogCancelBtn.addEventListener('click', function(){
  accountAddDialog.classList.remove('show');
  setTimeout(function(){
    accountAddDialog.close();
  }, 250);
});

//モーダル内の「入力内容を確認する」ボタンが押されたときの処理
openCheckDialogBtn.addEventListener('click', function(){
  const nameInput = document.getElementById('add-name');
  const passwordInput = document.getElementById('add-default-pw');
  const errorMsg = document.getElementById('add-form-error-msg');

  // エラー状態とメッセージの初期化(エラーなし、枠線も通常、メッセージも隠す状態に戻す)
  let hasError = false;
  nameInput.style.backgroundColor = '';
  passwordInput.style.backgroundColor = '';
  errorMsg.style.display = 'none';
  
  //入力された「文字（値）」を取得
  let newName = nameInput.value;
  let defaultPassword = passwordInput.value;
  
  //メッセージ変更とエラーの塗りつぶしの適用(判定にはtrue/falseの値を使う)
  if (newName === '' && defaultPassword === '') {
    errorMsg.textContent = '名前と初期パスワードを入力してください。';
    //中身を薄い赤で塗りつぶす
    nameInput.style.backgroundColor = '#ffeeee';
    passwordInput.style.backgroundColor = '#ffeeee';
    hasError = true;//エラーの目印ON
  }
  else if (newName === '') {
    errorMsg.textContent = '名前を入力してください。';
    //中身を薄い赤で塗りつぶす
    nameInput.style.backgroundColor = '#ffeeee';
    hasError = true;//エラーの目印ON
  }
  else if (defaultPassword === '') {
    errorMsg.textContent = '初期パスワードを入力してください。';
    passwordInput.style.backgroundColor = '#ffeeee';
    hasError = true;//エラーの目印ON
  }

  // エラーが1つでもあれば、一括表示して処理を中断する
  if (hasError === true) {
    errorMsg.style.display = 'block';// エラーメッセージを表示
    return;
  }


  // 入力フォームから値を取得する
const name = document.getElementById('add-name').value;
const year = document.getElementById('year').value;
const month = document.getElementById('month').value;
const day = document.getElementById('day').value;
const password = document.getElementById('add-default-pw').value;

// 権限セレクトボックスの選択されているテキスト(管理者or従業員)を取得する
const dialogSelect = document.getElementById('add-permissions');
const permissionText = dialogSelect.options[dialogSelect.selectedIndex].text;

// 確認ダイアログの(jspの)spanタグに値を入れる
document.getElementById('check-add-name').textContent = name;
document.getElementById('check-add-birthday').textContent = year + '年' + month + '月' + day + '日';
document.getElementById('check-add-permissions').textContent = permissionText;
document.getElementById('check-add-default-password').textContent = password;

// 確認ダイアログを開く
  accountAddCheckDialog.showModal();
  requestAnimationFrame(function() {
    accountAddCheckDialog.classList.add("show");
  });

  //入力ダイアログを閉じる
  accountAddDialog.classList.remove('show');
  setTimeout(function(){
    accountAddDialog.close();
  }, 250);
});

// 確認モーダルのキャンセルボタンが押されたときの処理(新規追加入力ダイアログに戻る)
checkDialogCancelBtn.addEventListener('click', function(){
  accountAddCheckDialog.classList.remove('show');
  setTimeout(function(){
    accountAddCheckDialog.close();

    //入力したデータを保持したまま、新規追加入力ダイアログを再び開く
    accountAddDialog.showModal();
    requestAnimationFrame(function(){
      accountAddDialog.classList.add('show');
    });
  }, 250);
});

// 確認モーダルの「追加」ボタンが押されたときの処理(DBへ送信)
addConfirmBtn.addEventListener('click', function(){
  accountAddForm.submit();
});



// 2.編集ダイアログの処理
const accountEditDialog = document.querySelector('.account-edit-dialog');       
const accountEditForm = document.getElementById('account-edit-form');         
const editCancelBtn = accountEditDialog.querySelector('.cancel-btn');       

const accountEditCheckDialog = document.getElementById('account-edit-check-dialog');  
const editCloseBtn = document.getElementById('account-edit-close-btn');     

const dialogNameInput = accountEditDialog.querySelector('.dialog-name');// Input要素として取得
const editIdInput = accountEditDialog.querySelector('.account-form .id'); 
const editPermissionsSelect = document.getElementById('edit-permissions');          


// 一覧の行（.account-row）クリック：編集モーダルを開き、既存データをセット
document.querySelectorAll('.account-row').forEach(function(row) {
  row.addEventListener('click', function(e) {
    if (e.target.tagName === 'INPUT' || e.target.closest('button') || e.target.closest('td:first-child')){
      return;
    }

    const id = row.getAttribute('data-id');
    const name = row.getAttribute('data-name');
    const permission = row.getAttribute('data-permissions-id'); // 行に「管理者」や「従業員」という文字が入っていると想定

    if (editIdInput) editIdInput.value = id;
    if (dialogNameInput) dialogNameInput.value = name;

    if (editPermissionsSelect) {
      // 行のデータが日本語、数値どちらでも対応できるように判定
      if (permission === '1' || permission === '管理者') {
        editPermissionsSelect.value = '1';
      } else {
        editPermissionsSelect.value = '2';
      }
    }

    accountEditDialog.showModal();
    requestAnimationFrame(function() {
      accountEditDialog.classList.add('show');
    });
  });
});

// 編集入力モーダル：キャンセルボタン
if (editCancelBtn) {
  editCancelBtn.addEventListener('click', function(){
    accountEditDialog.classList.remove('show');
    setTimeout(function(){
      accountEditDialog.close();
    }, 250);
  });
}

// 編集入力モーダル：「更新」ボタンが押されたとき（確認画面へ移る）
if (accountEditForm) {
  accountEditForm.addEventListener('submit', function(event) {
    event.preventDefault(); // 一度送信を止める

    // Inputから最新の「名前」を取得、セレクトボックスから選択された「権限名」を取得
    const currentName = dialogNameInput.value;
    const selectedPermissionText = editPermissionsSelect.options[editPermissionsSelect.selectedIndex].text;

    // 確認ダイアログの正しいJSP idに値を反映
    const checkEditName = document.getElementById('check-edit-name');
    const checkEditPerm = document.getElementById('check-edit-permissions');
    
    if (checkEditName) checkEditName.textContent = currentName;
    if (checkEditPerm) checkEditPerm.textContent = selectedPermissionText;

    // 編集完了（確認）モーダルを開く
    if (accountEditCheckDialog) {
      accountEditCheckDialog.showModal();
      requestAnimationFrame(function() {
        accountEditCheckDialog.classList.add('show');
      });
    }

    // 入力モーダルを閉じる
    accountEditDialog.classList.remove('show');
    setTimeout(function() {
      accountEditDialog.close();
    }, 250);
  });
}

// 編集完了（確認）モーダル：「閉じる」ボタンが押されたとき（ここで本当にJavaへ送信）
if (editCloseBtn) {
  editCloseBtn.addEventListener('click', function() {
    if (accountEditCheckDialog) {
      accountEditCheckDialog.classList.remove('show');
      setTimeout(function() {
        accountEditCheckDialog.close();
        
        // 💡 サーバー（Java）へフォームを送信
        accountEditForm.submit();
      }, 250);
    }
  });
}
// const accountEditDialog = document.querySelector('.account-edit-dialog');// 編集ダイアログ
// const accountEditForm = document.getElementById('account-edit-form');// 編集フォーム
// const editCancelBtn = accountEditDialog.querySelector('.cancel-btn');// 編集ダイアログのキャンセルボタン

// const accountEditCheckDialog = document.getElementById('account-edit-check-dialog');// 編集完了ダイアログ
// const editCloseBtn = document.getElementById('account-edit-close-btn');// 閉じるボタン

// const dialogNameSpan = accountEditDialog.querySelector('.dialog-name');// 更新した名前
// const editIdInput = accountEditDialog.querySelector('.account-form .id');// hiddenのID
// const editPermissionsSelect = document.getElementById('edit-permissions');// 権限の更新


// document.querySelectorAll('.account-row').forEach(function(row) {
//   row.addEventListener('click', function(e) {
//     // チェックボックスがクリックされたときに編集モーダルを開かない
//     if (e.target.tagName === 'INPUT' || e.target.closest('button') || e.target.closest('td:first-child')){
//       return;
//     }

    
//     // クリックされた行(row)からデータを取得する
//     const id = row.getAttribute('data-id');
//     const name = row.getAttribute('data-name');
//     const permission = row.getAttribute('data-permissions-id');

//     // 編集モーダルの初期値をセットする
//     if (editIdInput) editIdInput.value = id;
//     if (dialogNameSpan) dialogNameSpan.textContent = name;

//     if (editPermissionsSelect) {
//       if (permission === '管理者') {
//         editPermissionsSelect.value = '1';
//       } else {
//         editPermissionsSelect.value = '2';
//       }
//     }

//     // モーダル表示
//     accountEditDialog.showModal();
//     requestAnimationFrame(function() {
//       accountEditDialog.classList.add('show');
//     });
//   });
// });

// // キャンセルボタンを押したときの処理
// if (editCancelBtn) {
//   editCancelBtn.addEventListener('click', function(){
//     accountEditDialog.classList.remove('show');
//     setTimeout(function(){
//       accountEditDialog.close();
//     }, 250);
//   });
// }

// // 更新ボタンが押されたときの処理
// if (accountEditForm) {
//   accountEditForm.addEventListener('submit', function(event) {
//     event.preventDefault();

//     // 現在選択されているデータを取得
//     const name = dialogNameSpan.textContent;
//     const permissionText = editPermissionsSelect.options[editPermissionsSelect.selectedIndex].text;

//     // 編集完了ダイアログのspanタグへ最新の値を反映させる
//     document.getElementById('check-add-name').textContent = name;
//     document.getElementById('check-add-permissions').textContent = permissionText;

//     // 編集完了ダイアログを開く
//     accountEditCheckDialog.showModal();
//     requestAnimationFrame(function() {
//       accountEditCheckDialog.classList.add('show');
//     });

//     // 編集モーダルを閉じる
//     accountEditDialog.classList.remove('show');
//     setTimeout(function() {
//       accountEditDialog.close();
//     }, 250);
//   });
// }

// // 編集ダイアログの閉じるボタンを押したときの処理(ここでDBへ送信)
// if (editCloseBtn) {
//   editCloseBtn.addEventListener('click', function() {
//     if (accountEditCheckDialog) {
//     accountEditCheckDialog.classList.remove('show');
//     setTimeout(function() {
//       accountEditCheckDialog.close();
      
//       // DB（サーブレット）へデータを送るためにフォームを送信
//       accountEditForm.submit();
//     }, 250);
//    }
//   });
// }



// 3.削除ダイアログの処理
const deleteBtn = document.getElementById('delete-button');//ゴミ箱のアイコン
const accountDeleteDialog = document.querySelector('.account-delete-dialog');//削除ダイアログ本体
const deleteDialogCancelBtn = document.getElementById('account-delete-dialog-cancel-btn');//削除ダイアログ内のキャンセルボタン
const deleteForm = document.getElementById('account-delete-form');//削除用フォーム

// ゴミ箱ボタンが押されたときの処理
if (deleteBtn) {
  deleteBtn.addEventListener('click', function() {
    // 現在チェックが入っているチェックボックスをすべて取得
    const accountCheckBoxes = document.querySelectorAll('.account-edit-check:checked');

    // 1つもチェックされてなければ、ダイアログを開かずにアラートを出す
    if (accountCheckBoxes.length === 0) {
      alert('削除するアカウントを1つ以上選択してください。');
      return;
    }

    // 選択されている件数を表示する
    document.getElementById('account-delete-count').textContent = accountCheckBoxes.length;

    // チェックされたIDを配列に集める
    const idArray = [];
    accountCheckBoxes.forEach(function(checkbox) {
      //pushメソッド 配列の一番最後に新しいデータを1つ追加する
      idArray.push(checkbox.value);
    });

    // joinメソッドで「配列の中に入っている複数のデータを、指定した文字で1つにつなぎ合わせて、1つの文字列に変換する」
    document.getElementById('delete-ids-input').value = idArray.join(',');

    // 削除ダイアログを開く
    accountDeleteDialog.showModal();
    requestAnimationFrame(function() {
      accountDeleteDialog.classList.add('show');
      });    
    });
  } 

  // 削除ダイアログのキャンセルボタンが押されたときに処理
  if (deleteDialogCancelBtn) {
    deleteDialogCancelBtn.addEventListener('click', function() {
      accountDeleteDialog.classList.remove('show');
      setTimeout(function(){
        accountDeleteDialog.close();
      }, 250);
    });
  }