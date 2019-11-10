package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;


public interface UserDao {

    User findByUsername(String name);
    void save(User user);
    boolean findByCode(String code);
    void updateStatus(String code);
    User findByUsernameAndPassword(User user);
}
