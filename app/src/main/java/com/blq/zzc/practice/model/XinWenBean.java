package com.blq.zzc.practice.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/3.
 */
public class XinWenBean {
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    private List<String> imagesUrl;
    public void setImagesUrl(List<String> imageUrl) {
        this.imagesUrl = imageUrl;
    }
    public List<String> getImagesUrl() {
        return imagesUrl;
    }

    private String detailHtml;
    public void setDetailHtml(String detailHtml) {
        this.detailHtml = detailHtml;
    }
    public String getDetailHtml() {
        return detailHtml;
    }
}
