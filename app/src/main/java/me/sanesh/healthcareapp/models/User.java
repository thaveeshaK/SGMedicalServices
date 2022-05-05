package me.sanesh.healthcareapp.models;

public class User {

    private String uid;
    private String userName;
    private String userFullName;
    private String email;
    private String password;
    private String phoneNo;
    private String accountType;

    public User(String uid, String userName, String userFullName, String email, String password, String phoneNo, String accountType) {
        this.uid = uid;
        this.userName = userName;
        this.userFullName = userFullName;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
        this.accountType = accountType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
