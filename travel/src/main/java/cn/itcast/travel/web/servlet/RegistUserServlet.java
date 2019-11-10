package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/register")
public class RegistUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //校验验证码
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        //移出验证码，保证验证码只是用一次
        session.removeAttribute("CHECKCODE_SERVER");

        if (checkcode_server==null||!checkcode_server.equalsIgnoreCase(check)){//不区分大小写如果不等
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);

            //设置写入文本格式
            response.setContentType("application/json;charset=UTF-8");
            mapper.writeValue(response.getOutputStream(),json);
            return;
        }

        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();


        //2.封装对象
        User user = new User();

        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.调用service方法
        UserService service =  new UserServiceImpl();

        boolean flag = false;
        try {
            flag = service.regit(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //4.验证是否注册成功
        ResultInfo info = new ResultInfo();
        if (flag){
            info.setFlag(true);
        }else {
            info.setFlag(false);
            info.setErrorMsg("注册失败！");
        }

        //5.将提示信息json 化,并写到客户端
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);

        //设置写入文本格式
        response.setContentType("application/json;charset=UTF-8");
        mapper.writeValue(response.getOutputStream(),json);
        System.out.println(json);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request,response);
    }
}
