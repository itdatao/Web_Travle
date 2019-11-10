package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.PageService;

import java.util.List;

public class PageServiceImg implements PageService {
    private RouteDao dao = new RouteDaoImpl();

    @Override
    public PageBean pageQuery(int cid, int currentPage, int pageSize,String rname) {

        PageBean<Route> page = new PageBean();//创建page对象，等待封装

        page.setCurrentPage(currentPage);//设置当前页，

        page.setPageSize(pageSize);//设置页面显示路线数量

        int totalCount = dao.findTotalCount(cid,rname);//总条数
        page.setTotalCount(totalCount);

        int start = (currentPage-1)*pageSize;//开始的位置

        List<Route> routeList = dao.findByPage(cid, start, pageSize,rname);//从数据库读取路线列表封装成集合

        page.setList(routeList);

        int totalPage = totalCount%pageSize==0 ? totalCount/pageSize:(totalCount/pageSize)+1;
        //如果总页数能被每页显示的数量整除，直接返回结果，反之加一页显示
        page.setTotalPage(totalPage);

        return page;
    }
}
