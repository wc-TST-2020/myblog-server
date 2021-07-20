package com.ldy.myblog.service;

import com.ldy.myblog.common.dto.LoginDto;
import com.ldy.myblog.common.dto.SignUpDto;
import com.ldy.myblog.common.lang.Result;
import com.ldy.myblog.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private UserService userService;

    @Override
    public Result login(LoginDto loginDto) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(loginDto.getEmail(),loginDto.getPassword()));
        } catch (UnknownAccountException | IncorrectCredentialsException e) {
            return Result.fail(e.getMessage());
        }
        User user = (User) subject.getPrincipal();
        // 设置最近登录时间
        user.setLast_login(new Date());
        userService.setUserLastLogin(user.getUserid(),user.getLast_login());
        // 构造返回数据
        Map<String,String> map = new HashMap<>();
        map.put("name",user.getUsername());
        map.put("email",user.getEmail());
        map.put("role",user.getRole());
        map.put("sessionId",(String) subject.getSession().getId());
        return Result.succ("登录成功！欢迎回来，"+user.getUsername(),map);
    }

    @Override
    public Result signUp(SignUpDto signUpDto) {
        try {
            userService.saveUser(signUpDto);
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }
        return Result.fail("注册成功");
    }

    @Override
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.fail("退出登录成功");
    }
}
