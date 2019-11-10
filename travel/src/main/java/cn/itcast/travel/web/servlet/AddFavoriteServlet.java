package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addFavoriteServlet")
public class AddFavoriteServlet extends HttpServlet {
    private  FavoriteService favoriteService = new FavoriteServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.获取rid
        String rid = request.getParameter("rid");
        //2,判断当前是否有用户登录
        User user = (User)request.getSession().getAttribute("user");
        int uid = 0;
        if (user==null){
            return;
        }else{
           uid = user.getUid();
    }
        System.out.println("addFavoriteServlet: rid="+rid+":  uid="+uid+"");
        favoriteService.addFavorite(rid,uid);
    }
}
