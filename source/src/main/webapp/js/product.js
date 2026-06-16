document.addEventListener("DOMContentLoaded", () => {

  // 新規追加ポップアップ（確認）
  const dialog1 = document.querySelector(".newcheck");
  // 新規追加モーダル
  const dialog2 = document.querySelector(".newmodal");

  // 「新規追加」ボタン → dialog1 を開く
  const showButton1 = document.querySelector("#add-btn");
  showButton1.addEventListener("click", () => {
    dialog1.showModal();
  });

  // dialog1 の「キャンセル」
  const closeButton1 = document.querySelector("#closedialog");
  closeButton1.addEventListener("click", () => {
    dialog1.close();
  });
  // dialog2のキャンセル
  const closeButton2 = document.querySelector("#cancel");
  closeButton2.addEventListener("click", () => {
    dialog2.close();
  });
  // dialog1 の「追加する」→ dialog1 を閉じて dialog2 を開く
  const showButton2 = document.querySelector("#add");
  showButton2.addEventListener("click", () => {
    dialog1.close();
    dialog2.showModal();
  });
  // 画像追加
  document.getElementById("add-photo").addEventListener("change" , function(e){
    const file = e.target.files[0];
    if(!file)return;

    const reader = new FileReader();
    reader.onload = function(event) {
        document.getElementById("preview").src = event.target.result;
    };
    reader.readAsDataURL(file);
  })
	
// 削除確認ポップアップ
const dialog3 = document.querySelector(".deletecheck");
// 削除結果通知ポップアップ
const dialog4 = document.querySelector(".deleteresult");

// 削除記号押す　→　dialog3（削除確認）開く
const showButton3 = document.querySelector("#delete-check");
showButton3.addEventListener("click", () => {
// 削除件数表示
const checked = document.querySelectorAll(".edit-check:checked").length;

document.querySelector(".deletecheck p").innerHTML =
    `${checked} 件選択されています。<br>選択商品を削除しますか？`;

  dialog3.showModal();
});

// dialog3 の「キャンセル」
const closeButton3 = document.querySelector("#cancel2");
closeButton3.addEventListener("click", () => {
  dialog3.close();
});

// dialog4（削除結果通知） の「キャンセル」
const closeButton4 = document.querySelector("#cancel3");
closeButton4.addEventListener("click", () => {
  dialog4.close();
});

// 削除フォーム
const deleteForm = document.querySelector("#delete-form");
const deleteIdsInput = document.querySelector("#delete-ids");

// dialog3 の「削除する」ボタンに削除処理を追加
document.querySelector("#delete").addEventListener("click", () => {

  // チェックされた項目を取得
  const checked = document.querySelectorAll(".edit-check:checked");
  const ids = Array.from(checked).map(c => c.value);

  // hidden にセット
  deleteIdsInput.value = ids.join(",");

  // フォーム送信
  deleteForm.submit();
});



// 編集ポップアップ
	const dialog5 = document.querySelector(".edit");
// 編集記号押す　→　dialog5開く
	const showButton5 = document.querySelector("#edit-button");
  showButton5.addEventListener("click", () => {
    dialog5.showModal();
  });
// dialog1 の「キャンセル」
  const closeButton5 = document.querySelector("#cancel4");
  closeButton5.addEventListener("click", () => {
    dialog5.close();
  });
// 編集ポップアップから削除ポップアップ
	const showButton6 = document.querySelector("#delete-check2");
  showButton6.addEventListener("click", () => {
		dialog5.close();
    dialog3.showModal();
  });
// 行クリックで編集モーダルを開く
document.addEventListener("click", function(e) {

  // 編集モードでなければ何もしない
  if (!showButton5.classList.contains("active")) return;

  const row = e.target.closest("tr");
  if (!row) return;

  // 行のデータ取得
  const id = row.querySelector(".edit-check").value;
  const janCode = row.querySelector(".td-jan").textContent;
  const productName = row.querySelector(".td-name").textContent;
  const durationDays = row.querySelector(".td-term").textContent;
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
});

  function checkClear(checked){
    var checkbox = document.querySelectorAll('input[name = "checks"]');
    if(checked === true){
        for(var n = 0; n < checkbox.length; n++){
            checkbox[n].checked = true;
        }//end for
    }else if(checked === false){
        for(var n = 0; n > checkbox.length; n++){
            checkbox[n].checked = false;
        }//end for
    }//end if
  }

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

});
