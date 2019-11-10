package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/FavoriteServlet")
public class FavoriteServlet extends HttpServlet {

    private FavoriteService service = new FavoriteServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //接收参数rid

        String rid = request.getParameter("rid");

        User user = (User) request.getSession().getAttribute("user");
        int uid ;
        if (user==null){
           uid = 0;//用户未登录
        }else{
            uid = user.getUid();//用户已经登录
        }
        //System.out.println("FavoriteServlet rid="+rid+"  uid="+uid+"");
        boolean flag = service.isFavorite(rid, uid);

        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(),flag);



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
