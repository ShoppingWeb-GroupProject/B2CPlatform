package model;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String role;
    private boolean isBlacklisted;
    private String phone;  
    private String address; 
    private double discount;
    private String lineId;

    // === Getter & Setter ===
    
    public String getLineId() { return lineId; }
    public void setLineId(String lineId) { this.lineId = lineId; }

    public double getDiscount() { return discount; }

    public void setDiscount(double discount) {
        this.discount = discount;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public boolean isBlacklisted() {
        return isBlacklisted;
    }
    public void setBlacklisted(boolean isBlacklisted) {
        this.isBlacklisted = isBlacklisted;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
