package com.wzy.blog.service;

import java.util.*;
import com.wzy.blog.pojo.Blog;
import com.wzy.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {

    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id ,Blog blog);

    void deleteBlog(Long id);

    Page<Blog> listBlog(Pageable pageable);

    //根据Tag查询相关博客
    Page<Blog> listBlog(Long tagId,Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer size);

    Page<Blog> listBlog(Pageable pageable,String query);

    Blog getAndConvert(Long id);

    Map<String,List<Blog>> archiveBlog();

    Long countBlog();
}
