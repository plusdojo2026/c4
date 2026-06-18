// ダイアログ処理
const stockDialog = document.querySelector('.stock-edit-dialog');

const dialogJan = stockDialog.querySelector('.dialog-jan');
const dialogProductName = stockDialog.querySelector('.dialog-product-name');
const dialogStock = stockDialog.querySelector('.dialog-stock');

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

    stockDialog.showModal();

    requestAnimationFrame(() => {
      stockDialog.classList.add("show");
    });
  });
});

document.querySelectorAll('.cancel-btn').forEach((btn) => {
  btn.addEventListener('click', () => {
    stockDialog.classList.remove("show");
    setTimeout(() => {
      btn.closest('dialog')?.close();
    }, 250);
  });
});

const incrementBtn = document.querySelector(".increment-btn");
const decrementBtn = document.querySelector(".decrement-btn");
const janCode = document.querySelector(".jancode");
const changeQuantityEl = document.querySelector(".change-quantity");
const newQuantityEl = document.querySelector(".new-quantity");

function updateDisplay() {
  janCode.value = currentJancode;
  changeQuantityEl.value = changeQuantity;
  newQuantityEl.value = currentStock + changeQuantity;
}

incrementBtn.addEventListener("click", () => {
  changeQuantity++;
  updateDisplay();
});

decrementBtn.addEventListener("click", () => {
  changeQuantity--;
  updateDisplay();
});

changeQuantityEl.addEventListener('input', () => {
  changeQuantity = changeQuantityEl.value;
  newQuantityEl.value = Number(currentStock) + Number(changeQuantity);
});

const newCheckdialog = document.querySelector(".new-check-dialog");

newCheckdialog.showModal();
 requestAnimationFrame(() => {
  newCheckdialog.classList.add("show");
});