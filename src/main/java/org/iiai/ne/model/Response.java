package org.iiai.ne.model;

public class Response {
    private static final String OK = "ok";
    private static final String ERROR = "error";

    private Meta meta;     // 元数据
    private Object data;   // 响应内容

    public Response success() {
        this.meta = new Meta(true, OK);
        return this;
    }

    public Response success(Object data) {
        this.meta = new Meta(true, OK);
        this.data = data;
        return this;
    }

    public Response failure() {
        this.meta = new Meta(false, ERROR);
        return this;
    }

    public Response failure(String message) {
        this.meta = new Meta(false, message);
        return this;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}


class Meta {
    private boolean success;

    private String message;

    public Meta(boolean success) {
        this.success = success;
    }

    public Meta(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
