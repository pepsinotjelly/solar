package com.bubble.vo;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/31 9:32 下午
 * @Desc :
 */
public class ResponseEntity {

    public ResponseEntity() {
    }

    public ResponseEntity(int status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private int status;

    private String msg;

    private Object data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
