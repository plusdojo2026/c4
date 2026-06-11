package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Stock implements Serializable {
    private int id; // ID
    private String jancode; // JANコード
    private int stockQuantity; // 在庫数
    private LocalDateTime createdAt; // 作成日
    private LocalDateTime updatedAt; // 更新日
    private int stores; // 店舗

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getJancode() { return jancode; }
    public void setJancode(String jancode) { this.jancode = jancode; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public int getStores() { return stores; }
    public void setStores(int stores) { this.stores = stores; }

    public Stock(int id, String jancode, int stockQuantity, LocalDateTime createdAt, LocalDateTime updatedAt, int stores) {
        super();
        this.id = id;
        this.jancode = jancode;
        this.stockQuantity = stockQuantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.stores = stores;
    }

    public Stock() {
        super();
        this.id = 0;
        this.jancode = "";
        this.stockQuantity = 0;
        this.stores = 1;
    }
}