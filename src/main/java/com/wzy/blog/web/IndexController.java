package com.wzy.blog.web;

import com.wzy.blog.pojo.Blog;
import com.wzy.blog.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
public class IndexController {


    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private InitService initService;

    @GetMapping({"/index", "/"})
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Page<Blog> page = blogService.listBlog(pageable);
        System.out.println(page.isEmpty());
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlog", blogService.listRecommendBlogTop(8));
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam String query, @PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, "%" + query + "%"));
        model.addAttribute("query", query);
        return "search";
    }


    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }

    @GetMapping("/footer/newblogs")
    public String newblogs(Model model) {
        List<Blog> topTitle = new ArrayList<>();
        //缓存中数量不足五个时，从数据库中拿，并初始化缓存
        if (redisService.getTopTitle().size() < 5) {
            topTitle = blogService.listRecommendBlogTop(5);
            log.info("初始化缓存");
            initService.initTop5();
        } else {
            log.info("缓存数据");
            topTitle = redisService.getTopTitle();
        }
        model.addAttribute("newblogs", topTitle);
        return "_fragments :: newblogList";
    }


    @GetMapping("/footer/adminnewblogs")
    public String adminnewblogs(Model model) {
        model.addAttribute("newblogs", blogService.listRecommendBlogTop(3));
        return "/admin/_fragments :: newblogList";
    }

}
