package model;

import java.io.Serializable;

public class Stock implements Serializable {
    private int id; // ID
    private String jancode; // JANコード
    private String productName; // 商品名
    private int stockQuantity; // 在庫数
    private int durationDays; // 賞味期限までの日数
    private int stores; // 店舗

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getJancode() { return jancode; }
    public void setJancode(String jancode) { this.jancode = jancode; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    public int getDurationDays() { return durationDays; }
    public void setDurationDays(int durationDays) { this.durationDays = durationDays; }
    public int getStores() { return stores; }
    public void setStores(int stores) { this.stores = stores; }

    public Stock(int id, String jancode, String productName, int stockQuantity, int durationDays, int stores) {
        super();
        this.id = id;
        this.jancode = jancode;
        this.productName = productName;
        this.stockQuantity = stockQuantity;
        this.durationDays = durationDays;
        this.stores = stores;
    }

    public Stock() {
        super();
        this.id = 0;
        this.jancode = "";
        this.stockQuantity = 0;
        this.durationDays = 0;
        this.stores = 1;
    }
}