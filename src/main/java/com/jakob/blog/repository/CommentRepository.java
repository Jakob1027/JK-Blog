package com.jakob.blog.repository;

import com.jakob.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jakob
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {
}
