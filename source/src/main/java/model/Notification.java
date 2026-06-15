package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Notification implements Serializable {
    private int id; // ID
    private String message; // 通知内容
    private LocalDateTime createdAt; // 作成日
    private int isRead; // 既読フラグ (0: 未読)
    private int isDeleted; // 削除フラグ (0: 未削除)

    // JSP表示用にフォーマットされた日時を返すメソッド
    public String getFormattedCreatedAt() {
        if (this.createdAt == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        return this.createdAt.format(formatter);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public int getIsRead() { return isRead; }
    public void setIsRead(int isRead) { this.isRead = isRead; }
    public int getIsDeleted() { return isDeleted; }
    public void setIsDeleted(int isDeleted) { this.isDeleted = isDeleted; }

    public Notification(int id, String message, LocalDateTime createdAt, int isRead, int isDeleted) {
        super();
        this.id = id;
        this.message = message;
        this.createdAt = createdAt;
        this.isRead = isRead;
        this.isDeleted = isDeleted;
    }

    public Notification() {
        super();
        this.id = 0;
        this.message = "";
        this.isRead = 0;
        this.isDeleted = 0;
    }
}