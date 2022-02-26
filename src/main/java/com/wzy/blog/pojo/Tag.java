package com.wzy.blog.pojo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.*;
import java.util.ArrayList;

@Entity
@Table(name="t_tag")
public class Tag implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "标签名不能为空")
    private String name;
    @ManyToMany(mappedBy = "tags",cascade = {CascadeType.REMOVE})
    private List<Blog> blogs = new ArrayList<>();
    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
