package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeServlet")
public class activeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        //1.校验激活码是否合法
        String code = request.getParameter("code");
        UserService service = new UserServiceImpl();
        String msg = "";//提示信息
        if(code!=null){
            boolean flag = service.active(code);
            if (flag){//激活成功，登陆
                msg = "激活成功，请<a href='login.html'>登录</a>";
            }else{
                msg="激活失败，请稍后再试！";
            }
        }else{
            response.getWriter().write("Have not code active....");
        }


        response.getWriter().write(msg);






    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);
    }
}
