package com.testSign.esign.admin.po;

public class EsignTemplate {
    private Integer id;

    private String replaceInfo;

    private String path;

    private String name;

    private Integer type;

    private Integer signX;

    private Integer signY;

    private Integer signOtherX;

    private Integer signOtherY;

    private Integer signPage;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReplaceInfo() {
        return replaceInfo;
    }

    public void setReplaceInfo(String replaceInfo) {
        this.replaceInfo = replaceInfo == null ? null : replaceInfo.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSignX() {
        return signX;
    }

    public void setSignX(Integer signX) {
        this.signX = signX;
    }

    public Integer getSignY() {
        return signY;
    }

    public void setSignY(Integer signY) {
        this.signY = signY;
    }

    public Integer getSignOtherX() {
        return signOtherX;
    }

    public void setSignOtherX(Integer signOtherX) {
        this.signOtherX = signOtherX;
    }

    public Integer getSignOtherY() {
        return signOtherY;
    }

    public void setSignOtherY(Integer signOtherY) {
        this.signOtherY = signOtherY;
    }

    public Integer getSignPage() {
        return signPage;
    }

    public void setSignPage(Integer signPage) {
        this.signPage = signPage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}