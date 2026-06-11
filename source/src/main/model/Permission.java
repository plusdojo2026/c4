package model;

import java.io.Serializable;

public class Permission implements Serializable {
    private int id; // 権限ID
    private String permission; // 権限（1:管理者, 2:一般ユーザー）

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getPermission() { return permission; }
    public void setPermission(String permission) { this.permission = permission; }

    public Permission(int id, String permission) {
        super();
        this.id = id;
        this.permission = permission;
    }

    public Permission() {
        super();
        this.id = 0;
        this.permission = "";
    }
}