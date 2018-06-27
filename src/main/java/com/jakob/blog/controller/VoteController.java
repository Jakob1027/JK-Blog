package com.jakob.blog.controller;

import com.jakob.blog.entity.User;
import com.jakob.blog.service.BlogService;
import com.jakob.blog.service.VoteService;
import com.jakob.blog.utils.ConstraintViolationExceptionHandler;
import com.jakob.blog.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.ConstraintViolationException;

/**
 * @author Jakob
 */
@Controller
@RequestMapping("/votes")
public class VoteController {
    @Autowired
    private VoteService voteService;

    @Autowired
    private BlogService blogService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Response> createVote(Long blogId) {
        try {
            blogService.addVote(blogId);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false,ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false,e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true,"点赞成功"));
    }

    @DeleteMapping("/{voteId}")
    public ResponseEntity<Response> deleteComment(@PathVariable Long voteId,Long blogId) {
        boolean isOwner = false;
        User user = voteService.getVoteById(voteId).getUser();

        //判断操作用户是否是点赞所有者
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
            blogService.deleteVote(blogId,voteId);
            voteService.deleteVote(voteId);
        }catch (ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }  catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false,e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true,"取消点赞成功"));
    }

}
