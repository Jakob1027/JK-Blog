package com.jakob.blog.service;

import com.jakob.blog.entity.User;
import com.jakob.blog.entity.es.EsBlog;
import com.jakob.blog.vo.TagVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * EsBlog 服务接口
 * @author Jakob
 */
public interface EsBlogService {
    /**
     * 删除EsBlog
     * @param id
     */
    void deleteEsBlog(String id);

    /**
     * 更新EsBlog
     * @param esBlog
     * @return
     */
    EsBlog updateEsBlog(EsBlog esBlog);

    /**
     * 根据blogId获取EsBlog
     * @param BlogId
     * @return
     */
    EsBlog getEsBlogByBlogId(Long BlogId);

    /**
     * 获取博客列表
     * @param pageable
     * @return
     */
    Page<EsBlog> listEsBlogs(Pageable pageable);

    /**
     * 获取最新博客列表
     * @param keyword
     * @param pageable
     * @return
     */
    Page<EsBlog> listNewestEsBlogs(String keyword,Pageable pageable);

    /**
     * 获取最热博客列表
     * @param keyword
     * @param pageable
     * @return
     */
    Page<EsBlog> listHottestEsBlogs(String keyword,Pageable pageable);

    /**
     * 最新前五
     * @return
     */
    List<EsBlog> listTop5NewestEsBlogs();

    /**
     * 最热前五
     * @return
     */
    List<EsBlog> listTop5HottestEsBlogs();

    /**
     * 最热前 30 标签
     * @return
     */
    List<TagVo> listTop30Tags();

    /**
     * 最热前12用户
     * @return
     */
    List<User> listTop12Users();
}
