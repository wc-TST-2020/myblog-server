package com.ldy.myblog.service;

import com.ldy.myblog.common.dto.SignUpDto;
import com.ldy.myblog.mapper.UserMapper;
import com.ldy.myblog.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(long id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        if (email == null) {
            return null;
        }
        return userMapper.selectUserByEmail(email);
    }

    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @Override
    public int setUserLastLogin(long id, Date lastLogin) {
        if (lastLogin == null) {
            return 0;
        }
        return userMapper.updateLastLogin(id,lastLogin);
    }

    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @Override
    public int saveUser(SignUpDto signUpDto) throws RuntimeException {
        User user = new User(0,signUpDto.getEmail(),signUpDto.getUsername(),signUpDto.getPassword(),"user",1,new Date(),null);
        if (userMapper.selectUserByEmail(user.getEmail()) != null) {
            throw new RuntimeException("该用户已经存在");
        }
        return userMapper.insertUser(user);
    }
}
