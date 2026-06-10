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