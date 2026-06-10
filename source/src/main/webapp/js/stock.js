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