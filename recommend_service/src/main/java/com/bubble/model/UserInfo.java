package com.bubble.model;

public class UserInfo {
    private Integer id;

    private String name;

    private String email;

    private String avatarcolor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getAvatarcolor() {
        return avatarcolor;
    }

    public void setAvatarcolor(String avatarcolor) {
        this.avatarcolor = avatarcolor == null ? null : avatarcolor.trim();
    }
}