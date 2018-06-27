package com.jakob.blog.vo;

import java.io.Serializable;

/**
 * 菜单值对象
 * @author Jakob
 */
public class Menu implements Serializable {
    private String name;
    private String url;

    public Menu() {
    }

    public Menu(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
