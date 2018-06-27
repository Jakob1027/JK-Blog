package com.jakob.blog.vo;

import com.jakob.blog.entity.Catalog;

import java.io.Serializable;

/**
 * @author Jakob
 */
public class CatalogVo implements Serializable {
    private String username;
    private Catalog catalog;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }
}
