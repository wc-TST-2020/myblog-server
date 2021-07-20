package com.ldy.myblog.common.lang;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result <T> {
    private int code;
    private String msg;
    private T data;

    public static <T> Result getResult(int code,String msg,T data){
        return new Result(code,msg,data);
    }

    public static <T> Result succ(String msg,T data){
        return getResult(200,msg,data);
    }

    public static Result succ(String msg){
        return getResult(200,msg,null);
    }

    public static <T> Result fail(String msg,T data){
        return getResult(400,msg,data);
    }

    public static Result fail(String msg){
        return getResult(400,msg,null);
    }

    public static Result exception(int code,String msg){
        return getResult(code,msg,null);
    }
}
