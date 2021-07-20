package com.ldy.myblog.mapper;

import com.ldy.myblog.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Mapper // 这个注解表示了这是一个mybatis的mapper类
@Repository // dao层组件注解，表示这是一个dao层组件
public interface UserMapper {
    User selectUserById(long id);

    User selectUserByEmail(String email);

    int updateLastLogin(@Param("id") long id, @Param("lastLogin") Date lastLogin);

    int insertUser(User user);
}
