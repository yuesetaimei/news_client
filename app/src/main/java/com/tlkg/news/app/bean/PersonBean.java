package com.tlkg.news.app.bean;

import java.io.Serializable;

/**
 * Created by wuxiaoqi on 2017/10/13.
 */

public class PersonBean implements Serializable {
    public String name;

    public String imageUrl;

    public String art;

    public PersonBean() {
    }

    public PersonBean(String name, String imageUrl, String art) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.art = art;
    }
}
