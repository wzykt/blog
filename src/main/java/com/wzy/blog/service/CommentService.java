package com.wzy.blog.service;

import com.wzy.blog.pojo.Comment;

import java.util.*;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);

    void deleteComment();
}
