package com.ldy.myblog.service;

import com.ldy.myblog.common.dto.SignUpDto;
import com.ldy.myblog.pojo.User;

import java.util.Date;

public interface UserService {
    User getUserById(long id);
    User getUserByEmail(String email);
    int setUserLastLogin(long id, Date lastLogin);
    int saveUser(SignUpDto signUpDto);
}
