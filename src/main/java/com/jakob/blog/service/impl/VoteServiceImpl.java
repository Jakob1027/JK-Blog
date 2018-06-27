package com.jakob.blog.service.impl;

import com.jakob.blog.entity.Vote;
import com.jakob.blog.repository.VoteRepository;
import com.jakob.blog.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jakob
 */
@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Override
    public Vote getVoteById(Long id) {
        return voteRepository.getOne(id);
    }

    @Override
    public void deleteVote(Long id) {
        voteRepository.deleteById(id);
    }
}
