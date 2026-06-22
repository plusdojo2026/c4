// 1.追加ダイアログの処理
const addButton = document.getElementById('add-button');//モーダル開くボタン
const accountAddDialog = document.querySelector('.account-add-dialog');// 追加用ダイアログ
const addDialogCancelBtn = document.getElementById('account-add-dialog-cancel-btn');//キャンセルボタン
const addDialogAddBtn = document.getElementById('account-add-dialog-add-btn');//追加ボタン
const accountAddCheckDialog = document.getElementById('account-add-check-dialog');//確認用ダイアログ

//モーダルを開く
addButton.addEventListener('click', function(){
  accountAddDialog.showModal();
  console.log("openmodel");
  requestAnimationFrame(() => {
    accountAddDialog.classList.add("show");
  });
});

//モーダルを閉じる
addDialogCancelBtn.addEventListener('click', function(){
  accountAddDialog.classList.remove('show');
  setTimeout(function(){
    accountAddDialog.close();
  }, 250);
});

//追加ボタンが押されたときの処理
addDialogAddBtn.addEventListener('click', function(){
  //確認ダイアログを開く
  accountAddCheckDialog.showModal();
  requestAnimationFrame(() => {
    accountAddCheckDialog.classList.add("show");
  });
  //追加モーダルを閉じる
  accountAddDialog.classList.remove('show');
  setTimeout(function(){
    accountAddDialog.close();
  }, 250);
});

// 追加確認モーダルを閉じる
accountAddCheckDialog.querySelector('.cancel-btn').addEventListener('click', function(){
  accountAddCheckDialog.classList.remove('show');
  setTimeout(function(){
    accountAddCheckDialog.close();
  }, 250);
});

// 追加処理
accountAddCheckDialog.querySelector('.add-btn').addEventListener('click', function(){
  if () {
    
  }
  accountAddCheckDialog.classList.remove('show');
  setTimeout(function(){
    accountAddCheckDialog.close();
  }, 250);
});




// 編集ボタンが押されたときの処理
const editButton = document.getElementById('edit-button');
const accountEditDialog = document.querySelector('.account-edit-dialog');

editButton.addEventListener('click', function(){
  accountEditDialog.showModal();
  console.log("editmodel");
});

// 入力内容の確認モーダル
let name;
let birthday;
let defaultPassword;
let permissionsId;

const accountAddForm = document.getElementById('account-add-form');
const formData = new FormData(accountAddForm);

console.log(formData.get('name'));

