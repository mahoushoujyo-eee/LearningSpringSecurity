package org.example.learingss.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.learingss.entities.User;

@Mapper
public interface UserMapper
{

    void resetPasswordByUsername(User user);

    Boolean ifContainsUsername(String username);

    User getUserByUsername(String username);

    void insertUser(User user);
}
