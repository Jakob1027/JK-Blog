package com.jakob.blog.controller;

import com.jakob.blog.entity.Blog;
import com.jakob.blog.entity.Comment;
import com.jakob.blog.entity.User;
import com.jakob.blog.service.BlogService;
import com.jakob.blog.service.CommentService;
import com.jakob.blog.utils.ConstraintViolationExceptionHandler;
import com.jakob.blog.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author Jakob
 */
@Controller
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @GetMapping
    public String listComments(Long blogId, Model model) {
        Blog blog = blogService.getBlogById(blogId);
        List<Comment> comments = blog.getComments();

        //判断操作用户是否是评论所有者
        String commentOwner = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getPrincipal().toString().equals("anonymousUser")) {
            User user = (User) authentication.getPrincipal();
            if (user != null) {
                commentOwner = user.getUsername();
            }
        }

        model.addAttribute("commentOwner", commentOwner);
        model.addAttribute("comments", comments);
        return "/userspace/blog :: #mainContainerRepleace";
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Response> saveComment(Long blogId, String commentContent) {
        try {
            blogService.addComment(blogId, commentContent);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false,ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false,e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true,"处理成功"));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Response> deleteComment(@PathVariable Long commentId,Long blogId) {
        boolean isOwner = false;
        User user = commentService.getCommentById(commentId).getUser();

        //判断操作用户是否是评论所有者
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getPrincipal().toString().equals("anonymousUser")) {
            User principal = (User) authentication.getPrincipal();
            if (principal != null&&principal.getUsername().equals(user.getUsername())) {
                isOwner = true;
            }
        }

        if(!isOwner) {
            return ResponseEntity.ok().body(new Response(false,"没有操作权限"));
        }

        try {
            blogService.deleteComment(blogId,commentId);
            commentService.deleteCommentById(commentId);
        }catch (ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }  catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false,e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true,"处理成功"));
    }

}
