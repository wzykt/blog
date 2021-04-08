package com.wzy.blog.dao;

import com.wzy.blog.pojo.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface TagRepository extends JpaRepository<Tag,Long> {

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);

    Tag findByName(String name);
}
