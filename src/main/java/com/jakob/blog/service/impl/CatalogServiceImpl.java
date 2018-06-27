package com.jakob.blog.service.impl;

import com.jakob.blog.entity.Catalog;
import com.jakob.blog.entity.User;
import com.jakob.blog.repository.CatalogRepository;
import com.jakob.blog.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jakob
 */
@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;

    @Override
    public Catalog saveCatalog(Catalog catalog) {
        List<Catalog> catalogs = catalogRepository.findByUserAndName(catalog.getUser(), catalog.getName());
        if(catalogs!=null&&catalogs.size()>0) {
            throw new IllegalArgumentException("该分类已经存在");
        }
        return catalogRepository.save(catalog);
    }

    @Override
    public void deleteCatalog(Long id) {
        catalogRepository.deleteById(id);
    }

    @Override
    public List<Catalog> listCatalog(User user) {
        return catalogRepository.findByUser(user);
    }

    @Override
    public Catalog getCatalogById(Long id) {
        return catalogRepository.getOne(id);
    }
}
