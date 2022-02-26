package com.wzy.blog.service;


import com.wzy.blog.pojo.Blog;

import java.util.List;

/**
 * Redis 初始化
 */
public interface RedisService {

    List<Blog> getTopTitle();
}
