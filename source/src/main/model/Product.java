import java.io.Serializable;
import java.time.LocalDateTime;

public class Product implements Serializable {
    private String janCode; // JAN
    private String productName; // 商品名
    private String baseProductId; // 紐づけ先
    private int caseQuantity; // 入数（ケース24本、バラ1本）
    private String photoPath; // 商品写真のパス
    private int durationDays; // 入荷日から通知日までの期間（日数）
    private LocalDateTime createdAt; // 作成日
    private LocalDateTime updatedAt; // 更新日

    public String getJanCode() { return janCode; }
    public void setJanCode(String janCode) { this.janCode = janCode; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getBaseProductId() { return baseProductId; }
    public void setBaseProductId(String baseProductId) { this.baseProductId = baseProductId; }
    public int getCaseQuantity() { return caseQuantity; }
    public void setCaseQuantity(int caseQuantity) { this.caseQuantity = caseQuantity; }
    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }
    public int getDurationDays() { return durationDays; }
    public void setDurationDays(int durationDays) { this.durationDays = durationDays; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Product(String janCode, String productName, String baseProductId, int caseQuantity,
            String photoPath, int durationDays, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super();
        this.janCode = janCode;
        this.productName = productName;
        this.baseProductId = baseProductId;
        this.caseQuantity = caseQuantity;
        this.photoPath = photoPath;
        this.durationDays = durationDays;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Product() {
        super();
        this.janCode = "";
        this.productName = "";
        this.baseProductId = null;
        this.caseQuantity = 1;
        this.photoPath = null;
        this.durationDays = 0;
    }
}