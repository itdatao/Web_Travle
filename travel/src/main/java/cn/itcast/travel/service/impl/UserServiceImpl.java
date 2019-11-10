package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;


import java.sql.SQLException;


public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();


    @Override
    public boolean regit(User user) throws SQLException {


        //1.判断用户名是否合法，调用findByUsername();
        User user1 = dao.findByUsername(user.getUsername());

        if(user1!=null){//说明该用户名已经存在无法再次注册

            return false;
        }

        user.setStatus("N");//设置状态码，N表示未激活，Y表示已激活
        user.setCode(UuidUtil.getUuid());//设置激活码
        System.out.println(user.getCode());
        //2.如果合法，保存数据到数据库
        dao.save(user);

        String content = "<a href='http://localhost:8080/travel/activeServlet?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";
        //写待发送的文本信息，一个超链接让用户点击后status改变 然后激活，可以登录

        MailUtils.sendMail(user.getEmail(),content,"激活邮件");

        return true;
    }

    @Override
    public boolean active(String code) {

        UserDao dao = new UserDaoImpl();
        boolean flag = dao.findByCode(code);
        if (flag){//含有激活码，激活成功
            dao.updateStatus(code);//修改激活状态
            return true;
        }

        return false;
    }

    @Override
    public User login(User user) {

        UserDao dao = new UserDaoImpl();

        return dao.findByUsernameAndPassword(user);

    }


}
