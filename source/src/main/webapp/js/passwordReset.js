'use strict';

document.getElementById('login-form').onsubmit = function(event) {

    let employeeNumberInput = document.getElementById('employeeNumber');
		let birthdayInput = document.getElementById('birthday');
		let newPasswordInput = document.getElementById('newPassword');
    let checkPasswordInput = document.getElementById('checkPassword');
    let msgElement = document.getElementById('msg');

    employeeNumberInput.style.backgroundColor = '';
		birthdayInput.style.backgroundColor = '';
		newPasswordInput.style.backgroundColor = '';
    checkPasswordInput.style.backgroundColor = '';
    msgElement.style.fontWeight = 'bold';
    msgElement.style.color = 'red';

    let employeeNumber = employeeNumberInput.value;
		let birthday = birthdayInput.value;
    let newPassword = newPasswordINnput.value;
    let checkPassword = checkPasswordInput.value;


    if(employeeNumber === '' && checkPassword ==='') {
        msgElement.textContent = '項目を入力してください'
        employeeNumberInput.style.backgroundColor = '#ffeeee';
				birthdayInput.style.backgroundColor = '#ffeeee';
				newPasswordInput.style.backgroundColor = '#ffeeee';
				checkPasswordInput.style.backgroundColor = '#ffeeee';
				event.preventDefault();
    } else if (employeeNumber === '') {
			msgElement.textContent = '社員番号を入力してください。';
			employeeNumberInput.style.backgroundColor = '#ffeeee';
			event.preventDefault();
		} else if (birthday === '') {
			msgElement.textContent = '生年月日を入力してください。';
			birthdayInput.style.backgroundColor = '#ffeeee';
			event.preventDefault();
		} else if (newpassword === '') {
			msgElement.textContent = '新規パスワードを入力してください。';
			newpasswordInput.style.backgroundColor = '#ffeeee';
			event.preventDefault();
		}else if (checkPassword === '') {
			msgElement.textContent = '確認用パスワードを入力してください。';
			checkPasswordInput.style.backgroundColor = '#ffeeee';
			event.preventDefault();
		}
};