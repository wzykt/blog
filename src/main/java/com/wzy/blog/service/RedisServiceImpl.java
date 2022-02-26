package com.wzy.blog.service;

import com.wzy.blog.pojo.Blog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String SCORE_RANK = "top_blog";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<Blog> getTopTitle() {
        Set<Serializable> range = redisTemplate.boundZSetOps(SCORE_RANK).range(0, -1);
        List<Blog> res = new ArrayList<>();
        for (Serializable s : range) {
            Blog b = (Blog) s;
            res.add(b);
        }
        return res;
    }
}
