package com.ldy.myblog.controller;

import com.ldy.myblog.common.dto.LoginDto;
import com.ldy.myblog.common.dto.SignUpDto;
import com.ldy.myblog.common.lang.Result;
import com.ldy.myblog.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto){
        return accountService.login(loginDto);
    }

    @GetMapping("/signup")
    public Result signUp(@Validated @RequestBody SignUpDto signUpDto) {
        return accountService.signUp(signUpDto);
    }

    @GetMapping("/logout")
    public Result logout(){
        return accountService.logout();
    }

}
