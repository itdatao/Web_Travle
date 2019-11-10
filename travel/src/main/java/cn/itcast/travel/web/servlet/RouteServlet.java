package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RouteServlet")
public class RouteServlet extends HttpServlet {
    private RouteService routeService = new RouteServiceImpl();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /**
         * 根据rid查询一个路由路线的详细信息
         *
          */

        //1.接收参数rid
        String rid = request.getParameter("rid");
        if(rid==null||"".equals(rid))
        {
            throw new NullPointerException();

        }
        //2. 调用service方法返回route对象
       // System.out.println("RouteServlet: rid="+rid);
        Route route  = routeService.findOne(rid);

        //3. 将数据转换为json传递到页面

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),route);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
