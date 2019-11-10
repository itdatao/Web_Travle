package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;



public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public User findByUsername(String name) {
        User user1 = null;
        try{

            String sql = "select * from tab_user where username=?";
            user1 = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class),name);
        }catch (Exception e){

        }

        return user1;
    }

    @Override
    public void save(User user) {
        String sql ="insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?) ";
         jdbcTemplate.update(sql,user.getUsername(),
                user.getPassword(),user.getName(),
                user.getBirthday(),user.getSex(),
                user.getTelephone(),user.getEmail(),
                 user.getStatus(),user.getCode());

    }

    @Override
    public boolean findByCode(String code) {
        User user = null;
        try{
            String sql = "select * from tab_user where code=?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), code);

        }catch (Exception e){
            e.printStackTrace();
        }
        if(user==null){
            return false;
        }else{
            return true;
        }

    }

    @Override
    public void updateStatus(String code) {
        String sql = "update tab_user set status='Y' where code=?";
        jdbcTemplate.update(sql,code);
    }

    @Override
    public User findByUsernameAndPassword(User user) {
            User query = null;
            try{
                String sql = "select * from tab_user where username = ? and password= ?";
                query = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), user.getUsername(), user.getPassword());
            }catch (EmptyResultDataAccessException e){
                return null;
            }

        return query;
    }
}
