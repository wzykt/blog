package com.wzy.blog.service;

import com.wzy.blog.pojo.Blog;
import com.wzy.blog.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class InitService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BlogService blogService;

    @Autowired
    private RedisUtil redisUtil;

    public static final String SCORE_RANK = "top_blog";

    /**
     * 初始化数据
     */
    public void initTop5() {
        List<Blog> blogs = blogService.listRecommendBlogTop(5);
        Set<ZSetOperations.TypedTuple<Serializable>> tuples = new HashSet<>();
        for (int i = 0; i < blogs.size(); i++) {
            log.info(blogs.get(i).getTitle());
            DefaultTypedTuple<Serializable> tuple = new DefaultTypedTuple<>(blogs.get(i), 1D + i);
            tuples.add(tuple);
        }
        Long num = redisTemplate.opsForZSet().add(SCORE_RANK, tuples);
        log.info("初始化" + num + "个博客");
        //设置40天过期
        this.redisTemplate.expire(SCORE_RANK, 40, TimeUnit.DAYS);
    }

    //执行定时刷新
    @Scheduled(cron = "0 0 0 1/1 * ? ")
    public void refreshTop() {
        new Thread(() -> {
            this.initTop5();
        }).start();
    }
}