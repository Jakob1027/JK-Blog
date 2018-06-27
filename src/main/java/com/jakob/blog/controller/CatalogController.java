package com.jakob.blog.controller;

import com.jakob.blog.entity.Catalog;
import com.jakob.blog.entity.User;
import com.jakob.blog.service.CatalogService;
import com.jakob.blog.utils.ConstraintViolationExceptionHandler;
import com.jakob.blog.vo.CatalogVo;
import com.jakob.blog.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author Jakob
 */
@Controller
@RequestMapping("/catalogs")
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 展示所有分类
     * @param username
     * @param model
     * @return
     */
    @GetMapping
    public String listCatalogs(String username, Model model) {
        User user = (User) userDetailsService.loadUserByUsername(username);

        //判断操作用户是否是分类所有者
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isOwner = false;
        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getPrincipal().toString().equals("anonymousUser")) {
            User principal = (User) authentication.getPrincipal();
            if (principal != null&&principal.getUsername().equals(user.getUsername())) {
                isOwner = true;
            }
        }

        List<Catalog> catalogs = catalogService.listCatalog(user);
        model.addAttribute("isCatalogsOwner",isOwner);
        model.addAttribute("catalogs",catalogs);
        return "/userspace/u :: #catalogRepleace";
    }

    @PostMapping
    @PreAuthorize("authentication.name.equals(#catalogVo.username)")
    public ResponseEntity<Response> createCatalog(@RequestBody CatalogVo catalogVo) {
        String username = catalogVo.getUsername();
        Catalog catalog = catalogVo.getCatalog();
        User user = (User) userDetailsService.loadUserByUsername(username);

        try {
            catalog.setUser(user);
            catalogService.saveCatalog(catalog);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "保存成功"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> deleteCatalog(String username,@PathVariable Long id) {
        try {
            catalogService.deleteCatalog(id);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "删除成功"));
    }

    @GetMapping("/edit")
    public String getCatalogEdit(Model model) {
        model.addAttribute("catalog",new Catalog(null,null));
        return "/userspace/catalogedit";
    }

    @GetMapping("/edit/{id}")
    public String getCatalogEdit(@PathVariable Long id, Model model) {
        Catalog catalog = catalogService.getCatalogById(id);
        model.addAttribute("catalog",catalog);
        return "/userspace/catalogedit";
    }

}
