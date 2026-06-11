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

	
	// 削除確認ポップアップ
  const dialog3 = document.querySelector(".deletecheck");
	// 削除結果通知ポップアップ
  const dialog4 = document.querySelector(".deleteresult");

// 削除記号押す　→　dialog3開く
	const showButton3 = document.querySelector("#delete-check");
  showButton3.addEventListener("click", () => {
    dialog3.showModal();
  });
// dialog3 の「キャンセル」
  const closeButton3 = document.querySelector("#cancel2");
  closeButton3.addEventListener("click", () => {
    dialog3.close();
  });
// dialog3 の「削除する」→ dialog3 を閉じて dialog4 を開く
  const showButton4 = document.querySelector("#delete");
  showButton4.addEventListener("click", () => {
    dialog3.close();
    dialog4.showModal();
  });
// dialog3 の「キャンセル」
  const closeButton4 = document.querySelector("#cancel3");
  closeButton4.addEventListener("click", () => {
    dialog4.close();
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
})