package com.jakob.blog.repository;

import com.jakob.blog.entity.Catalog;
import com.jakob.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Jakob
 */
public interface CatalogRepository extends JpaRepository<Catalog,Long> {
    /**
     * 根据用户查询
     * @param user
     * @return
     */
    List<Catalog> findByUser(User user);

    /**
     * 根据用户和分类名称查询
     * @param user
     * @param name
     * @return
     */
    List<Catalog> findByUserAndName(User user,String name);
}
