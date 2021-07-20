package com.ldy.myblog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Blog {
    private String title;
    private String content;
    private long blogid;
    private Date date;
    private long userid;
    private int status;
    private String description;
}
