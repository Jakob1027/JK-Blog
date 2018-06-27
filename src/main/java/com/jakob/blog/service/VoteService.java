package com.jakob.blog.service;

import com.jakob.blog.entity.Vote;

/**
 * @author Jakob
 */
public interface VoteService {

    /**
     * 根据id获取点赞
     * @param id
     * @return
     */
    Vote getVoteById(Long id);

    /**
     * 根据id取消点赞
     * @param id
     */
    void deleteVote(Long id);
}
