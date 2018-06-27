package com.jakob.blog.service;

import com.jakob.blog.entity.Blog;
import com.jakob.blog.entity.Catalog;
import com.jakob.blog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Jakob
 */
public interface BlogService {
    /**
     * 保存博客
     * @param blog
     * @return
     */
    Blog saveBlog(Blog blog);

    /**
     * 删除博客
     * @param id
     */
    void deleteBlog(Long id);

    /**
     * 根据id查询博客
     * @param id
     * @return
     */
    Blog getBlogById(Long id);

    /**
     * 根据用户进行博客名称分页模糊查询（最新）
     * @param user
     * @param title
     * @return
     */
    Page<Blog> listBlogsByTitleVote(User user, String title, Pageable pageable);

    /**
     * 根据用户进行博客名称分页模糊查询（最热）
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    Page<Blog> listBlogsByTitleVoteAndSort(User user,String title,Pageable pageable);

    /**
     * 阅读量递增
     * @param id
     */
    void readingIncrease(Long id);

    /**
     * 添加评论
     * @param blogId
     * @param commentContent
     */
    Blog addComment(Long blogId,String commentContent);

    /**
     * 删除评论
     * @param blogId
     * @param commentId
     */
    void deleteComment(Long blogId,Long commentId);

    /**
     * 点赞
     * @param blogId
     * @return
     */
    Blog addVote(Long blogId);

    /**
     * 取消点赞
     * @param blogId
     * @param VoteId
     */
    void deleteVote(Long blogId,Long VoteId);

    /**
     * 根据分类查询博客
     * @param catalog
     * @param pageable
     * @return
     */
    Page<Blog> listBlogsByCatalog(Catalog catalog,Pageable pageable);
}
