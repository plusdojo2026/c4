const form = document.getElementById('form');

form.onsubmit = function (event) {

  console.log('test');

  // 2. スタイル（塗りつぶし）を変更するために、入力欄の「要素そのもの」を取得
  let employeeNumberInput = document.getElementById('employeeNumber');
  let passwordInput = document.getElementById('password');
  let msgElement = document.getElementById('msg');

  // 一度、前回のエラーの塗りつぶしをリセットする
  employeeNumberInput.style.backgroundColor = '';
  passwordInput.style.backgroundColor = '';
  msgElement.style.fontWeight = 'bold';//太字にする
  msgElement.style.color = 'red';//赤字にする

  //入力された「文字（値）」を取得
  let employeeNumber = employeeNumberInput.value;
  let password = passwordInput.value;

  //メッセージ変更とエラーの塗りつぶしの適用
  if (employeeNumber === '' && password === '') {
    msgElement.textContent = '社員番号とパスワードを入力してください。';
    //中身を薄い赤で塗りつぶす
    employeeNumberInput.style.backgroundColor = '#ffeeee';
    passwordInput.style.backgroundColor = '#ffeeee';
    event.preventDefault();
  }
  else if (employeeNumber === '') {
    msgElement.textContent = '社員番号を入力してください。';
    //中身を薄い赤で塗りつぶす
    employeeNumberInput.style.backgroundColor = '#ffeeee';
    event.preventDefault();
  }
  else if (password === '') {
    msgElement.textContent = 'パスワードを入力してください。';
    passwordInput.style.backgroundColor = '#ffeeee';
    event.preventDefault();
  }
};