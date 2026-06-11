package model;

import java.io.Serializable;
import java.time.LocalDate;

public class StockMovement implements Serializable {
    private String jancode; // JAN
    private int stockId; // 在庫テーブルのID
    private String reason; // 理由
    private int quantity; // 増減数
    private LocalDate receivedAt; // 入荷日
    private LocalDate notifyAt; // 通知日

    public String getJancode() { return jancode; }
    public void setJancode(String jancode) { this.jancode = jancode; }
    public int getStockId() { return stockId; }
    public void setStockId(int stockId) { this.stockId = stockId; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public LocalDate getReceivedAt() { return receivedAt; }
    public void setReceivedAt(LocalDate receivedAt) { this.receivedAt = receivedAt; }
    public LocalDate getNotifyAt() { return notifyAt; }
    public void setNotifyAt(LocalDate notifyAt) { this.notifyAt = notifyAt; }

    public StockMovement(String jancode, int stockId, String reason, int quantity, LocalDate receivedAt, LocalDate notifyAt) {
        super();
        this.jancode = jancode;
        this.stockId = stockId;
        this.reason = reason;
        this.quantity = quantity;
        this.receivedAt = receivedAt;
        this.notifyAt = notifyAt;
    }

    public StockMovement() {
        super();
        this.jancode = "";
        this.stockId = 0;
        this.reason = "";
        this.quantity = 0;
    }
}