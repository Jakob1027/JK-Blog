package com.jakob.blog.repository;

import com.jakob.blog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * @author Jakob
 */
public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * 根据用户姓名分页查询用户列表
     * @param name
     * @param pageable
     * @return
     */
    Page<User> findByNameLike(String name, Pageable pageable);

    /**
     * 根据用户账号查询用户
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 根据用户名称列表查询用户列表
     * @param usernames
     * @return
     */
    List<User> findByUsernameIn(Collection<String> usernames);
}
