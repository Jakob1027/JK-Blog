package com.jakob.blog.repository;

import com.jakob.blog.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jakob
 */
public interface VoteRepository extends JpaRepository<Vote,Long> {
}
