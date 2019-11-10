package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    private UserService service =  new UserServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取表单数据
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();

        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //封装错误信息对象
        ResultInfo info = new ResultInfo();
        User loginUser = service.login(user);
        if(loginUser==null){
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误!");
            ObjectMapper mapper = new ObjectMapper();
            String s = mapper.writeValueAsString(info);
            //设置响应格式并写入
            response.setContentType("application/json;charset=UTF-8");
            mapper.writeValue(response.getOutputStream(),s);
        }
        //2.需要确定用户的账号是否激活
        //ResultInfo info = new ResultInfo();
        if(loginUser!=null&&!"Y".equalsIgnoreCase(loginUser.getStatus())){
            info.setFlag(false);
            info.setErrorMsg("该用户未激活！");
        }

        if(loginUser!=null&&"Y".equalsIgnoreCase(loginUser.getStatus())){
            info.setFlag(true);
        }


        //将登陆用户存入session域，用于回显数据
        HttpSession session = request.getSession();


        //3.校验验证码
        String check = request.getParameter("check");
        String login_checkcode = (String)session.getAttribute("CHECKCODE_SERVER");
        if(check==null||!check.equalsIgnoreCase(login_checkcode)){
            info.setFlag(false);
            info.setErrorMsg("验证码为空或输入错误！");

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);

            //设置写入文本格式
            response.setContentType("application/json;charset=UTF-8");
            mapper.writeValue(response.getOutputStream(),json);

        }

        session.setMaxInactiveInterval(60*60*10);
        session.setAttribute("user",loginUser);



        //4.是否选自动登录
        String auto = request.getParameter("auto");

        Cookie cookie = new Cookie("userInfo", URLEncoder.encode(loginUser.getName() + "#" + loginUser.getPassword()+"#"+
                loginUser.getStatus(),"UTF-8"));

        if(loginUser!=null&&auto!=null&&auto.equalsIgnoreCase("on"))
        {
            //自动登录
            response.addCookie(cookie);
            cookie.setMaxAge(60*60);
            System.out.println("自动登陆了");
        }else {//没有自动登录
            // System.out.println("没自动登录。。。");
            cookie.setMaxAge(0);
        }




        //5.响应错误信息
        //5.1将提示信息json化
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(info);
        System.out.println(s);
        //设置响应格式并写入
        response.setContentType("application/json;charset=UTF-8");
        mapper.writeValue(response.getOutputStream(),s);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request,response);
    }
}
