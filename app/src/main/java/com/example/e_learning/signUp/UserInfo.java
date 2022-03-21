package com.example.e_learning.signUp;

public class UserInfo {
    public String firstnameInfo, lastnameInfo, emailInfo;
    public String userType;

    public String getFirstnameInfo() {
        return firstnameInfo;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setFirstnameInfo(String firstnameInfo) {
        this.firstnameInfo = firstnameInfo;
    }

    public String getLastnameInfo() {
        return lastnameInfo;
    }

    public void setLastnameInfo(String lastnameInfo) {
        this.lastnameInfo = lastnameInfo;
    }

    public String getEmailInfo() {
        return emailInfo;
    }

    public void setEmailInfo(String emailInfo) {
        this.emailInfo = emailInfo;
    }

    public UserInfo(){

    }



    public UserInfo(String firstnameInfo, String lastnameInfo, String emailInfo, String userType){
        this.firstnameInfo = firstnameInfo;
        this.lastnameInfo = lastnameInfo;
        this.emailInfo = emailInfo;
        this.userType = userType;
    }
}
