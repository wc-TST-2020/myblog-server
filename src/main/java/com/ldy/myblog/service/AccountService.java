package com.ldy.myblog.service;

import com.ldy.myblog.common.dto.LoginDto;
import com.ldy.myblog.common.dto.SignUpDto;
import com.ldy.myblog.common.lang.Result;

public interface AccountService {
    public Result login(LoginDto loginDto);

    public Result signUp(SignUpDto signUpDto);

    public Result logout();
}
