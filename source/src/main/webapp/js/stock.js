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
const searchInput = document.getElementById('search-input');

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

    searchInput.value = jan;

    setTimeout(() => {
      lastJan = '';
    }, 2000);
  } catch (e) {
    console.log(e);
  }

  isScanning = false;
}

// ダイアログ処理
const stockAddDialog = document.querySelector('.stock-add-dialog');
const stockEditDialog = document.querySelector('.stock-edit-dialog');
const newCheckDialog = document.querySelector(".new-check-dialog");

const addButton = document.getElementById('add-button');

const dialogJan = stockEditDialog.querySelector('.dialog-jan');
const dialogProductName = stockEditDialog.querySelector('.dialog-product-name');
const dialogStock = stockEditDialog.querySelector('.dialog-stock');

let changeQuantity;
let currentStock;
let currentJancode;

document.querySelectorAll('.stock-row').forEach((row) => {
  row.addEventListener('click', () => {
    const {
      jan,
      productName,
      stockQuantity,
    } = row.dataset;

    currentJancode = jan;
    currentStock = Number(stockQuantity);
    changeQuantity = 0;

    dialogJan.textContent = jan;
    dialogProductName.textContent = productName;
    dialogStock.textContent = stockQuantity;

    updateDisplay();

    stockEditDialog.showModal();

    requestAnimationFrame(() => {
      stockEditDialog.classList.add("show");
    });
  });
});

addButton.addEventListener('click', () => {
  changeQuantity = 0;
  stockAddDialog.showModal();
  updateDisplay();
  requestAnimationFrame(() => {
    stockAddDialog.classList.add("show");
  });
});

document.querySelectorAll('.cancel-btn').forEach((btn) => {
  btn.addEventListener('click', () => {
    stockAddDialog.classList.remove("show");
    stockEditDialog.classList.remove("show");
    newCheckDialog?.classList.remove("show");
    setTimeout(() => {
      btn.closest('dialog')?.close();
    }, 250);
  });
});

const incrementBtns = document.querySelectorAll(".increment-btn");
const decrementBtns = document.querySelectorAll(".decrement-btn");
const janCode = document.querySelector(".jancode");
const changeQuantityEl = document.querySelector(".change-quantity");
const addQuantityEl = document.querySelector(".add-quantity");
const newQuantityEl = document.querySelector(".new-quantity");

function updateDisplay() {
  janCode.value = currentJancode;
  changeQuantityEl.value = changeQuantity;

  if(stockAddDialog.open) {
    console.log("stockAddDialog changeValues is :" + Number(changeQuantity));
    if(Number(changeQuantity) < 1) {
      changeQuantity = 1;
      addQuantityEl.value = 1;
    } else {
      addQuantityEl.value = changeQuantity;
    }
  }

  if(stockEditDialog.open) {
    console.log("stockEditDialog changeValues is :" + Number(changeQuantity));
    newQuantityEl.value = currentStock + changeQuantity;
  }
}

incrementBtns.forEach((btn) => {
  btn.addEventListener('click', () => {
    changeQuantity++;
    updateDisplay();
  });
});

decrementBtns.forEach((btn) => {
  btn.addEventListener('click', () => {
    changeQuantity--;
    updateDisplay();
  });
});

changeQuantityEl.addEventListener('input', () => {
  changeQuantity = changeQuantityEl.value;
  newQuantityEl.value = Number(currentStock) + Number(changeQuantity);
});

if(newCheckDialog != null) {
  newCheckDialog.showModal();

  requestAnimationFrame(() => {
    newCheckDialog.classList.add("show");
  });
}