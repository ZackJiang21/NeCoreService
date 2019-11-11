package org.iiai.ne.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.iiai.ne.model.User;

@Mapper
public interface UserDao {
    User getUser(@Param("openId") String openId, @Param("userId") int usrId);

    void insertUser(@Param("user") User user);
}
