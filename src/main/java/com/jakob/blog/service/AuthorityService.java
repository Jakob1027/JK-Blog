package com.jakob.blog.service;

import com.jakob.blog.entity.Authority;

/**
 * @author Jakob
 */
public interface AuthorityService {
    /**
     * 根据id查询权限
     * @param id
     * @return
     */
    Authority findById(Long id);
}
