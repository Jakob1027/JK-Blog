package com.jakob.blog.service.impl;

import com.jakob.blog.entity.Authority;
import com.jakob.blog.repository.AuthorityRepository;
import com.jakob.blog.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jakob
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Authority findById(Long id) {
        Authority authority = authorityRepository.findById(id).get();
        return authority;
    }
}
