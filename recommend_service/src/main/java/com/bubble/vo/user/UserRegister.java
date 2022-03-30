package com.bubble.vo.user;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/29 1:58 下午
 * @Desc :
 */
public class UserRegister {
    private String userName;
    private String userEmail;
    private String userAvatar;
    private String userPwd;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

}
