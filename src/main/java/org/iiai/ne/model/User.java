package org.iiai.ne.model;

public class User {
    private int id;

    private String openId;

    private String weChatName;

    private String imgUrl;

    public User() {
    }

    public User(String openId, String weChatName, String imgUrl) {
        this.openId = openId;
        this.weChatName = weChatName;
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getWeChatName() {
        return weChatName;
    }

    public void setWeChatName(String weChatName) {
        this.weChatName = weChatName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
