package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

import java.sql.SQLException;

public interface UserService {
    boolean regit(User user) throws SQLException;
    boolean active(String code);
    User login(User user);
}
