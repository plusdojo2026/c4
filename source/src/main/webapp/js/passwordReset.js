'use strict';

document.getElementById('loginform').onsubmit = function(event) {
    event.preventDefault();

    let employeeNumberInput = document.getElementById('employeeNumber');
    let newPasswordInput = document.getElementById('newPassword');
    let checkPasswordInput = document.getElementById('checkPassword');
		let birthdayInput = document.getElementById('birthday');
		let yearInput = document.getElementById('year');
		let monthInput = document.getElementById('month');
		let dayInput = document.getElementById('day');
    let msgElement = document.getElementById('msg');

    employeeNumberInput.style.backgroundColor = '';
    newPasswordInput.style.backgroundColor = '';
    checkPasswordInput.style.backgroundColor = '';
		birthdayInput.style.backgroundColor = '';
		yearInput.style.backgroundColor = '';
		monthInput.style.backgroundColor = '';
		dayInput.style.backgroundColor = '';
    msgElement.style.frontWeight = 'bold';
    msgElement.style.color = 'red';

    let employeeNumber = employeeNumberInput.value;
    let newpassword = newpasswordINnput.value;
    let checkPassword = checkPasswordInput.value;
		let birthday = birthdayInput.value;
		let year = yearInput.value;
		let month = monthInput.value;
		let day = dayInput.value;



    if(employeeNumber === '' && checkPassword ==='') {
        msgElement.textContent = '項目を入力してください'
        employeeNumberInput.style.backgroundColor = '#ffeeee';
				newPasswordInput.style.backgroundColor = '#ffeeee';
				checkPasswordInput.style.backgroundColor = '#ffeeee';
				birthdayInput.style.backgroundColor = '#ffeeee';
				yearInput.style.backgroundColor = '#ffeeee';
				monthInput.style.backgroundColor = '#ffeeee';
				dayInout.style.backgroundColor = '#ffeeee';
				event.preventDefault();
    } else if (employeeNumber === '') {
			msgElement.textContent = '社員番号を入力してください';
			employeeNumberInput.style.backgroundColor = '#ffeeee';
			event.preventDefault();
		} else if (birthday === '') {
			msgElement.textContent = '生年月日を入力してください';
			birthdayInput.style.backgroundColor = '#ffeeee';
			event.preventDefault();
		} else if (year === '') {
			msgElement.textContent = '西暦を入力してください';
			yearInput.style.backgroundColor = '#ffeeee';
			event.prevemtDefault();
		} else if (month === '') {
			msgElement.textContent = '誕生月を入力してください';
			monthInput.style.backgroundColor = '#ffeeee';
			event.preventDefault();
		} else if (day === '') {
			msgElement.textContent = '誕生日を入力してください';
			dayInput.style.backgroundColor = '#ffeeee';
			event.preventDefault();
		} else if (newpassword === '') {
			msgElement.textContent = '新規パスワードを入力してください';
			newpasswordNumberInput.style.backgroundColor = '#ffeeee';
			event.preventDefault();
		}else if (checkPassword === '') {
			msgElement.textContent = '確認用パスワードを入力してください';
			checkPasswordNumberInput.style.backgroundColor = '#ffeeee';
			event.preventDefault();
		}
};