'use strict';


document.getElementById('login-form').onsubmit = function(event) {
	
		// 入力欄の要素を取得
    let employeeNumberInput = document.getElementById('employeeNumber');
		let newPasswordInput = document.getElementById('newPassword');
    let checkPasswordInput = document.getElementById('checkPassword');
    let msgElement = document.getElementById('msg');

		// エラー表示を初期化
    employeeNumberInput.style.backgroundColor = '';
		newPasswordInput.style.backgroundColor = '';
    checkPasswordInput.style.backgroundColor = '';
    msgElement.style.fontWeight = 'bold';
    msgElement.style.color = 'red';

		// 入力値を取得
    let employeeNumber = employeeNumberInput.value;
    let newPassword = newPasswordInput.value;
    let checkPassword = checkPasswordInput.value;

		console.log({
  		employeeNumber,
  		newPassword,
  		checkPassword
		});

		console.log(
  		employeeNumber === '',
  		newPassword === '',
  		checkPassword === ''
		);


		// 全項目が未入力の場合
    if(employeeNumber === '' && newPassword === '' && checkPassword ==='') {
        msgElement.textContent = '項目を入力してください。'

				// 未入力欄を薄い赤で塗りつぶす
        employeeNumberInput.style.backgroundColor = '#ffeeee';
				newPasswordInput.style.backgroundColor = '#ffeeee';
				checkPasswordInput.style.backgroundColor = '#ffeeee';

				// フォーム送信を中止する
				event.preventDefault();
		
		// 社員番号が未入力の場合
    } else if (employeeNumber === '') {
			msgElement.textContent = '社員番号を入力してください。';
			employeeNumberInput.style.backgroundColor = '#ffeeee';
			event.preventDefault();

		// 新規パスワードが未入力の場合
		} else if (newPassword === '') {
			msgElement.textContent = '新規パスワードを入力してください。';
			newPasswordInput.style.backgroundColor = '#ffeeee';
			event.preventDefault();

		// 確認用パスワードが未入力の場合
		}else if (checkPassword === '') {
			msgElement.textContent = '確認用パスワードを入力してください。';
			checkPasswordInput.style.backgroundColor = '#ffeeee';
			event.preventDefault();

		// 新規パスワードと確認用パスワードが不一致の場合
		}else if (newPassword !== checkPassword) {
			msgElement.textContent = 'パスワードが一致しません。';
			newPasswordInput.style.backgroundColor = '#ffeeee';
			checkPasswordInput.style.backgroundColor = '#ffeeee';
			event.preventDefault();
		}
};

//パスワードの表示・非表示
//新規パスワード
const toggleNewPassword = document.getElementById('toggleNewPassword');
const newPassword = document.getElementById('newPassword');

toggleNewPassword.addEventListener('click', function() {
  //現在のtype属性を取得
  const type = newPassword.getAttribute('type') === 'password' ? 'text' : 'password';
  newPassword.setAttribute('type', type);

  //iタグのアイコンを切り替え
  this.classList.toggle('fa-eye');
  this.classList.toggle('fa-eye-slash');

});

//確認用パスワード
const toggleCheckPassword = document.getElementById('toggleCheckPassword');
const checkPassword = document.getElementById('checkPassword');

toggleCheckPassword.addEventListener('click', function() {

	const type = checkPassword.getAttribute('type') ==='password' ? 'text' : 'password';
	checkPassword.setAttribute('type', type);

	this.classList.toggle('fa-eye');
	this.classList.toggle('fa-eye-slash');

});