package cn.itcast.travel.web.filter;


import cn.itcast.travel.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;

@WebFilter(filterName = "autoLoginFilter",urlPatterns = "/login.html")
public class autoLoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;

        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        //user未登录
        if (user==null){

            Cookie[] cookies = request.getCookies();
            String autoValue = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userInfo")){
                    autoValue = URLDecoder.decode(cookie.getValue(),"UTF-8");
                }
            }
            if (autoValue!=null){//上次点了自动登录，这次就直接登陆，转到首页
                    String username = autoValue.split("#")[0];
                    String password = autoValue.split("#")[1];
                    String status = autoValue.split("#")[2];
                    User user1 = new User();
                    user1.setName(username);
                    user1.setPassword(password);
                    user1.setStatus(status);
                    session.setAttribute("user",user1);

                System.out.println("autoFilter 执行了");
            }


        }


        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
