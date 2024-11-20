package model.entities;

public class Administrator {
    private int ID;
    private String Password;
    private String Salt;

    public Administrator(int id, String password, String salt) {
        this.ID = id;
        this.Password = password;
        this.Salt = salt;
    }
}
