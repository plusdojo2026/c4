
console.log("JS 読み込み OK");

// 全選択チェックボックスの制御
const checkAll = document.querySelector("#check-all");
if (!checkAll) {
  console.log("check-all が見つかりません");
}
checkAll.addEventListener("click", () => {
  console.log("check-all clicked");
});
checkAll.addEventListener("change", () => {
  console.log("check-all changed");
  const checks = document.querySelectorAll(".edit-check");
  checks.forEach(c => c.checked = checkAll.checked);
});



// 商品追加確認ダイアログの制御
const addButton = document.querySelector("#add-button"); // 商品追加確認ダイアログ起動ボタン
const newCheckdialog = document.querySelector("#dialog-new-confirm");   // 商品追加確認ダイアログ
const newCheckdialogCloseButton = document.querySelector("#new-dialog-closeButton");  // キャンセルボタン
const newCheckdialogAddButton = document.querySelector("#new-dialog-addButton");  // 追加するボタン

//  商品追加確認ダイアログ を開く
addButton.addEventListener("click", () => {
  newCheckdialog.showModal();
  requestAnimationFrame(() => {
    newCheckdialog.classList.add("show");
  });
});

// 商品追加確認ダイアログ の「キャンセル」
newCheckdialogCloseButton.addEventListener("click", () => {
  newCheckdialog.classList.remove("show");
  setTimeout(() => {
    newCheckdialog.close();
  }, 250);
});


//商品追加モーダルの制御
const newModaldialog = document.querySelector("#dialog-new-modal");   // 新規追加モーダル
const newModalCloseButton = document.querySelector("#dialog-new-modal-closeButton"); // 新規追加モーダルの閉じるボタン

newModalCloseButton.addEventListener("click", () => {
  newModaldialog.classList.remove("show");
  setTimeout(() => {
    newModaldialog.close();
  }, 250);
});

// 新規追加ダイアログ の「追加する」→ 新規追加ダイアログ を閉じて newModaldialog を開く
newCheckdialogAddButton.addEventListener("click", () => {
  newModaldialog.dispatchEvent(new Event("close"));
  newCheckdialog.classList.remove("show");
  setTimeout(() => {
    newCheckdialog.close();
  }, 250);
  newModaldialog.showModal();
  requestAnimationFrame(() => {
    newModaldialog.classList.add("show");
  });
});
// ケース・バラの入力
const caseNo = document.getElementById("case-no");
const caseYes = document.getElementById("case-yes");
const caseQty = document.getElementById("case-qty");
const singleSelect = document.getElementById("single-select");
const singleSelectFieldset = document.getElementById("single-select-fieldset");
const baraForm = document.getElementById("bara-form");

//  新規追加モーダルを閉じたときに初期表示へ戻す
newModaldialog.addEventListener("close", () => {
  caseNo.checked = true;
  caseYes.checked = false;

  singleSelect.style.display = "none";
  singleSelectFieldset.style.display = "none";
  baraForm.style.display = "none";

  caseQty.value = 1;
  caseQty.disabled = true;

  singleSelect.selectedIndex = 0;
  singleSelect.innerHTML = '<option value="">選択してください</option>';


  document.getElementById("bara-jan").value = "";
  document.getElementById("bara-name").value = "";
  document.getElementById("bara-term").value = "";
  document.getElementById("selectedName").value = "";
  document.getElementById("selectedTerm").value = "";

  document.getElementById("JAN").value = "";
  document.getElementById("pname").value = "";
  document.getElementById("term").value = "";
  document.getElementById("add-photo").value = "";

  // 画像プレビューがあるなら消す
  const preview = document.getElementById("preview");
  if (preview) preview.src = "";
});
//  プルダウン選択 → バラ欄に自動入力（1回だけ登録）
singleSelect.addEventListener("change", () => {

  const opt = singleSelect.options[singleSelect.selectedIndex];

  if (!opt || opt.value === "") {
    document.getElementById("bara-jan").value = "";
    document.getElementById("bara-name").value = "";
    document.getElementById("bara-term").value = "";
    return;
  }

  document.getElementById("bara-jan").value = opt.value;
  document.getElementById("bara-name").value = opt.dataset.name;
  document.getElementById("bara-term").value = opt.dataset.term;
});


// ▼ 初期状態（単品）ではプルダウン非表示
singleSelect.style.display = "none";
singleSelectFieldset.style.display = "none";

// ▼ 単品を選んだとき
caseNo.addEventListener("change", () => {
  singleSelect.style.display = "none";
  singleSelectFieldset.style.display = "none";
});

// ▼ ケース商品を選んだとき
caseYes.addEventListener("change", () => {

  // プルダウン初期化
  singleSelect.innerHTML = '<option value="">選択してください</option>';

  // 商品一覧テーブルから単品だけ抽出して追加
  document.querySelectorAll("#product-table-body tr").forEach(row => {
    if (row.dataset.caseQuantity === "1") {  // 単品だけ
      const jan = row.querySelector(".td-jan").textContent;
      const name = row.querySelector(".td-name").textContent;
      const term = row.querySelector(".td-term").textContent;

      const opt = document.createElement("option");
      opt.value = jan;
      opt.textContent = `${name}（${jan}）`;
      opt.dataset.name = name;
      opt.dataset.term = term;

      singleSelect.appendChild(opt);
    }
  });

  singleSelect.style.display = "block";
  singleSelectFieldset.style.display = "block";
});


//  UI 更新関数
function updateCaseUI() {
  const isCase = caseYes.checked;

  if (isCase) {
    // ケース商品
    caseQty.disabled = false;
    baraForm.style.display = "block";

    // バラ入力欄を有効化
    document.getElementById("bara-jan").disabled = false;
    document.getElementById("bara-name").disabled = false;
    document.getElementById("bara-term").disabled = false;

    // placeholder をケース用に変更
    document.getElementById("JAN").placeholder = "ケースJAN";
    document.getElementById("pname").placeholder = "ケース商品名";
    document.getElementById("term").placeholder = "ケース期間";

    // ▼ プルダウンを単品だけで再生成
    singleSelect.innerHTML = '<option value="">選択してください</option>';

    document.querySelectorAll("#product-table-body tr").forEach(row => {
      if (row.dataset.caseQuantity === "1") {  // 単品だけ
        const jan = row.querySelector(".td-jan").textContent;
        const name = row.querySelector(".td-name").textContent;
        const term = row.querySelector(".td-term").textContent;

        const opt = document.createElement("option");
        opt.value = jan;
        opt.textContent = `${name}（${jan}）`;
        opt.dataset.name = name;
        opt.dataset.term = term;

        singleSelect.appendChild(opt);
      }
    });


    // メッセージ
    // singleMessage.style.display = (singleSelect.value !== "") ? "block" : "none";

  } else {
    // 単品
    caseQty.value = 1;
    caseQty.disabled = true;
    baraForm.style.display = "none";
    singleSelectFieldset.style.display = "none";

    // バラ入力欄を無効化（required を無効化）
    document.getElementById("bara-jan").disabled = true;
    document.getElementById("bara-name").disabled = true;
    document.getElementById("bara-term").disabled = true;

    // placeholder を単品用に戻す
    document.getElementById("JAN").placeholder = "バラJAN";
    document.getElementById("pname").placeholder = "バラ商品名";
    document.getElementById("term").placeholder = "バラ期間";;
  }
}

// ★ イベント登録
caseNo.addEventListener("change", updateCaseUI);
caseYes.addEventListener("change", updateCaseUI);
caseQty.addEventListener("input", updateCaseUI);
singleSelect.addEventListener("change", updateCaseUI);


//  初期化
updateCaseUI();




// 画像追加
const addPhoto = document.getElementById("add-photo");
if (addPhoto) {
  document.getElementById("add-photo").addEventListener("change", function (e) {
    const file = e.target.files[0];
    if (!file) return;

    const reader = new FileReader();
    reader.onload = function (event) {
      document.getElementById("preview").src = event.target.result;
    };
    reader.readAsDataURL(file);
  })
}
// 削除のスタイル
let deleteStyle = "checkbox";
// 削除確認ポップアップ
const dialog3 = document.querySelector(".deletecheck");
// 削除結果通知ポップアップ
const dialog4 = document.querySelector(".deleteresult");

// 削除記号押す　→　dialog3（削除確認）開く
const showButton3 = document.querySelector("#delete-button");
showButton3.addEventListener("click", () => {
  console.log("delete-check clicked");

  // チェックボックス数見る
   deleteStyle = "checkbox";

  // 削除件数表示
  const checked = document.querySelectorAll(".edit-check:checked").length;

  document.querySelector(".deletecheck p").innerHTML =
    `${checked} 件選択されています。<br>選択商品を削除しますか？`;

  dialog3.showModal();
  requestAnimationFrame(() => {
    dialog3.classList.add("show");
  });
});

// dialog3 の「キャンセル」
const closeButton3 = document.querySelector("#cancel2");
closeButton3.addEventListener("click", () => {
  dialog3.classList.remove("show");
  setTimeout(() => {
    dialog3.close();
  }, 250);
});

// dialog4（削除結果通知） の「キャンセル」
const closeButton4 = document.querySelector("#cancel3");
closeButton4.addEventListener("click", () => {
  dialog4.classList.remove("show");
  setTimeout(() => {
    dialog4.close();
  }, 250);
});

// 削除フォーム
const deleteForm = document.querySelector("#delete-form");
const deleteIdsInput = document.querySelector("#delete-ids");

// dialog3 の「削除する」ボタンに削除処理を追加
document.querySelector("#delete").addEventListener("click", () => {

  if (deleteStyle === "clicked") {
    // 行クリックされた1件を削除
    const jan = clickedRow.querySelector(".td-jan").textContent.trim();
    deleteIdsInput.value = jan;

  } else {
    // チェックボックス削除
    const checked = document.querySelectorAll(".edit-check:checked");
    const ids = Array.from(checked).map(c => c.value);
    deleteIdsInput.value = ids.join(",");
  }

  // フォーム送信
  deleteForm.submit();
});




// 新規追加ダイアログ の「キャンセル」
const closeButton5 = document.querySelector("#cancel4");
closeButton5.addEventListener("click", () => {
  dialog5.classList.remove("show");
  setTimeout(() => {
    dialog5.close();
  }, 250);
});

// 編集ポップアップ
const dialog5 = document.querySelector(".edit");
// 編集記号押す　→　dialog5開く
// const showButton5 = document.querySelector("#edit-button");
// showButton5.addEventListener("click", () => {

// });

// 行クリックを数える変数
let clickedRow = null;
// 編集ポップアップから削除ポップアップ
const showButton6 = document.querySelector("#delete-check2");
showButton6.addEventListener("click", () => {

  // 行数見る
  deleteStyle = "clicked"

  dialog5.classList.remove("show");
  setTimeout(() => {
    dialog5.close();
  }, 250);

  if (clickedRow) {
    const jan = clickedRow.querySelector(".td-jan").textContent;
    const name = clickedRow.querySelector(".td-name").textContent;

    document.querySelector(".deletecheck p").innerHTML =
      `1 件選択されています。<br>選択商品を削除しますか？`;
  }


  dialog3.showModal();
  requestAnimationFrame(() => {
    dialog3.classList.add("show");
  });
});



// document.querySelector("#edit-button").addEventListener("click", () => {

// チェックされた行を取得
// const checked = document.querySelectorAll(".edit-check:checked");

// 0 件 → エラー
// if (checked.length === 0) {
// alert("編集する商品を 1 件選択してください");
// return;
// }

// 2 件以上 → エラー
// if (checked.length > 1) {
// alert("編集できるのは 1 件だけです");
// return;
// }

// 1 件だけ選択されている
// const checkbox = checked[0];
// const row = checkbox.closest("tr");

// });


// 行クリック編集
document.querySelectorAll("tr[data-base-product-id]").forEach(row => {
  row.addEventListener("click", (e) => {

    // チェックボックスをクリックしたときは無視
    if (e.target.classList.contains("edit-check")) return;

    //  クリック数
    clickedRow = row;

    // 行のデータ取得
    const id = row.querySelector(".edit-check").value;
    const janCode = row.querySelector(".td-jan").textContent;
    const productName = row.querySelector(".td-name").textContent;
    const durationText = row.querySelector(".td-term").textContent;
    const durationDays = durationText.replace(/\D/g, "");  
    const photoPath = row.querySelector("img").getAttribute("src");

    // data- 属性から取得（JSP に追加済み）
    const baseProductId = row.dataset.baseProductId;
    const caseQuantity = row.dataset.caseQuantity;

    // モーダルに値をセット
    document.getElementById("edit-id").value = id;
    document.getElementById("edit-jan").value = janCode;
    document.getElementById("edit-name").value = productName;
    document.getElementById("edit-term").value = durationDays;

    document.getElementById("edit-photo").value = photoPath;
    document.getElementById("edit-base").value = baseProductId;
    document.getElementById("edit-case").value = caseQuantity;

    // モーダルを開く
    dialog5.showModal();
    requestAnimationFrame(() => {
      dialog5.classList.add("show");
    });
  });
});



// camera制御
const video = document.getElementById('video');
const cameraButton = document.getElementById('camera-button');
const cameraOn = document.getElementById('camera-on');
const cameraOff = document.getElementById('camera-off');

let stream = null;
let isCameraOn = false;

async function startCamera() {
  navigator.mediaDevices.getUserMedia({
    video: {
      facingMode: "user",
    },
    audio: false,
  })
    .then((mediaStream) => {
      stream = mediaStream;
      video.srcObject = stream;
      isCameraOn = true;
      video.classList.add('active');
      cameraOn.classList.remove('active');
      cameraOff.classList.add('active');
    })
    .catch((e) => {
      console.log(e);
    });
}

function stopCamera() {
  if (!stream) return;

  stream.getTracks().forEach((track) => {
    track.stop();
  });

  video.srcObject = null;
  stream = null;

  isCameraOn = false;
  video.classList.remove('active');
  cameraOff.classList.remove('active');
  cameraOn.classList.add('active');
}

cameraButton.addEventListener('click', () => {
  if (isCameraOn) {
    stopCamera();
  } else {
    startCamera();
  }
});

// 要素が多すぎるため下に入れてます　↓
// 在庫ページから送られてきた hidden の値を取得
const stockJan = document.querySelector("input[name='send-jancode']")?.value;
const stockName = document.querySelector("input[name='send-productName']")?.value;

//  閉じるで初期化
newModaldialog.dispatchEvent(new Event("close"));
// ▼ 在庫ページから来たときだけ自動で開く
if (newModaldialog.dataset.isflag == 'true' && (stockJan != null && stockName != null)) {

  // ダイアログを自動で開く
  newModaldialog.showModal();
  requestAnimationFrame(() => newModaldialog.classList.add("show"));

  //  モーダル内の入力欄に値をセット
  document.querySelector("#JAN").value = stockJan;
  document.querySelector("#pname").value = stockName;
}



