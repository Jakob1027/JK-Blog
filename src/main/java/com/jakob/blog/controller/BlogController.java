package com.jakob.blog.controller;

import com.jakob.blog.entity.User;
import com.jakob.blog.entity.es.EsBlog;
import com.jakob.blog.service.EsBlogService;
import com.jakob.blog.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Blog控制器
 * @author Jakob
 */
@Controller
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private EsBlogService esBlogService;

    @GetMapping
    public String listBlogs(@RequestParam(value="order",required = false,defaultValue = "new") String order,
                            @RequestParam(value="keyword",required = false,defaultValue = "") String keyword,
                            @RequestParam(value = "async",required = false) boolean async,
                            @RequestParam(value = "pageIndex",required = false,defaultValue = "0") int pageIndex,
                            @RequestParam(value = "pageSize",required = false,defaultValue = "10") int pageSize,
                            Model model) {
        List<EsBlog> list;
        Page<EsBlog> page = null;
        boolean isEmpty = true; // 系统初始化时，没有博客数据

        try {
            if (order.equals("hot")) { // 最热查询
                Sort sort = new Sort(Sort.Direction.DESC,"readSize","commentSize","voteSize","createTime");
                Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
                page = esBlogService.listHottestEsBlogs(keyword, pageable);
            } else if (order.equals("new")) { // 最新查询
                Sort sort = new Sort(Sort.Direction.DESC,"createTime");
                Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
                page = esBlogService.listNewestEsBlogs(keyword, pageable);
            }
            isEmpty = false;
        } catch (Exception e) {
            Pageable pageable = PageRequest.of(pageIndex,pageSize);
            page = esBlogService.listEsBlogs(pageable);
        }

        list = page.getContent();

        model.addAttribute("keyword",keyword);
        model.addAttribute("order",order);
        model.addAttribute("blogList",list);
        model.addAttribute("page",page);

        if(!async&&!isEmpty) {
            List<EsBlog> newest = esBlogService.listTop5NewestEsBlogs();
            model.addAttribute("newest",newest);
            List<EsBlog> hottest = esBlogService.listTop5HottestEsBlogs();
            model.addAttribute("hottest",hottest);
            List<TagVo> tags = esBlogService.listTop30Tags();
            model.addAttribute("tags",tags);
            List<User> users = esBlogService.listTop12Users();
            model.addAttribute("users",users);
        }

        return async?"/index :: #mainContainerRepleace":"/index";
    }
}
