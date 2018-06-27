package com.jakob.blog.service.impl;

import com.jakob.blog.entity.*;
import com.jakob.blog.entity.es.EsBlog;
import com.jakob.blog.repository.BlogRepository;
import com.jakob.blog.service.BlogService;
import com.jakob.blog.service.EsBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jakob
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private EsBlogService esBlogService;

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        boolean isNew = (blog.getId()==null);
        blog = blogRepository.save(blog);
        EsBlog esBlog = null;
        if(isNew) {
            esBlog = new EsBlog(blog);
        } else {
            esBlog = esBlogService.getEsBlogByBlogId(blog.getId());
            esBlog.update(blog);
        }
        esBlogService.updateEsBlog(esBlog);
        return blog;
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        EsBlog esBlog = esBlogService.getEsBlogByBlogId(id);
        blogRepository.deleteById(id);
        esBlogService.deleteEsBlog(esBlog.getId());
    }

    @Override
    public Blog getBlogById(Long id) {
        return blogRepository.getOne(id);
    }

    @Override
    public Page<Blog> listBlogsByTitleVote(User user, String title, Pageable pageable) {
        // 模糊查询
        title = "%" + title + "%";
        String tags = title;
        Page<Blog> blogs = blogRepository.findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(title,
                user, tags, user, pageable);
        return blogs;
    }

    @Override
    public Page<Blog> listBlogsByTitleVoteAndSort(User user, String title, Pageable pageable) {
        title = "%"+title+"%";
        return blogRepository.findByUserAndTitleLike(user,title,pageable);
    }

    @Transactional
    @Override
    public void readingIncrease(Long id) {
        Blog blog = blogRepository.getOne(id);
        blog.setReadSize(blog.getReadSize()+1);
        blogRepository.save(blog);
    }

    @Override
    public Blog addComment(Long blogId, String commentContent) {
        Blog blog = blogRepository.getOne(blogId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        blog.addComment(new Comment(commentContent,user));
        return this.saveBlog(blog);
    }

    @Override
    public void deleteComment(Long blogId, Long commentId) {
        Blog blog = blogRepository.getOne(blogId);
        blog.deleteComment(commentId);
        this.saveBlog(blog);
    }

    @Override
    public Blog addVote(Long blogId) {
        Blog blog = blogRepository.getOne(blogId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Vote vote = new Vote(user);
        boolean isExist = blog.addVote(vote);
        if(isExist) {
            throw new IllegalArgumentException("该用户已经点过赞了");
        }
        return blogRepository.save(blog);
    }

    @Override
    public void deleteVote(Long blogId, Long voteId) {
        Blog blog = blogRepository.getOne(blogId);
        blog.deleteVote(voteId);
        blogRepository.save(blog);
    }

    @Override
    public Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable) {
        return blogRepository.findByCatalog(catalog, pageable);
    }


}
