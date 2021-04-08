package com.wzy.blog.service;

import com.wzy.blog.pojo.User;

public interface UserService {

    User checkUser(String username, String password);

}
