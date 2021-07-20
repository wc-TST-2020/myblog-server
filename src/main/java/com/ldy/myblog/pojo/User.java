package com.ldy.myblog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements Serializable {
    private long userid;
    private String email;
    private String username;
    private String password;
    private String role;
    private int status;
    private Date created;
    private Date last_login;

    public long getId(){
        return userid;
    }
}
