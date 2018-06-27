package com.jakob.blog.service;

import com.jakob.blog.entity.Catalog;
import com.jakob.blog.entity.User;

import java.util.List;

/**
 * @author Jakob
 */
public interface CatalogService {

    /**
     * 保存分类
     * @param catalog
     * @return
     */
    Catalog saveCatalog(Catalog catalog);

    /**
     * 删除分类
     * @param id
     */
    void deleteCatalog(Long id);

    /**
     * 展示分类
     * @param user
     * @return
     */
    List<Catalog>  listCatalog(User user);

    /**
     * 根据id获取分类
     * @param id
     * @return
     */
    Catalog getCatalogById(Long id);
}
