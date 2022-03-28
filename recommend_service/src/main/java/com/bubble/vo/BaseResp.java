package com.bubble.vo;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/28 9:36 上午
 * @Desc :
 */
public class BaseResp {
    private String token;
    private String baseMsg;
    private int baseCode;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBaseMsg() {
        return baseMsg;
    }

    public void setBaseMsg(String baseMsg) {
        this.baseMsg = baseMsg;
    }

    public int getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(int baseCode) {
        this.baseCode = baseCode;
    }

}
