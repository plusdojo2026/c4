const form = document.getElementById('form');

form.onsubmit = function (event) {
  console.log('test');
  let employeeNumber = document.getElementById('employeeNumber').value;
  let password = document.getElementById('password').value;
  let msgElement = document.getElementById('msg');

  employeeNumber.style.border = '';
  password.style.border = '';

  let employeeNumber = employeeNumber.value;
  let password = password.value;

  if (employeeNumber === '' && password === '') {
    msgElement.textContent = '社員番号とパスワードを入力してください。';
    employeeNumber.style.border = '2px solid red';
    password.style.border = '2px solid red';
    event.preventDefault();
  }

  else if (employeeNumber === '') {
    msgElement.textContent = '社員番号を入力してください。';
    employeeNumber.style.border = '2px solid red';
    event.preventDefault();
  }

  else if (password === '') {
    msgElement.textContent = 'パスワードを入力してください。';
    password.style.border = '2px solid red';
    event.preventDefault();
  }
};