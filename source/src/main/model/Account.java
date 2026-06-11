import java.io.Serializable;

public class Account implements Serializable {
    private int id; // 社員番号
    private String name; // 氏名
    private String birthday; // 生年月日
    private String password; // パスワード
    private int permissionsId; // 権限テーブルのID

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBirthday() { return birthday; }
    public void setBirthday(String birthday) { this.birthday = birthday; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public int getPermissionsId() { return permissionsId; }
    public void setPermissionsId(int permissionsId) { this.permissionsId = permissionsId; }

    public Account(int id, String name, String birthday, String password, int permissionsId) {
        super();
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.password = password;
        this.permissionsId = permissionsId;
    }

    public Account() {
        super();
        this.id = 0;
        this.name = "";
        this.birthday = "";
        this.password = "";
        this.permissionsId = 0;
    }
}