package com.jakob.blog.service;

import com.jakob.blog.entity.Comment;

/**
 * @author Jakob
 */
public interface CommentService {

    /**
     * 根据id获取评论
     * @param id
     * @return
     */
    Comment getCommentById(Long id);

    /**
     * 根据id删除评论
     * @param id
     */
    void deleteCommentById(Long id);
}
