package com.jakob.blog.repository;

import com.jakob.blog.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jakob
 */
public interface AuthorityRepository extends JpaRepository<Authority,Long> {

}
