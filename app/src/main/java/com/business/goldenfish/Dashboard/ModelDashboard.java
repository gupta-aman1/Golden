package com.business.goldenfish.Dashboard;

public class ModelDashboard {

    private String title;
    private int imgid;
    private String visibility;

    public String getVisibility() {
        return visibility;
    }

    public ModelDashboard(String title, int imgid, String visibility, String status) {
        this.title = title;
        this.imgid = imgid;
        this.visibility = visibility;
        this.status = status;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

}
