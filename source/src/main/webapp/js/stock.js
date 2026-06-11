// camera制御
const video = document.getElementById('video');
const cameraButton = document.getElementById('camera-button');
const cameraOn = document.getElementById('camera-on');
const cameraOff = document.getElementById('camera-off');

let stream = null;
let isCameraOn = false;

async function startCamera() {
  try {
    stream = await navigator.mediaDevices.getUserMedia({
      video: {
        facingMode: "environment",
        width: { ideal: 640 },
        height: { ideal: 480 }
      },
      audio: false,
    });

    video.srcObject = stream;
    isCameraOn = true;
    video.classList.add('active');
    cameraOn.classList.remove('active');
    cameraOff.classList.add('active');

    video.onloadedmetadata = () => {
      startScanLoop();
    };
  } catch (e) {
    console.log(e);
  }
}

function stopCamera() {
  clearInterval(scanInterval);
  scanInterval = null;

  reader.reset();

  if (stream) {
    stream.getTracks().forEach((track) => {
      track.stop();
    });
  }

  video.srcObject = null;
  stream = null;

  isCameraOn = false;
  video.classList.remove('active');
  cameraOff.classList.remove('active');
  cameraOn.classList.add('active');
}

cameraButton.addEventListener('click', () => {
  isCameraOn ? stopCamera() : startCamera();
});

// バーコード処理
const {
  BrowserMultiFormatReader,
  BarcodeFormat,
  DecodeHintType
} = ZXing;

const frame = document.querySelector('.barcode-frame');

let scanInterval = null;
let isScanning = false;
let lastJan = '';

const hints = new Map();

hints.set(
  DecodeHintType.POSSIBLE_FORMATS,
  [BarcodeFormat.EAN_13]
);

const reader = new ZXing.MultiFormatReader();
reader.setHints(hints);
const canvas = document.createElement('canvas');

const ctx = canvas.getContext('2d', {
  willReadFrequently: true
});

function startScanLoop() {
  if (scanInterval) return;

  scanInterval = setInterval(scanBarcode, 2000);
}

async function scanBarcode() {
  if (isScanning) return;
  if (!video.videoWidth) return;

  isScanning = true;

  try {
    const videoRect = video.getBoundingClientRect();
    const frameRect = frame.getBoundingClientRect();
    const scaleX = video.videoWidth / videoRect.width;
    const scaleY = video.videoHeight / videoRect.height;
    const sx = (frameRect.left - videoRect.left) * scaleX;
    const sy = (frameRect.top - videoRect.top) * scaleY;
    const sw = frameRect.width * scaleX;
    const sh = frameRect.height * scaleY;
    canvas.width = sw;
    canvas.height = sh;

    ctx.drawImage(
      video,
      sx, sy, sw, sh,
      0, 0, sw, sh
    );

    const luminanceSource = new ZXing.HTMLCanvasElementLuminanceSource(
      canvas
    );

    const binaryBitmap = new ZXing.BinaryBitmap(
      new ZXing.HybridBinarizer(
        luminanceSource
      )
    );

    const result = reader.decode(binaryBitmap);

    if (!result) {
      isScanning = false;
      return;
    }

    const jan = result.getText();

    if (!/^\d{13}$/.test(jan)) {
      isScanning = false;
      return;
    }

    if (jan === lastJan) {
      isScanning = false;
      return;
    }

    lastJan = jan;

    console.log('JAN:', jan);

    setTimeout(() => {
      lastJan = '';
    }, 2000);
  } catch (e) {
    console.log(e);
  }

  isScanning = false;
}