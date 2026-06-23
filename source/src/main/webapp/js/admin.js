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
  //required属性の箇所のチェック(空なら警告ポップアップを出す)
  // 入力エラーがあればポップアップを表示
  // reportValidity()は、エラーがあるとfalseを返す
  if (!accountAddForm.checkVisibility()) {
    accountAddForm.reportValidity();
    return;//エラーがあると処理を中断
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
// 行をタップしたときに編集モーダルへ遷移

const accountEditDialog = document.querySelector('.account-edit-dialog');

const dialogName = accountEditDialog.querySelector('.dialog-name');
const dialogPermissions = accountEditDialog.querySelector('.dialog-permissions');
const idInput = accountEditDialog.querySelector('.account-form .id');
const cancelButton = accountEditDialog.querySelector('cancel-button');

let currentId;
let currentName;
let currentBirthday;
let currentPermission;

document.querySelectorAll('.account-row').forEach(function(row) {
  row.addEventListener('click', function(e) {
    if (e.target.tagName === 'INPUT' || e.target.closest('button')){
      return;
    }

    currentId = id;
    currentName = name;
    currentBirthday = birthday;
    currentPermission = permissionsId;

    if (idInput) {
      idInput.value = id;
    }

    if (dialogName) {
      dialogName.textContent = name;
    }

    if (dialogPermissions) {
      dialogPermissions.textContent = permissionsId;
    }

    // モーダル表示
    accountEditDialog.showModal();

    requestAnimationFrame(function() {
      accountEditDialog.classList.add('show');
    });

  });
});

// キャンセルボタンを押したときの処理
accountEditDialog.querySelector('.cancel-btn').addEventListener('click', function(){
  accountEditDialog.classList.remove('show');
  setTimeout(function(){
    accountEditDialog.close();
  }, 250);
});


// ダイアログ処理
// const stockAddDialog = document.querySelector('.stock-add-dialog');
// const stockEditDialog = document.querySelector('.stock-edit-dialog');
// const newCheckDialog = document.querySelector(".new-check-dialog");

// const addButton = document.getElementById('add-button');

// const dialogJan = stockEditDialog.querySelector('.dialog-jan');
// const dialogProductName = stockEditDialog.querySelector('.dialog-product-name');
// const dialogStock = stockEditDialog.querySelector('.dialog-stock');
// const addReceivedAt = stockAddDialog.querySelector('.receivedAt');

// let currentId;
// let currentJancode;
// let currentStock;
// let changeQuantity;

// document.querySelectorAll('.stock-row').forEach((row) => {
//   row.addEventListener('click', () => {
//     const {
//       id,
//       jan,
//       productName,
//       stockQuantity,
//     } = row.dataset;

//     currentId = id;
//     currentJancode = jan;
//     currentStock = Number(stockQuantity);
//     changeQuantity = 0;

//     dialogJan.textContent = jan;
//     dialogProductName.textContent = productName;
//     dialogStock.textContent = stockQuantity;

//     updateDisplay();

//     stockEditDialog.showModal();

//     requestAnimationFrame(() => {
//       stockEditDialog.classList.add("show");
//     });
//   });
// });

// const date = new Date();

// addButton.addEventListener('click', () => {
//   changeQuantity = 0;
//   const year = String(date.getFullYear());
//   const month = String(date.getMonth() + 1).padStart(2, "0");
//   const day = String(date.getDate()).padStart(2, "0");

//   addReceivedAt.value = `${year}-${month}-${day}`;

//   updateDisplay();
//   stockAddDialog.showModal();
//   requestAnimationFrame(() => {
//     stockAddDialog.classList.add("show");
//   });
// });

// document.querySelectorAll('.cancel-btn').forEach((btn) => {
//   btn.addEventListener('click', () => {
//     stockAddDialog.classList.remove("show");
//     stockEditDialog.classList.remove("show");
//     newCheckDialog?.classList.remove("show");
//     setTimeout(() => {
//       btn.closest('dialog')?.close();
//     }, 250);
//   });
// });

// const incrementBtns = document.querySelectorAll(".increment-btn");
// const decrementBtns = document.querySelectorAll(".decrement-btn");
// const id = document.querySelector(".id");
// const janCode = document.querySelector(".jancode");
// const changeQuantityEl = document.querySelector(".change-quantity");
// const addQuantityEl = document.querySelector(".add-quantity");
// const newQuantityEl = document.querySelector(".new-quantity");

// function updateDisplay() {
//   id.value = currentId;
//   janCode.value = currentJancode;
//   changeQuantityEl.value = changeQuantity;

//   if(stockAddDialog.open) {
//     if(Number(changeQuantity) < 1) {
//       changeQuantity = 1;
//       addQuantityEl.value = 1;
//     } else {
//       addQuantityEl.value = changeQuantity;
//     }
//   }

//   if(stockEditDialog.open) {
//     newQuantityEl.value = currentStock + changeQuantity;
//   }
// }

// incrementBtns.forEach((btn) => {
//   btn.addEventListener('click', () => {
//     changeQuantity++;
//     updateDisplay();
//   });
// });

// decrementBtns.forEach((btn) => {
//   btn.addEventListener('click', () => {
//     changeQuantity--;
//     updateDisplay();
//   });
// });

// changeQuantityEl.addEventListener('input', () => {
//   changeQuantity = changeQuantityEl.value;
//   newQuantityEl.value = Number(currentStock) + Number(changeQuantity);
// });

// if(newCheckDialog != null) {
//   newCheckDialog.showModal();

//   requestAnimationFrame(() => {
//     newCheckDialog.classList.add("show");
//   });
// }