package com.bubble.vo;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/27 10:39 上午
 * @Desc :
 */
public class UserEntity {

    private int userId;
    private String userPwd;
    private String userName;
    private String userAvatar;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }


}
