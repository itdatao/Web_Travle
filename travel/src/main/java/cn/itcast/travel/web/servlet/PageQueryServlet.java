package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.service.PageService;
import cn.itcast.travel.service.impl.PageServiceImg;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cn.itcast.travel.util.IsNum.isNumeric;


@WebServlet("/PageQueryServlet")
public class PageQueryServlet extends HttpServlet {
    private PageService service = new PageServiceImg();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cidStr = request.getParameter("cid");
//        int index = cidStr.indexOf("&");
//        String cidS = cidStr.substring(0,index);
        String pageSizeStr = request.getParameter("pageSize");
        String currentPageStr = request.getParameter("currentPage");
        String rname = request.getParameter("rname");


        if(rname!=null&&rname.length()>0){
            rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
            if(rname.equals("undefined")){
                rname = "";
            }
        }


        boolean  isNum = isNumeric(cidStr);

        int cid = 0;
        if(cidStr!=null&&cidStr.length()>0 && !cidStr.equals("null")&&isNum){
            cid = Integer.parseInt(cidStr);
        }else{
            cid=1;
        }

        int pageSize = 0;
        if(pageSizeStr!=null&&pageSizeStr.length()>0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
            pageSize=6;
        }

        int currentPage = 0;
        if(currentPageStr!=null&&currentPageStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);
        }else{
            currentPage=1;
        }

        System.out.println("rname: "+rname+" cid: "+cidStr+" currentPage:  "+currentPage);



        //2.调用service方法，返回pageBean对象
        PageBean pageBean = service.pageQuery(cid, currentPage, pageSize,rname);


      /*    int count = pageBean.getTotalCount();
            int totalPage = pageBean.getTotalPage();*/
        //3.将对象json化，返回到前台
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),pageBean);
        //System.out.println("list:   "+pageBean.getList());

    }
}
